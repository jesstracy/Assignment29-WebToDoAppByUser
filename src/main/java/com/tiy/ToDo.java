package com.tiy;

import javax.persistence.*;

/**
 * Created by jessicatracy on 9/15/16.
 */
@Entity
@Table(name = "todos")
public class ToDo {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User user;

    @Column(nullable = false)
    String text;

    @Column(nullable = false)
    boolean isDone;

    public ToDo() {
    }

    public ToDo(String text, User user) {
        this.text = text;
        this.isDone = false;
        this.user = user;
    }
}



