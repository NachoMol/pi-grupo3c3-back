package com.explorer.equipo3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Long id;
    private Date checkin;
    private Date checkout;
    private Double price;
    private Long user_id;
    private Long product_id;
    private Long city_id;
}
