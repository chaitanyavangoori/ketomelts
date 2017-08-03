package com.ketomelts.main.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ketomelts.main.domain.Stock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Chaitanya on 2/26/17.
 */
@Controller
@RequestMapping(value= "/create")
public class CreateController {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/new")
    public void upload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username ) throws IOException {

        byte[] bytes;

        if (!file.isEmpty()) {
            bytes = file.getBytes();
            //store file in storage
        }


        System.out.println(String.format("receive %s from %s", file.getOriginalFilename(), username));
    }
}
