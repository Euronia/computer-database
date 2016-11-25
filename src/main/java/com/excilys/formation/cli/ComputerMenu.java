package com.excilys.formation.cli;

import java.util.Scanner;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.computerservice.ComputerService;
import com.excilys.formation.service.computerservice.computerserviceimpl.ComputerServiceImpl;
import com.excilys.formation.util.MenuUtil;

/**
 * This menu allows the user to do an action on the computer table. Currently
 * allows to list computer , see specific computer, add/delete/update computer.
 * 
 * @author Euronia
 * 
 */

public class ComputerMenu implements BaseMenu {

    Scanner scanner = MainMenu.getScanner();
    private Page<ComputerDto> pageComputer;
    private ComputerService computerService;

    public ComputerMenu() {
        computerService = new ComputerServiceImpl();
    }
    /**
     * Starts the Menu for the Computer part Allows an user to
     * list/delete/create/update computers
     */
    @Override
    public void startMenu() {
        System.out.println("/// Menu or1dinateur ///" + " \n Souhaitez vous : " + "\n 1- Lister les ordinateurs "
                + "\n 2- Montrer l'ordinateur possédant l'id x" + "\n 3- Créer un ordinateur "
                + "\n 4- Mettre à jour un ordinateur " + "\n 5- Supprimer un ordinateur "
                + "\n 6- Retourner au menu principal" + "\n 7- Quitter l'application");
        while (!scanner.hasNextInt())
            scanner.next();
        int choice = scanner.nextInt();
        switch (choice) {
        case 1:
            list();
            break;
        case 2:
            info();
            break;
        case 3:
            create();
            break;
        case 4:
            update();
            break;
        case 5:
            delete();
            break;
        case 6:
            MainMenu main = new MainMenu();
            main.startMenu();
            break;
        case 7:
            break;
        default:
            startMenu();
            break;
        }
    }
    
    public void list() {
        boolean continueLoop = true;
        pageComputer = new Page<>(10);
        while (continueLoop) {
            showComputerPage();
            continueLoop = MenuUtil.manageNavigation(pageComputer);
        }
        startMenu();
    }
    
    public void create() {
        ComputerDto computerDto = new ComputerDto();
        System.out.println("Entrez le nom de l'ordinateur");
        String name = "";
        while (name.isEmpty()) {
            name = scanner.nextLine();
        }
        computerDto.setName(name);
        System.out.println(
                "Vous pouvez entrez une date de début de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        computerDto.setIntroduced(MenuUtil.inputDate().toString());
        System.out.println(
                "Vous pouvez entrez une date d'arrêt de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        computerDto.setDiscontinued(MenuUtil.inputDate().toString());
        System.out.println("Entrez l'id de la compagnie fabricant l'ordinateur : ");
        computerDto.setCompanyId(MenuUtil.waitForInt());
        try {
            computerService.create(computerDto);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        startMenu();
    }
    
    private void showComputerPage() {
        try {
            computerService.getPage(pageComputer);
            StringBuilder stringBuilder = new StringBuilder();
            for (ComputerDto computer : pageComputer.elements) {
                stringBuilder.append(computer.toString()).append("\n");
            }
            stringBuilder.append("Page : ").append(pageComputer.getCurrentPage()).append(" / ").append(pageComputer.nbPages)
            .append("\nOptions :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter");
            System.out.println(stringBuilder.toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        
    }
    
    public void info() {
        System.out.println("Quelle est l'id de l'ordinateur auquel vous souhaitez acceder ?");
        scanner.nextLine();
        String infoId = MenuUtil.waitForLine();
        int idToShow = -1;
        if (MenuUtil.isInteger(infoId)) {
            idToShow = Integer.parseInt(infoId);
        }
        if (idToShow >= 1) {
            try {
                ComputerDto computer = computerService.getById(idToShow);
                if (computer != null) {
                    System.out.println(new StringBuilder().append("Nom : ").append(computer.getName())
                            .append("\nDate de début de production : ").append(computer.getIntroduced())
                            .append("\nDate de fin de production : ").append(computer.getDiscontinued())
                            .append("\nId de la compagnie : ").append(computer.getCompanyId()).toString());
                } else {
                    System.out.println("Aucun ordinateur trouvé");
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        startMenu();
    }
    
    public void update() {
        System.out.println("Quelle est l'id de l'ordinateur que vous souhaitez modifier ?");
        scanner.nextLine();
        String input = MenuUtil.waitForLine();
        int idToUpdate = -1;
        if (MenuUtil.isInteger(input)) {
            idToUpdate = Integer.parseInt(input);
        }
        if (idToUpdate >= 1) {
            try {
                ComputerDto computerDto = computerService.getById(idToUpdate);
                if (computerDto != null) {
                    // NAME
                    System.out.println(new StringBuilder().append("Entrez un nouveau nom si vous souhaitez le changer (")
                          .append(computerDto.getName()).append(") :").toString());  
                    String newName = scanner.nextLine();
                    if (!newName.isEmpty()) {
                        computerDto.setName(newName);
                    }
                    // INTRODUCED
                    System.out.println(new StringBuilder().append("Entrez une nouvelle date de début de production (")
                            .append(computerDto.getIntroduced())
                            .append(") au format aaaa-mm-jj (\"null\" pour retirer la date):").toString());
                    computerDto.setIntroduced(MenuUtil.inputNewDate(computerDto.getIntroduced()));
                    // DISCONTINUED
                    System.out.println(new StringBuilder().append("Entrez une nouvelle date de fin de production (")
                            .append(computerDto.getDiscontinued())
                            .append(") au format aaaa-mm-jj (\"null\" pour retirer la date):").toString());
                    computerDto.setDiscontinued(MenuUtil.inputNewDate(computerDto.getDiscontinued()));
                    // COMPANY ID
                    System.out.println("Vous pouvez entrer un nouvel id de compagnie (" + computerDto.getCompanyId() + ") :");
                    String newCompanyId = scanner.nextLine();
                    if (!newCompanyId.isEmpty() && MenuUtil.isInteger(newCompanyId)) {
                        computerDto.setCompanyId(Integer.parseInt(newCompanyId));
                    }
                    computerService.update(computerDto);
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun ordinateur trouvé pour cet id");
        }
        startMenu();
    }

    public void delete() {
        System.out.println("Quelle est l'id de l'ordinateur à supprimer ? (Entrée pour annuler");
        scanner.nextLine();
        String input = MenuUtil.waitForLine();
        int idToDelete = -1;
        if (MenuUtil.isInteger(input)) {
            idToDelete = Integer.parseInt(input);
        }
        if (idToDelete >= 1) {
            try {
                computerService.delete(idToDelete);
                System.out.println("Ordinateur supprimé");
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        startMenu();
    }
    
    @Override
    public void exitMenu() {

    }

}
