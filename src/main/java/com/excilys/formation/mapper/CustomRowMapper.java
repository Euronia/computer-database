package com.excilys.formation.mapper;

import com.excilys.formation.entity.Computer;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.entity.Company;

public class CustomRowMapper {

    ////////// Constants //////////
    
    public static final RowMapper<Company> MAPPER_COMPANY =
            (rs, i) -> PersistenceMapper.mapResultToCompany(rs);
            
    public static final RowMapper<Computer> MAPPER_COMPUTER = 
            (rs, i) -> PersistenceMapper.mapResultToComputer(rs);

    ////////// Fields //////////

    ////////// Constructors //////////

    ////////// Getters & Setters //////////

    ////////// Methods //////////

    ////////// Builder //////////
}
