package com.example.shiftbet.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.UUID;

@Service
public class ImageFileService {
    public String saveImage(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            UUID id = UUID.randomUUID();
            String fileName = file.getOriginalFilename().replace(' ', '_');

            String imagePath = "D:\\Mein progectos\\java\\ShiftBet\\src\\main\\resources\\static\\images\\" +id + fileName;
            file.transferTo(new File(imagePath));
            return "/images/" + id + fileName;
        }
        return null;
    }
}
