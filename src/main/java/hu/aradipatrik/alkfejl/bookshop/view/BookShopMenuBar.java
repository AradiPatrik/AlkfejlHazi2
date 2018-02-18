package hu.aradipatrik.alkfejl.bookshop.view;

import hu.aradipatrik.alkfejl.bookshop.model.bean.Book;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Customer;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Purchase;
import hu.aradipatrik.alkfejl.bookshop.view.dialogs.AddCustomerDialog;
import hu.aradipatrik.alkfejl.bookshop.view.dialogs.BuyBookDialog;
import hu.aradipatrik.alkfejl.bookshop.view.dialogs.SellBookDialog;
import hu.aradipatrik.alkfejl.bookshop.view.tablemodels.BookTableModel;
import hu.aradipatrik.alkfejl.bookshop.view.tablemodels.CustomerTableModel;
import hu.aradipatrik.alkfejl.bookshop.view.tablemodels.PurchaseTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * A {@link BookShopMenuBar} osztály reprezentálja a menüt.
 * Azért teheti ezt meg, mert a {@link JMenuBar} osztályból származik.
 * Így a menüre a this-szel hivatkozhatunk.
 */
public class BookShopMenuBar extends JMenuBar implements ActionListener {

    // A JMenuBar implementálja a Serializable interfészt, emiatt kell a serialVersionUID
    // Ez máshol is elõfordulhat, feloldása: az osztály mellett bal oldalt sárga
    // felkiáltójel Eclipse-ben, ráklikkelve 'Add generated serial version id'.
    // Kipróbálhatjuk, ha ezt a sort töröljük.
    private static final long serialVersionUID = 2973555574160940115L;
    private BookShopGUI gui;

    public BookShopMenuBar(BookShopGUI gui) {
        super();
        this.gui = gui;

        // Három menüpontot gyártunk általánosan, a createMenuPoint metódussal
        createMenuPoint(Labels.customer, Labels.add_customer, Labels.list_customers);
        createMenuPoint(Labels.book, Labels.buy_book, Labels.list_books);
        createMenuPoint(Labels.sell, Labels.sell_book, Labels.list_sold_books);
    }

    private void createMenuPoint(String name, String... subnames) {
        // Létrehozunk egy menupontot az elsõ paraméter alapján
        JMenu menu = new JMenu(name);

        // A menupontot hozzáadjuk a BookShopMenuBar-hoz
        this.add(menu);

        // Az egyes menu itemeket a maradék paraméter értékeivel hozzuk létre
        for (String subname : subnames) {
            JMenuItem menuItem = new JMenuItem(subname);

            menu.add(menuItem);

            // Minden egyes menu itemet figyelünk
            // A menu itemek esetén a megfigyelést az ActionListener interfész
            // biztosítja, ezért a menubar implementálja ezt az interfészt és
            // felülírja az actionPerformed metódust
            menuItem.addActionListener(this);
        }
    }

    /*
     * Az interfészekhez tartozó metódusokat célszerû generálni, azután, hogy
     * megadtuk az implements kulcsó után az interfészt,
     * Jobb klikk + Source + Override/Implement
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        System.out.println("A következõ menüt meghívta a felhasználó:" + actionCommand);

        if (actionCommand.equals(Labels.add_customer)) {
            // Ha az új ügyfél felvételét választották, akkor egy
            // AddCustomerDialog-ot indítunk
            new AddCustomerDialog(gui, true);
        } else if (actionCommand.equals(Labels.list_customers)) {
            List<Customer> customers = gui.getController().getCustomers();

            // Csinálunk egy táblázatot, a CustomerTableModel alapjan, ami
            // megkapja a controltol a customereket
            JTable table = new JTable(new CustomerTableModel(customers));

            // A táblazatot rárakjuk egy ScrollPane-re, így ha az túl nagy lenne
            // az ablak méretéhez képest, akkor is görgetheto lesz
            JScrollPane container = new JScrollPane(table);

            // Ezt a ScrollPane-t állítjuk be a fõablak tartalmának
            gui.setActualContent(container);
        } else if(actionCommand.equals(Labels.buy_book)) {
            new BuyBookDialog(gui, true);
        } else if(actionCommand.equals(Labels.list_books)) {
            List<Book> books = gui.getController().getBooks();
            JTable table = new JTable(new BookTableModel(books));
            JScrollPane container = new JScrollPane(table);
            gui.setActualContent(container);
        } else if (actionCommand.equals(Labels.sell_book)) {
            new SellBookDialog(gui, true);
        } else if (actionCommand.equals(Labels.list_sold_books)) {
            List<Purchase> purchases = gui.getController().getPurchases();
            JTable table = new JTable(new PurchaseTableModel(purchases));
            JScrollPane container = new JScrollPane(table);
            gui.setActualContent(container);
        }
    }

}
