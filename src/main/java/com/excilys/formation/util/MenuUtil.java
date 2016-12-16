package com.excilys.formation.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.excilys.formation.cli.MainMenu;
import com.excilys.formation.pagination.Page;

/**
 * A utilitary class for menus.
 * 
 * @author Euronia
 *
 */
public class MenuUtil {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

    /**
     * Wait until the scanner has a new line and returns it.
     * 
     * @return the next line from the scanner
     */
    public static String waitForLine() {
        while (!MainMenu.scanner.hasNextLine()) {
            MainMenu.scanner.next();
        }
        return MainMenu.scanner.nextLine();
    }

    /**
     * Wait until the scanner has a new int and returns it.
     * 
     * @return the next int from the scanner
     */
    public static int waitForInt() {
        while (!MainMenu.scanner.hasNextInt()) {
            MainMenu.scanner.next();
        }
        return MainMenu.scanner.nextInt();
    }

    /**
     * Gets a new line from the scanner until it's empty, "null" or a valid date
     * and returns the date.
     * 
     * @return a LocalDate or null
     */
    public static LocalDate inputDate() {
        LocalDate date = null;
        boolean valid = false;
        while (date == null && !valid) {
            String line = MainMenu.scanner.nextLine();
            if (!line.isEmpty() && !line.equals("null")) {
                try {
                    date = LocalDate.parse(line, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("La date est au mauvais format");
                }
            } else {
                valid = true;
            }
        }
        return date;
    }

    /**
     * Asks for a new date but with the possibility to keep the old one.
     * 
     * @param localDate the old date
     * @return a LocalDate or null
     */
    public static String inputNewDate(String localDate) {
        String date = null;
        boolean valid = false;
        while (date == null && !valid) {
            String line = MainMenu.scanner.nextLine();
            if (!line.isEmpty() && !line.equals("null")) {
                try {
                    date = LocalDate.parse(line, formatter).toString();
                } catch (DateTimeParseException e) {
                    System.out.println("La date est au mauvais format");
                }
            } else if (line.isEmpty()) {
                date = localDate;
                valid = true;
            } else {
                valid = true;
            }
        }
        return date;
    }

    /**
     * Checks if a string is an integer.
     * 
     * @param pStringToCheck the String to check
     * @return a boolean
     */
    public static boolean isInteger(String stringToCheck) {
        if (stringToCheck == null) {
            return false;
        }
        int length = stringToCheck.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        // we check if the string starts with "-" and the length is > 1
        if (stringToCheck.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        // we check each char of the string to see if it's a number
        for (; i < length; i++) {
            char c = stringToCheck.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Manage the page navigation in the menus.
     * 
     * @param page the Page on which to operate
     * @return a boolean indicating if the operation was successful
     */
    public static boolean manageNavigation(Page<?> page) {
        boolean ok = false;
        while (!ok) {
            int nextOption = MenuUtil.waitForInt();
            if (nextOption == 1) {
                ok = page.prevPage();
                if (!ok) {
                    System.out.println("Vous êtes déjà sur la première page");
                }
            } else if (nextOption == 2) {
                ok = page.nextPage();
                if (!ok) {
                    System.out.println("Vous êtes déjà sur la dernière page");
                }
            } else if (nextOption == 3) {
                MainMenu.scanner.nextLine();
                System.out.print("Entrez le numéro de la page :");
                String pageString = "";
                while (pageString.isEmpty() && !MenuUtil.isInteger(pageString)) {
                    pageString = MainMenu.scanner.nextLine();
                }
                ok = page.setPage(Integer.parseInt(pageString));
                if (!ok) {
                    System.out.println("Cette page n'existe pas");
                }
            } else if (nextOption == 4) {
                return false;
            }
        }
        return true;
    }
}