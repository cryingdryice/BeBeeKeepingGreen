package com.mysite.BeBeeKeepingGreen.map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BeeHive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String plantLocation;
    // 좌표 넣기
    @Column(nullable = true)
    private String x;
    @Column(nullable = true)
    private String y;
}
