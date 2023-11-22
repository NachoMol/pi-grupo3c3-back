package com.explorer.equipo3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesDTO {

    private Long id;
    private Long user_id;
    private Long product_id;
    private Date created_at;
    private Date updated_at;

}
