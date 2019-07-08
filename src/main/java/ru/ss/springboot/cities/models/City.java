package ru.ss.springboot.cities.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cities")
@NoArgsConstructor
@RequiredArgsConstructor
public class City implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @SequenceGenerator(
            name = "city_generator",
            sequenceName = "city_sequence",
            initialValue = 1000
    )
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "locked")
    private boolean locked;

}