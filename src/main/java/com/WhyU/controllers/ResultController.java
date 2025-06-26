package com.WhyU.controllers;

import com.WhyU.dto.ResultDTO;
import com.WhyU.models.Result;
import com.WhyU.services.impl.ResultServiceImpl;
import com.WhyU.services.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/results")
public class ResultController {
    private final ResultServiceImpl resultService;
    private final UserServiceImpl userService;

    @Autowired
    public ResultController(ResultServiceImpl resultService, UserServiceImpl userService) {
        this.resultService = resultService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ResultDTO>> findAllResults(){
        return ResponseEntity.ok().body(resultService.findAllResults().stream().map(Result::getDTO).toList());
    }

    @GetMapping("/by_user/{userID}")
    public ResponseEntity<List<ResultDTO>> findAllResultsByUserId(@PathVariable Long userID){
        if(!userService.findUserById(userID).getUsername().equals(getCurrentUser()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        return ResponseEntity.ok().body(resultService.findAllResultsByUserId(userID).stream().map(Result::getDTO).toList());
    }

    @GetMapping("by_story/{storyID}")
    public ResponseEntity<List<ResultDTO>> findResultsByStoryId(@PathVariable Long storyID){
        return ResponseEntity.ok().body(resultService.findAllResultsByStoryId(storyID).stream().map(Result::getDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> findResultById(@PathVariable Long id){
        if(!resultService.findResultById(id).getUser().getUsername().equals(getCurrentUser()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        return ResponseEntity.ok().body(resultService.findResultById(id).getDTO());
    }

    @PostMapping
    public ResponseEntity<ResultDTO> createResult(@RequestBody ResultDTO dto){
        return ResponseEntity.ok().body(resultService.createResult(dto).getDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> updateResult(@PathVariable Long id, @RequestBody ResultDTO dto, HttpServletRequest request){
        if(!resultService.findResultById(id).getUser().getUsername().equals(getCurrentUser()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        return ResponseEntity.ok().body(resultService.updateResult(id, dto).getDTO());
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : null;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
