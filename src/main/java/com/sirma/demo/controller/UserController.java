package com.sirma.demo.controller;

import com.sirma.demo.model.ColleaguesDuoDTO;
import com.sirma.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/longest-duo-in-project")
    public ColleaguesDuoDTO longestDuoInProject (@RequestBody MultipartFile csvFile){
        return userService.getLongestDuo(csvFile);
    }
}
