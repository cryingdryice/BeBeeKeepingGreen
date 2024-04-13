package com.mysite.BeBeeKeepingGreen.record;

import com.mysite.BeBeeKeepingGreen.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class HiveRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private SiteUser owner;

    private String content;
}
