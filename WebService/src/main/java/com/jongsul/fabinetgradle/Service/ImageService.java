package com.jongsul.fabinetgradle.Service;


import com.jongsul.fabinetgradle.Domain.Image;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public interface ImageService {

    String upload(MultipartFile file, HttpServletRequest request) throws SQLException;
    byte[] download(HttpServletRequest request) throws SQLException;
}
