package com.excilys.formation.util;

import java.sql.Timestamp;
import java.time.LocalDate;

public class DateConverter {
    
    public static LocalDate fromTimestampToLocalDate(Timestamp pTimestamp) {
        if (pTimestamp != null) {
            return pTimestamp.toLocalDateTime().toLocalDate();
        }
        return null;
    }
    
    public static Timestamp fromLocalDateToTimestamp(LocalDate pLocalDate) {
        if (pLocalDate != null) {
            return Timestamp.valueOf(pLocalDate.atStartOfDay());
        }
    return null;
    }
    
}
