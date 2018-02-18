package hu.aradipatrik.alkfejl.bookshop;

import hu.aradipatrik.alkfejl.bookshop.controller.BookShopController;

/**
 * Az osztály az alkalmazás belépési pontja, az alkalmazás indításáért felel.
 */
public class Main {

    /**
     * Az alkalmazás belépési pontja.
     *
     * @param args A parancssori argumentumok listája.
     */
    public static void main(String[] args) {
        BookShopController controller = new BookShopController();
        controller.startDesktop();
    }

}
