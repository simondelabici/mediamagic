package org.kd1sgr.mediamagic.services;

import org.kd1sgr.mediamagic.model.Image;
import org.kd1sgr.mediamagic.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public ImageService() {}

    @Transactional
    public void saveImage(Image image) {
        imageRepository.save( image );
    }

    public List<Image> findImages() {

        return imageRepository.findImages();
    }



}