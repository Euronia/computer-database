package com.excilys.formation.util;

import java.sql.Timestamp;
import java.time.LocalDate;

public class DateConverter {

    public static LocalDate fromTimestampToLocalDate(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toLocalDateTime().toLocalDate();
        }
        return null;
    }

    public static Timestamp fromLocalDateToTimestamp(LocalDate localDate) {
        if (localDate != null) {
            return Timestamp.valueOf(localDate.atStartOfDay());
        }
        return null;
    }
}