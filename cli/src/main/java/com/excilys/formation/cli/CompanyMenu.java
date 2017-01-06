package com.excilys.formation.cli;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This menu allows the user to do an action on the company table. Currently
 * only allows to list companies.
 * 
 * @author Euronia
 * @version 1.0
 */

@Component
public class CompanyMenu implements BaseMenu {

    ////////// Parameters //////////

    @Autowired
    private CompanyService companyService;

    private Scanner scanner = MainMenu.getScanner();
    private Page<CompanyDto> pageCompany;
    private static Logger logger;

    static {
        logger = LoggerFactory.getLogger("cdbLogger");
    }

    ////////// Constructors //////////

    public CompanyMenu() {
    }

    ////////// Methods //////////

    @Override
    public void startMenu () {
        System.out.println("/// Menu compagnie ///" + " \n Souhaitez vous : " + "\n 1- Lister les compagnies "
                + "\n 2- Retourner au menu principal" + "\n 3- Quitter l'application");
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int choice = scanner.nextInt();
        switch (choice) {
        case 1:
            list();
            startMenu();
            break;
        case 2:
            break;
        case 3:
            break;
        default:
            break;
        }
    }

    /**
     * This method prints (in cli) a page.
     */
    public void showPage() {
        pageCompany = companyService.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (CompanyDto company : pageCompany.elements) {
            stringBuilder.append(company.toString()).append("\n");
        }
        stringBuilder.append("Page : ").append(pageCompany.getCurrentPage()).append(" / ")
                .append(pageCompany.nbPages)
                .append("\nOptions :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter");
        System.out.println(stringBuilder.toString());
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
