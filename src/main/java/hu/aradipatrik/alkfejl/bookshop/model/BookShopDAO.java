package hu.aradipatrik.alkfejl.bookshop.model;

import hu.aradipatrik.alkfejl.bookshop.model.bean.Book;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Customer;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Purchase;

import java.util.List;

/**
 * Az interfész a BookShop app adatelérési retegét reprezentálja.
 */
public interface BookShopDAO {

    /**
     * Hozzáad egy {@link Customer}-t az adattárhoz.
     *
     * @param customer A tárolandó {@link Customer}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    public boolean addCustomer(Customer customer);

    /**
     * Visszaadja a tárolt {@link Customer} példányokat.
     *
     * @return A tárolt {@link Customer}-ek listája.
     */
    public List<Customer> getCustomers();

    /**
     * Hozzáad egy {@link Book}-ot az adattárhoz.
     *
     * @param book A tárolandó {@link Book}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    public boolean addBook(Book book);

    /**
     * Visszaadja a tárolt {@link Book} példányokat.
     *
     * @return A tárolt {@link Book}-ek listája.
     */
    public List<Book> getBooks();

    /**
     * Hozzáad egy {@link Purchase}-et az adattárhoz.
     *
     * @param purchase A tárolandó {@link Purchase}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    public boolean addPurchase(Purchase purchase);

    /**
     * Visszaadja a tárolt {@link Purchase} példányokat.
     *
     * @return A tárolt {@link Purchase}-ek listája.
     */
    public List<Purchase> getPurchases();
}
