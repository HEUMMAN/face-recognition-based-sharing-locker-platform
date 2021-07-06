package com.jongsul.fabinetgradle.Controller;

import com.jongsul.fabinetgradle.Service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/imgUpload")
    public String upload(@RequestParam(name = "img") MultipartFile file, HttpServletRequest request) throws IOException, SQLException {
        imageService.upload(file, request);
        return "cabinet";
    }

    @RequestMapping("/getImage")
    @ResponseBody
    public byte[] downloadImg(HttpServletRequest request) throws IOException, SQLException {
        return imageService.download(request);
    }
}
