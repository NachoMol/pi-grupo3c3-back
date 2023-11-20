package com.explorer.equipo3.validations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DatesReservationValidator.class)
@Documented
public @interface ValidateReservationDates {
    String message() default "Las fechas de reserva no son válidas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
