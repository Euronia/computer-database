package com.excilys.formation.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.util.ServiceUtil;

@Component
public class ComputerAndDtoMapper {

    @Autowired
    private MessageSource messageSource;
    
    ////////// Getters & Setters //////////
    
    public  void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    
    ////////// Methods //////////

    public String localDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(messageSource.getMessage("date.format",null ,LocaleContextHolder.getLocale()));
        if (date == null) {
            return null;
        } else {
            return date.format(formatter);
        }
    }

    public LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(messageSource.getMessage("date.format",null ,LocaleContextHolder.getLocale()));
        if (date == null | date.isEmpty()) {
            return null;
        } else {
            LocalDate returnDate = LocalDate.parse(date);
            return LocalDate.parse(returnDate.format(formatter),formatter);
        }
    }

    public static Page<ComputerDto> computerPageToDtoPage(Page<Computer> computerPage) {
        Page<ComputerDto> returnPage = new Page<ComputerDto>(10);
        ServiceUtil.copyAttributes(computerPage, returnPage);
        returnPage.setElements(computerListToDtoList(computerPage.getElements()));
        return returnPage;
    }

    /**
     * Converts a list from ComputerDto to Computer.
     * 
     * @param listDto the list to convert
     * @return a Computer List
     */
    public List<Computer> dtoListToComputerList(List<ComputerDto> listDto) {
        List<Computer> computers = null;
        if (listDto != null) {
            computers = new ArrayList<>();
            for (ComputerDto computer : listDto) {
                Company company = new Company(computer.getCompanyName());
                company.setId(computer.getCompanyId());
                computers.add(new Computer.Builder(computer.getName()).manufacturer(company)
                        .introduced(stringToLocalDate(computer.getIntroduced()))
                        .discontinued(stringToLocalDate(computer.getDiscontinued())).id(computer.getId()).build());
            }
        }
        return computers;
    }
    
    public Page<ComputerDto> dtoPagetoComputerPage(Page<Computer> pageComputer) {
        Page<ComputerDto> returnPage = new Page<>(10);
        returnPage.setPage(pageComputer.getCurrentPage());
        returnPage.setElementsByPage(pageComputer.getElementsByPage());
        returnPage.setNbPages(pageComputer.getNbPages());
        returnPage.setTotalElements(pageComputer.getTotalElements());
        returnPage.setElements(computerListToDtoList(pageComputer.getElements()));
        return returnPage;
    }

    /**
     * Converts a list from Computer to ComputerDto.
     * 
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
     * 
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
