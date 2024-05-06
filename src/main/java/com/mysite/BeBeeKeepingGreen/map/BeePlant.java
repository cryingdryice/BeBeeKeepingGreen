package com.mysite.BeBeeKeepingGreen.map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BeePlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String plantLocation;

    private boolean isConfirm;
}
