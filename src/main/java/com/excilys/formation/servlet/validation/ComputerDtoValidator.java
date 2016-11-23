package com.excilys.formation.servlet.validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.excilys.formation.dto.ComputerDto;

public class ComputerDtoValidator {
    
    public boolean validate (ComputerDto computer) {
        
        return validateDate(computer.getIntroduced(),computer.getDiscontinued());
    }
    
    private boolean validateDate (String introduced, String discontinued) {
        LocalDate introducedDate;
        LocalDate discontinuedDate;
        final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        try {
        introducedDate = LocalDate.parse(introduced, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return false;
        }
        try {
        discontinuedDate = LocalDate.parse(discontinued, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return false;
        }
        Period diff = Period.between(introducedDate, discontinuedDate);
        return diff.isNegative();
    }
    
}
