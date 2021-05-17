package com.jongsul.fabinetgradle.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ImageDTO {

    private String name;
    private MultipartFile img;
}
