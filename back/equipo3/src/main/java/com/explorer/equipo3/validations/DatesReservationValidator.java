package com.explorer.equipo3.validations;

import com.explorer.equipo3.model.Reservation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Date;

public class DatesReservationValidator implements ConstraintValidator<ValidateReservationDates, Reservation> {
    @Override
    public void initialize(ValidateReservationDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext constraintValidatorContext) {
        if (reservation == null){
            return true;
        }
        LocalDate currentDate = LocalDate.now();
        //Validar que la fecha de inicio sea igual a la actual o mayor
        if (reservation.getCheckin() != null && reservation.getCheckin().isBefore(currentDate)){
            return false;
        }
        // Validar que la fecha de fin sea mayor que la fecha de inicio
        if (reservation.getCheckin() != null && reservation.getCheckout() != null &&
                reservation.getCheckout().isBefore(reservation.getCheckin())) {
            return false;
        }

        return true;

    }
}
