package com.jongsul.fabinetgradle.Service;


import com.jongsul.fabinetgradle.Domain.Image;

import java.util.List;

public interface ImageService {

    public String join(Image image);
    public List<Image> findOne(String imageName);
}
