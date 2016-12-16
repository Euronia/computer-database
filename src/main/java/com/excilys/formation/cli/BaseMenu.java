package com.excilys.formation.cli;

/**
 * Interface of a menu. A menu must have a method to start it and a method to
 * end it.
 * 
 * @author Euronia
 * @version 1.0
 */

public interface BaseMenu {

    /**
     * This method launch the menu in the cli.
     */
    public void startMenu();

    /**
     * This method closes the menu in the cli.
     */
    public void exitMenu();

}
