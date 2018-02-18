package hu.aradipatrik.alkfejl.bookshop.view.tablemodels;

import hu.aradipatrik.alkfejl.bookshop.model.bean.Book;
import hu.aradipatrik.alkfejl.bookshop.view.Labels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Egyedi table modelt az AbstractTableModelbõl tudunk származtatni.
 */
public class BookTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    // Az egyes oszlop fejlécek nevei
    private String[] columnNames = new String[] {
            Labels.author, Labels.title, Labels.year, Labels.category,
            Labels.price, Labels.piece, Labels.ancient };

    List<Book> books;

    public BookTableModel(List<Book> books) {
        super();

        this.books = books;
    }

    /* A table model megvalósításához felül kell írni néhány fontos metódust!
     */

    /**
     * Megadja, hogy hány oszlopa van a táblázatnak.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Megadja, hogy hány sora van a táblázatnak.
     */
    @Override
    public int getRowCount() {
        return books.size();
    }

    /**
     * Megadja, hogy adott oszlopnak mi a neve.
     */
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Megadja, hogy adott sorban és oszlopban milyen érték szerepel.
     */
    @Override
    public Object getValueAt(int row, int col) {
        Book book = books.get(row);
        String askedColumnName = columnNames[col];

        if (askedColumnName.equals(Labels.author)) {
            return book.getAuthor();
        }else if (askedColumnName.equals(Labels.title)) {
            return book.getTitle();
        }else if (askedColumnName.equals(Labels.year)) {
            return book.getYear();
        }else if (askedColumnName.equals(Labels.category)) {
            return book.getCategory();
        }else if (askedColumnName.equals(Labels.price)) {
            return book.getPrice();
        }else if (askedColumnName.equals(Labels.piece)) {
            return book.getPiece();
        }else if (askedColumnName.equals(Labels.ancient)) {
            return book.isAncient();
        }

        return Labels.unknown;
    }

    // Nagyon fontos! Eredetileg egy JTable-ben minden sztring
    // Így viszont ami boolean, az checkboxkent jelenik meg.
    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    // Nagyon fontos! Eredetileg egy JTable minden mezõje szerkeszthetõ
    // Jelenleg ezt letiltjuk, a szerkesztéshez a kontrolleren keresztül az
    // adatbázis kommunikációt is implementalni kell!
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

}
