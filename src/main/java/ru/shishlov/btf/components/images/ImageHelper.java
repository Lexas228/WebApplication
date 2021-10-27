package ru.shishlov.btf.components.images;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
@Setter
public class ImageHelper {
    private ImageHandler imageHandler;
    private ImageConvertor imageConvertor;

}
