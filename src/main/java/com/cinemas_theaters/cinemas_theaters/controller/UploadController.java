package com.cinemas_theaters.cinemas_theaters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @PostMapping
    public ResponseEntity upload(@RequestParam("file")MultipartFile file, HttpServletRequest request){

        String context = request.getServletContext().getRealPath("/");

        File path = new File(context + File.separator + file.getOriginalFilename());

        try (FileOutputStream os = new FileOutputStream(path)){
            os.write(file.getBytes());
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(null);
    }
}
