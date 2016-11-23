package com.excilys.formation.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.dto.ComputerDto;

public class ComputerValidator {

	//TODO replace List with a Map 
    public static List<String> validate(ComputerDto pcomputer) {
        List<String> errors = new ArrayList<String>();
        errors.addAll(nameIsValid(pcomputer.getName()));
        errors.addAll(dateIsValid(pcomputer.getIntroduced(),null));
        errors.addAll(dateIsValid(pcomputer.getDiscontinued(),pcomputer.getIntroduced()));
        return errors;
    }
    
    private static List<String> nameIsValid(String pname) {
        List<String> errors = new ArrayList<String>();
        if (pname.trim().length() >= 3) {
            errors.add("The name must be atleast 3 characters long");
        }
        return errors;
    }
    
    private static List<String> dateIsValid(String date, String beforeDate ) {
        List<String> errors = new ArrayList<String>();
        if (date != null) {
            try {
			LocalDate localDate = LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                errors.add("Invalid date");
            }
        }
        return errors;
    }   
}