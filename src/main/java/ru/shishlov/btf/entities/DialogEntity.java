package ru.shishlov.btf.entities;

import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "Dialog")
public class DialogEntity extends BasicEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "person_dialogs",
            joinColumns = @JoinColumn(name = "dialog_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<PersonEntity> persons;

    @OneToMany(mappedBy = "dialog", cascade = CascadeType.ALL)
    private List<MessageEntity> messageEntities;
}
