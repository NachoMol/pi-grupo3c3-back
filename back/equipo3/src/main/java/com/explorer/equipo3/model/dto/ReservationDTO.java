package com.explorer.equipo3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Long id;
    private LocalDate checkin;
    private LocalDate checkout;
    private Long user_id;
    private Long product_id;
    private Long city_id;
}
