package com.excilys.formation.cli;

import java.util.Scanner;

import com.excilys.formation.cli.util.MenuUtil;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.CompanyServiceImpl;
import com.excilys.formation.service.exception.ServiceException;

/**
 * This menu allows the user to do an action on the company table. Currently
 * only allows to list companies.
 * 
 * @author Euronia
 * @version 1.0
 */

public class CompanyMenu implements BaseMenu {

    private static CompanyService companyService = new CompanyServiceImpl();
     MainMenu main = new MainMenu();
    Scanner scanner = main.getScanner();
    private Page<CompanyDto> pageCompany;

    @Override
    public void startMenu() {
        System.out.println("/// Menu compagnie ///" + " \n Souhaitez vous : " + "\n 1- Lister les compagnies "
                + "\n 2- Retourner au menu principal" + "\n 3- Quitter l'application");
        while (!scanner.hasNextInt())
            scanner.next();
        int choice = scanner.nextInt();
        switch (choice) {
        case 1:
            list();
            startMenu();
            break;
        case 2:
            main.startMenu();
            break;
        case 3:
            break;
        default:
            startMenu();
            break;
        }
    }
    
    public void showPage() {
        try {
            companyService.getPage(pageCompany);
            StringBuilder stringBuilder = new StringBuilder();
            for (CompanyDto company : pageCompany.elements) {
                stringBuilder.append(company.toString()).append("\n");
            }
            stringBuilder.append("Page : ").append(pageCompany.getCurrentPage()).append(" / ").append(pageCompany.nbPages)
                .append("\nOptions :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter");
            System.out.println(stringBuilder.toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    public void list() {
        pageCompany = new Page<>(10);
        do {
            showPage();
        } while (MenuUtil.manageNavigation(pageCompany));
        startMenu();
    }

    @Override
    public void exitMenu() {

    }

}
