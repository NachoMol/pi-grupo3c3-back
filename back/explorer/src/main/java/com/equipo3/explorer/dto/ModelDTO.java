package com.equipo3.explorer.dto;

import com.equipo3.explorer.model.Brand;
import com.equipo3.explorer.model.CarType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModelDTO {

    private Long id;
    private String name;
    private String seats;
    private Brand brand;
    private CarType carType;

}
