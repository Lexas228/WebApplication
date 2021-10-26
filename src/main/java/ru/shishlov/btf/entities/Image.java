package ru.shishlov.btf.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "content")
    private byte[] content;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

}
