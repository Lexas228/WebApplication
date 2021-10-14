package ru.shishlov.btf.entities;


import lombok.*;
import ru.shishlov.btf.components.validators.FieldMatch;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "people")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    private PersonInformationEntity personInformation;

}
