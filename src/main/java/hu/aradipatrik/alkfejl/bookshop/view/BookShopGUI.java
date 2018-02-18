package hu.aradipatrik.alkfejl.bookshop.view;

import hu.aradipatrik.alkfejl.bookshop.controller.BookShopController;

import java.awt.Container;

import javax.swing.JFrame;

/**
 * Ez az osztály indítja el a GUI-t, és ezen keresztül érhetõ el a control osztály a többi gui elem számára.
 */
public class BookShopGUI {

    private JFrame window;
    private BookShopController controller;

    public BookShopGUI(BookShopController controller) {
        this.controller = controller;
    }

    public void startGUI() {
        // A GUI kirajzolására és az azon történõ események kezelésére a Java egy külön szálat használ.
        // Ezen a szálon indítjuk el a createAndShowGUI() metódust.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {

        // A JFrame egy magas szintû konténer, egy ablak címmel és kerettel.
        window = new JFrame(Labels.main_window_title);

        // Ha bezárjuk az ablakot, akkor alapértelmezésben azt csak elrejtjuk.
        // Ezt a viselkedést módosítjuk arra, hogy az ablak ténylegesen záródjon be.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gyártunk egy bookMenuBar objektumot
        BookShopMenuBar bookMenuBar = new BookShopMenuBar(this);

        // Amit rárakunk a Book Shop ablakunkra
        window.setJMenuBar(bookMenuBar);

        // Az ablaknak beállítjuk a méretét
        window.setSize(800,600);

        // Készen vagyunk, megjeleníthetjük az ablakot
        window.setVisible(true);
    }

    /**
     * A kapott containert beállítja a fõablak {@link JFrame} contentjeként.
     */
    public void setActualContent(Container container) {
        // tartalmat mindig a content pane-hez kell hozzáadni
        // vagy content pane-ként beállítani.
        window.setContentPane(container);
        window.setVisible(true);
    }

    /**
     * Visszaadja az alkalmazás fõablakát. A metódus az alkalmazás belsõ
     * vázának, infrastruktúrájának részét képezi, minden alkalmazásban szükség
     * van rá.
     */
    public JFrame getWindow() {
        return window;
    }

    /**
     * Visszaadja az alkalmazás controllerét. A metódus az alkalmazás belsõ
     * vázának, infrastruktúrájának részét képezi, minden alkalmazásban szükség
     * van rá.
     */
    public BookShopController getController() {
        return controller;
    }

}
