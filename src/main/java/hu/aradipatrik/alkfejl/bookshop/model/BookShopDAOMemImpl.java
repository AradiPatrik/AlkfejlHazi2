package hu.aradipatrik.alkfejl.bookshop.model;

import hu.aradipatrik.alkfejl.bookshop.model.bean.Book;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Customer;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Purchase;

import java.util.ArrayList;
import java.util.List;

/**
 * Ez az osztály az adatelérést szolgálja. Mivel nincs mögötte tényleges
 * perzisztens tároló (pl. adatbázis), ezért csak memóriába tárolja az
 * adatokat.
 */
public class BookShopDAOMemImpl implements BookShopDAO {

    private static int id = 1;

    List<Customer> customers = new ArrayList<Customer>();
    List<Book> books = new ArrayList<Book>();
    List<Purchase> purchases = new ArrayList<Purchase>();

    /**
     * Hozzáad egy {@link Customer}-t az adattárhoz.
     *
     * @param customer A tárolandó {@link Customer}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    @Override
    public boolean addCustomer(Customer customer) {
        if (!checkCustomerNameUnique(customer)) {
            return false;
        }

        // léptetjük az id-t az új customer-hez (ezt az adattár kell biztosítja)
        id++;

        // az adattároló id-t oszt az objektumnak
        customer.setId(id);

        // eltároljuk
        boolean isStored = customers.add(customer);

        System.out.println("Ügyfelek:" + customers.toString());

        return isStored;
    }

    /**
     * Ellenõrzi a {@link #customers} integritását, a {@link Customer#getName()}
     * egyedi kell legyen.
     *
     * @param newCustomer Az újonnan felveendõ {@link Customer}.
     * @return True, ha a név egyedi, false egyébként.
     */
    private boolean checkCustomerNameUnique(Customer newCustomer) {
        boolean rvIsValid = true;

        for (Customer customer : customers) {
            if (customer.getName().equals(newCustomer.getName())) {
                rvIsValid = false;
                break;
            }
        }

        return rvIsValid;
    }

    /**
     * Visszaadja a tárolt {@link Customer} példányokat.
     *
     * @return A tárolt {@link Customer}-ek listája.
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Hozzáad egy {@link Book}-ot az adattárhoz.
     *
     * @param book A tárolandó {@link Book}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    public boolean addBook(Book book) {
        if (!checkBookTitleUnique(book)) {
            return false;
        }

        // léptetjük az id-t az új book-hoz (ezt az adattár kell biztosítja)
        id++;

        // az adattároló id-t oszt az objektumnak
        book.setId(id);

        boolean isStored = books.add(book);

        System.out.println("Könyvek:" + books.toString());

        return isStored;
    }

    /**
     * Ellenõrzi a {@link #books} integritását, a {@link Book#getTitle()}
     * egyedi kell legyen.
     *
     * @param newBook Az újonnan felveendõ {@link Book}.
     * @return True, ha a cím egyedi, false egyébként.
     */
    private boolean checkBookTitleUnique(Book newBook){
        boolean rvIsValid = true;

        for (Book book : books) {
            if (book.getTitle().equals(newBook.getTitle())) {
                rvIsValid = false;
                break;
            }
        }

        return rvIsValid;
    }

    /**
     * Visszaadja a tárolt {@link Book} példányokat.
     *
     * @return A tárolt {@link Book}-ek listája.
     */
    public List<Book> getBooks() {
        return books;
    }

    public boolean addPurchase(Purchase purchase){
        if (!customers.contains(purchase.getCustomer()) ||
                !books.contains(purchase.getBook())) {
            return false;
        }

        // léptetjük az id-t az új book-hoz (ezt az adattár kell biztosítja)
        id++;

        // az adattároló id-t oszt az objektumnak
        purchase.setId(id);

        boolean isStored = purchases.add(purchase);

        System.out.println("Vásárlások:" + purchases.toString());

        return isStored;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

}
