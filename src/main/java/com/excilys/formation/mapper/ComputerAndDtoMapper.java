package com.excilys.formation.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;

public class ComputerAndDtoMapper {
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ////////// Methods //////////
    
    public static String localDateToString(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
    
    public static LocalDate stringToLocalDate(String date) {
        if (date == null) {
            return null;
        } else {
            return LocalDate.parse(date,formatter);
        }
    }
    
    /**
     * Converts a list from ComputerDto to Computer.
     * @param listDto the list to convert
     * @return a Computer List
     */
    public static List<Computer> dtoListToComputerList(List<ComputerDto> listDto) {
        List<Computer> computers = null;
        if (listDto != null) {
            computers = new ArrayList<>();
            for (ComputerDto computer : listDto) {
                Company company = new Company(computer.getCompanyName()); 
                company.setId(computer.getCompanyId());
                computers.add(new Computer.ComputerBuilder(computer.getName()).manufacturer(company)
                        .introduced(stringToLocalDate(computer.getIntroduced())).discontinued(stringToLocalDate(computer.getDiscontinued())).id(computer.getId())
                        .build());
            }
        }
        return computers;
    }

    /**
     * Converts a list from Computer to ComputerDto.
     * @param pList the list to convert
     * @return a ComputerDto List
     */
    public static List<ComputerDto> computerListToDtoList(List<Computer> pList) {
        List<ComputerDto> computersDto = null;
        if (pList != null) {
            computersDto = new ArrayList<>();
            for (Computer computer : pList) {
                ComputerDto computerDto = new ComputerDto();
                computerDto.setId(computer.getId());
                computerDto.setName(computer.getName());
                if (computer.getIntroduced() != null) {
                    computerDto.setIntroduced(computer.getIntroduced().toString());
                } else {
                    computerDto.setIntroduced(null);
                }
                if (computer.getDiscontinued() != null) {
                    computerDto.setDiscontinued(computer.getDiscontinued().toString());
                } else {
                    computerDto.setDiscontinued(null);
                }
                Company company = computer.getManufacturer();
                computerDto.setCompanyId(company.getId());
                computerDto.setCompanyName(company.getName());
                computersDto.add(computerDto);
            }
        }
        return computersDto;
    }
    
    /**
     * Transform a Computer into a ComputerDto.
     * @param pComp the Computer we want to transform
     * @return the parameter computer transformed into a ComputerDto
     */
    public static ComputerDto computerToDto(Computer pComp) {
        ComputerDto computerDto = new ComputerDto();
        computerDto.setId(pComp.getId());
        computerDto.setName(pComp.getName());
        if (pComp.getIntroduced() != null) { 
            computerDto.setIntroduced(pComp.getIntroduced().toString());
        }
        if (pComp.getDiscontinued() != null) {
            computerDto.setDiscontinued(pComp.getDiscontinued().toString());
        }        
        Company company = pComp.getManufacturer();
        computerDto.setCompanyId(company.getId());
        computerDto.setCompanyName(company.getName());
        return computerDto;
    }
}
