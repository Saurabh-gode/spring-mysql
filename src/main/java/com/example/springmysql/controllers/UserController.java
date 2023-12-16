package com.example.springmysql.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.springmysql.entity.*;
import com.example.springmysql.response.ResponseHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping(path = "/v1/user")
public class UserController {
    ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody Object addNewUser(@RequestBody String bodyJSON) throws JsonProcessingException {
        User parsedUser = objectMapper.readValue(bodyJSON, User.class);
        logger.info(objectMapper.writeValueAsString(bodyJSON));
        User springUser = new User();
        springUser.setName(parsedUser.getName());
        springUser.setEmail(parsedUser.getEmail());
        userRepository.save(springUser);
        return ResponseHandler.send(HttpStatus.OK, false, "Users Fetched successfully", springUser);

    }

    @GetMapping(path = "/all")
    public @ResponseBody Object getAllUsers(){
        Iterable<User> userData = userRepository.findAll();
       return ResponseHandler.send(HttpStatus.OK, false, "Users Fetched successfully", userData);
    }

    @DeleteMapping(path = "/remove/{user_id}")
    public @ResponseBody Object removeUser(@PathVariable String user_id){
        int parsedId = parseInt(user_id);
        userRepository.deleteById(parsedId);

        return ResponseHandler.send(HttpStatus.OK, false, "Users removed successfully", null);
    }

    @PatchMapping(path = "/update/{user_id}")
    public @ResponseBody Object updateUser(@PathVariable String user_id, @RequestBody String bodyJSON ) throws JsonProcessingException {
        User parsedUser = objectMapper.readValue(bodyJSON, User.class);
        int parsedId = parseInt(user_id);
        User springUser = new User();
        springUser.setName(parsedUser.getName());
        springUser.setEmail(parsedUser.getEmail());
        springUser.setId(parsedId);
        userRepository.save(springUser);
        return ResponseHandler.send(HttpStatus.OK, false, "Users updated successfully", springUser);

    }


}
