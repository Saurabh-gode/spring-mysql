package com.example.springmysql.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class ResponseHandler {
    public static ResponseEntity<Object> send(HttpStatus status, boolean error, String message, Object responseObject){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("isSuccess", !error);
            map.put("message", message);
            map.put("data", responseObject);

            return new ResponseEntity<Object>(map,status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess",false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<Object>(map,status);
        }

    }
}
