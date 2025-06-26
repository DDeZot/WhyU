package com.WhyU.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("images")
public class ImageController {

    private final DefaultResourceLoader defaultResourceLoader;

    @Autowired
    public ImageController(DefaultResourceLoader defaultResourceLoader) {
        this.defaultResourceLoader = defaultResourceLoader;
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        Resource resource = defaultResourceLoader.getResource("classpath:static/uploads/" + imageName);

        if (!resource.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        if(imageName.endsWith(".jpg"))
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        if(imageName.endsWith(".png"))
            headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
