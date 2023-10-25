package com.equipo3.explorer.dto;

import com.equipo3.explorer.model.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CarDTO {

    private Long id;
    private Double price;
    private int stock;
    private List<Model> models;
}
