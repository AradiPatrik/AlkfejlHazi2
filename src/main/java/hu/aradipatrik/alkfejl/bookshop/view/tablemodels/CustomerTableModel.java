package hu.aradipatrik.alkfejl.bookshop.view.tablemodels;

import hu.aradipatrik.alkfejl.bookshop.model.bean.Customer;
import hu.aradipatrik.alkfejl.bookshop.view.Labels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

/**
 * Egyedi table modelt az AbstractTableModelbõl tudunk származtatni.
 */
public class CustomerTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    // Az egyes oszlop fejlécek nevei
    private String[] columnNames = new String[] {
            Labels.name, Labels.age, Labels.female,
            Labels.rented, Labels.student, Labels.grantee,
            Labels.qualification };

    // A vásárlókat tartalmazó objektum (a DAO-tól kapott átstruktúrálva)
    Map<Integer, Customer> customers = new HashMap<Integer, Customer>();

    public CustomerTableModel(List<Customer> customers) {
        super();

        prepareDataStructure(customers);
    }

    /**
     * A controlleren keresztül megkapott customers struktúráját (lista) a view
     * szükségleteinek megfelelõen átalakítja.
     * <p>
     * Egy map-be tesszük õket, kulcsként a sorszámot használjuk, így a
     * lenti {@ #getValueAt(int,int)} függvényt tudjuk implementálni.
     * </p>
     *
     * @param customers A {@link Customers}-ek listája.
     */
    private void prepareDataStructure(List<Customer> customers) {
        int row = 0;

        for (Customer customer : customers) {
            this.customers.put(row, customer);
            row++;
        }
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
        return customers.entrySet().size();
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
        Customer customer = customers.get(row);
        String askedColumnName = columnNames[col];

        if (askedColumnName.equals(Labels.name)) {
            return customer.getName();
        } else if (askedColumnName.equals(Labels.age)) {
            return customer.getAge();
        } else if (askedColumnName.equals(Labels.female)) {
            return customer.isFemale();
        } else if (askedColumnName.equals(Labels.rented)) {
            return customer.isRented();
        } else if (askedColumnName.equals(Labels.student)) {
            return customer.isStudent();
        } else if (askedColumnName.equals(Labels.grantee)) {
            return customer.isGrantee();
        } else if (askedColumnName.equals(Labels.qualification)) {
            return customer.getQualification();
        }

        return Labels.unknown;
    }

    // Nagyon fontos! Eredetileg egy JTable-ben minden String
    // Így viszont, ami boolean, az checkboxkent jelenik meg.
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

