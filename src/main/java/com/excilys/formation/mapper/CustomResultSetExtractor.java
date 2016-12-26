package com.excilys.formation.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class CustomResultSetExtractor {

    ////////// Constants //////////
    
    public static <T> ResultSetExtractor  singletonExtractor(RowMapper<? extends T> mapper) {
        return rs -> rs.next() ?  mapper.mapRow(rs, 1) : null;
    }

    ////////// Fields //////////

    ////////// Constructors //////////

    ////////// Getters & Setters //////////

    ////////// Methods //////////

    ////////// Builder //////////
}
