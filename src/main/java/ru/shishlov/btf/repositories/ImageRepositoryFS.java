package ru.shishlov.btf.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;

@Repository
public class ImageRepositoryFS implements ImageRepository{
    private final ImageRepositoryJpa imageRepositoryJpa;
    @Value(value = "${images.path}")
    private String imagePath;

    public ImageRepositoryFS(ImageRepositoryJpa imageRepositoryJpa){
        this.imageRepositoryJpa = imageRepositoryJpa;
    }

    @Override
    public void save(Image image, String login) {
        Path p = Paths.get(imagePath+"/"+login+"/"+image.getName());
        try {
            if(!Files.exists(p))
            Files.createDirectories(p.getParent());
            Files.write(p, image.getContent());
            image.setLocation(p.toString());
            image.setContent(null);
            imageRepositoryJpa.save(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Image image) {
        Path p = Paths.get(image.getLocation());
        try {
            if(Files.exists(p))
            Files.write(p, image.getContent());
            image.setContent(null);
            imageRepositoryJpa.save(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean(String login) {
        Path p = Paths.get(imagePath + login);
        File f = new File(p.toString());
        try {
            deleteDirectory(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Image> findById(long id){
        return imageRepositoryJpa.findById(id);
    }
}
