package ru.shishlov.btf.entities;


import lombok.*;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "people")
public class PersonEntity extends BasicEntity{

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    private PersonInformationEntity personInformation;

    @ManyToMany(mappedBy = "persons", cascade = CascadeType.REMOVE)
    private List<DialogEntity> dialogs;

}
