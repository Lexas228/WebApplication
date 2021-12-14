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

    @ManyToMany
    @JoinTable(name = "person_dialogs",
    joinColumns = @JoinColumn(name = "dialog_id"),
    inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<PersonEntity> personEntities;

    @OneToMany(mappedBy = "dialog")
    private List<MessageEntity> messageEntities;
}
