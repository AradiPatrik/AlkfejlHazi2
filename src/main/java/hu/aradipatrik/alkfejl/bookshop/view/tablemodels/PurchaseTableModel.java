package hu.aradipatrik.alkfejl.bookshop.view.tablemodels;

import hu.aradipatrik.alkfejl.bookshop.model.bean.Purchase;
import hu.aradipatrik.alkfejl.bookshop.view.Labels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PurchaseTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    private String[] columnNames = new String[] {
            Labels.customer, Labels.gender, Labels.book, Labels.price };

    List<Purchase> purchases;

    public PurchaseTableModel(List<Purchase> purchases) {
        super();

        this.purchases = purchases;
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
        return purchases.size();
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
        Purchase purchase = purchases.get(row);
        String askedColumnName = columnNames[col];

        if (askedColumnName.equals(Labels.customer)) {
            return purchase.getCustomer().getName();
        } else if (askedColumnName.equals(Labels.gender)) {
            return purchase.getCustomer().isFemale() ? Labels.female : Labels.male;
        } else if (askedColumnName.equals(Labels.book)) {
            return purchase.getBook().getTitle();
        } else if (askedColumnName.equals(Labels.price)) {
            return purchase.getBook().getPrice();
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
