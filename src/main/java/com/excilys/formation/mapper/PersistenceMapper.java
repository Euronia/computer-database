package com.excilys.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.util.DateConverter;

public class PersistenceMapper {
    
    public static List<Computer> mapResultsToComputerList(ResultSet resultSet) throws SQLException {
        List<Computer> computerList = null;
        if (resultSet != null) {
            computerList = new ArrayList<Computer>();
            while (resultSet.next()) {
                Computer computer = extractComputer(resultSet);
                computerList.add(computer);
            }
        } 
        return computerList;
    }
    
    public static List<Company> mapResultsToCompanyList(ResultSet resultSet) throws SQLException {
        List<Company> companyList = null;
        if (resultSet != null) {
            companyList = new ArrayList<Company>();
            while (resultSet.next()) {
                Company company = extractCompany(resultSet);
                companyList.add(company);
            }
        }
        return companyList;
    }
    
    public static Company mapResultToCompany(ResultSet resultSet) throws SQLException {
        Company company = null;
        if (resultSet != null) {
            if (resultSet.next()) {
                company = extractCompany(resultSet);
            }
        }
        return company;
    }
    
    public static Computer mapResultToComputer(ResultSet resultSet) throws SQLException {
        Computer computer = null;
        if (resultSet != null) {
            if (resultSet.next()) {
                computer = extractComputer(resultSet);
            }
        }
        return computer;
    }
    
    private static Computer extractComputer(ResultSet resultSet) throws SQLException {
        Company company = new Company(resultSet.getString("companyName"));
        company.setId(resultSet.getInt("companyId"));
        
        Computer computer = new Computer.ComputerBuilder(resultSet.getString("name"),company)
                 .setId(resultSet.getInt("id"))
                 .setDiscontinued(DateConverter.fromTimestampToLocalDate(resultSet.getTimestamp("discontinued")))
                 .setIntroduced(DateConverter.fromTimestampToLocalDate(resultSet.getTimestamp("introduced"))).build();
        return computer;            
    }
    
    private static Company extractCompany(ResultSet resultSet) throws SQLException { 
        Company company = new Company(resultSet.getString("name"));
        company.setId(resultSet.getInt("id"));
        return company;
    }
}
