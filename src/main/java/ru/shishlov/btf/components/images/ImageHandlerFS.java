package ru.shishlov.btf.components.images;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.entities.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;


@Component
@PropertySource(value = "classpath:application.properties")
public class ImageHandlerFS implements ImageHandler {
    @Value(value = "${images.path}")
    private String imagePath;

    @Override
    public void prepareForSave(Image image, String login) {
        Path p = Paths.get(imagePath+"/"+login+"/"+image.getName());
        try {
            Files.createDirectories(p.getParent());
            Files.write(p, image.getContent());
            image.setLocation(p.toString());
            image.setContent(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareForUpdate(Image image) {
        Path p = Paths.get(image.getLocation());
        try {
            Files.write(p, image.getContent());
            image.setContent(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareForDelete(String login) {
        Path p = Paths.get(imagePath + login);
        File f = new File(p.toString());
        try {
            deleteDirectory(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
