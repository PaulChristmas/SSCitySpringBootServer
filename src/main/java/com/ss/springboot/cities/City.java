package com.ss.springboot.cities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cities")
public class City implements Serializable{

    private static final long serialVersionUID = -1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @SequenceGenerator(
            name = "city_generator",
            sequenceName = "city_sequence",
            initialValue = 1000
    )
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "locked")
    private boolean locked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    protected City() {}

    public City(String name, boolean locked) {
        this.name = name;
        this.locked = locked;
    }

    @Override
    public String toString() {
        String lock = locked? "locked" : "unlocked";
        return String.format(
                "City: '%s', changes '%s'(id: %d)",
                name, lock, id);
    }
}