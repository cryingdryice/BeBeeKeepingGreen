package com.mysite.BeBeeKeepingGreen.map;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantForm {
    private int id;
    private String plantLocation;

    private String xCoordinate;
    private String yCoordinate;
}
