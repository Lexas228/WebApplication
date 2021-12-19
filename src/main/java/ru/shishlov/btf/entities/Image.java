package ru.shishlov.btf.entities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "image")
public class Image extends BasicEntity{

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "content")
    private byte[] content;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

}
