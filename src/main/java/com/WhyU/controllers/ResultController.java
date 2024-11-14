package com.WhyU.controllers;

import com.WhyU.dto.ResultDTO;
import com.WhyU.models.Result;
import com.WhyU.services.impl.ResultServiceImpl;
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

    @Autowired
    public ResultController(ResultServiceImpl resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public List<Result> findAllResults(){
        return resultService.findAllResults();
    }

    @GetMapping("/by_user/{userID}")
    public ResponseEntity<List<Result>> findAllResultsByUserId(@PathVariable Long userID){
        if(!resultService.findResultById(userID).getUser().getUsername().equals(getCurrentUser()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);

        return ResponseEntity.ok().body(resultService.findAllResultsByUserId(userID));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> findResultById(@PathVariable Long id){
        return ResponseEntity.ok().body(resultService.findResultById(id));
    }

    @PostMapping
    public ResponseEntity<Result> createResult(@RequestBody ResultDTO dto){
        return ResponseEntity.ok().body(resultService.createResult(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateResult(@PathVariable Long id, @RequestBody ResultDTO dto, HttpServletRequest request){
        if(!resultService.findResultById(id).getUser().getUsername().equals(getCurrentUser()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);

        return ResponseEntity.ok().body(resultService.updateResult(id, dto));
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
