package com.explorer.equipo3.model.dto;

import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Product product;
    private List<Image> images;

}
