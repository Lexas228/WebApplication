package ru.shishlov.btf.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "message")
public class MessageEntity extends BasicEntity{

    @ManyToOne()
    @JoinColumn(name = "dialog_id")
    private DialogEntity dialog;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "person_id")
    private PersonEntity whose;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private Timestamp date;
}
