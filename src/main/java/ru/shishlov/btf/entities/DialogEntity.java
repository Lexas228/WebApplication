package ru.shishlov.btf.entities;

import liquibase.pro.packaged.J;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Dialog")
public class DialogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "first_person_id")
    private PersonEntity firstPerson;

    @OneToOne
    @JoinColumn(name = "second_person_id")
    private PersonEntity secondPerson;


}
