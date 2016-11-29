package com.excilys.formation.validation;

import java.util.HashMap;
import java.util.Map;

import com.excilys.formation.dto.PageConstraints;

public class ConstraintsValidator {

    ////////// Methods //////////
    
    public static Map<String,String> validate(PageConstraints constraints) {
        Map<String,String> errors = new HashMap<String,String>();
        String currentError = validateCurrentPage(constraints);
        if (currentError != null) {
            errors.put("currentPage", currentError);
        }
        currentError = validatePerPage(constraints);
        if (currentError != null) {
            errors.put("perPage", currentError);
        }
        return errors;
    }

    public static String validateCurrentPage(PageConstraints constraints) {
        return (constraints.getCurrentPage() < 1) ? "CurrentPage is lower than 1" : null;
    }

    public static String validatePerPage(PageConstraints constraints) {
        return (constraints.getPerPage() < 1) ? "PerPage is lower than 1" : null;
    }
}