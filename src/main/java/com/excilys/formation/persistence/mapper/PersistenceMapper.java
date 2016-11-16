package com.excilys.formation.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.util.DateConverter;

public class PersistenceMapper {
    
    public static List<Computer> mapResultsToComputerList(ResultSet pResultSet) throws SQLException {
        List<Computer> computerList = null;
        if (pResultSet != null) {
            computerList = new ArrayList<Computer>();
            while (pResultSet.next()) {
                Computer computer = extractComputer(pResultSet);
                computerList.add(computer);
            }
        }
        return computerList;
    }
    
    public static List<Company> mapResultsToCompanyList(ResultSet pResultSet) throws SQLException {
        List<Company> companyList = null;
        if (pResultSet != null) {
            companyList = new ArrayList<Company>();
            while (pResultSet.next()) {
                Company company = extractCompany(pResultSet);
                companyList.add(company);
            }
        }
        return companyList;
    }
    
    public static Company mapResultToCompany(ResultSet pResultSet) throws SQLException {
        Company company = null;
        if (pResultSet != null) {
            if (pResultSet.next()) {
                company = extractCompany(pResultSet);
            }
        }
        return company;
    }
    
    public static Computer mapResultToComputer(ResultSet pResultSet) throws SQLException {
        Computer computer = null;
        if (pResultSet != null) {
            if (pResultSet.next()) {
                computer = extractComputer(pResultSet);
            }
        }
        return computer;
    }
    
    private static Computer extractComputer(ResultSet pResultSet) throws SQLException {
        Company company = new Company(pResultSet.getString("companyName"));
        company.setId(pResultSet.getInt("companyId"));
        
        Computer computer = new Computer.ComputerBuilder(pResultSet.getString("name"),company)
                 .setId(pResultSet.getInt("id"))
                 .setDiscontinued(DateConverter.fromTimestampToLocalDate(pResultSet.getTimestamp("discontinued")))
                 .setIntroduced(DateConverter.fromTimestampToLocalDate(pResultSet.getTimestamp("introduced"))).build();
        return computer;            
    }
    
    private static Company extractCompany(ResultSet pResultSet) throws SQLException { 
        Company company = new Company(pResultSet.getString("name"));
        company.setId(pResultSet.getInt("id"));
        return company;
    }
}
