package com.firebase.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Our education to give back to our Islamic society.The more we read it, the more we need it";
    }
    

}
