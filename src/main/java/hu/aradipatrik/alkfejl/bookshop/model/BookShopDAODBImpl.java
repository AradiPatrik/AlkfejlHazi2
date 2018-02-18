package hu.aradipatrik.alkfejl.bookshop.model;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Book;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Customer;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Purchase;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Ez az osztály az adatelérést szolgálja. Tényleges perzisztens
 * tárolót, adatbázist használ.
 */
public class BookShopDAODBImpl implements BookShopDAO {

    List<Customer> customers = new ArrayList<>();
    List<Book> books = new ArrayList<>();
    List<Purchase> purchases = new ArrayList<>();

    // Adatbázis fájlt reprezentáló string, melyet a
    // BookShopDB környezeti változóból olvasunk ki (env.bat állítja be)
    private static final String DATABASE_FILE = BookShopDAODBImpl.class.getResource("/bookshop.db").toString();

    // SQL paraméterezhetõ INSERT utasítás Customer felvételére
    // Az egyes paramétereket utólagosan állíthatjuk be (PreparedStatement)
    private static final String SQL_ADD_CUSTOMER =
            "INSERT INTO Customer " +
                    "(name, age, female, rented, student, grantee, qualification) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_ADD_BOOK =
            "INSERT INTO BOOK " +
                    "(author, title, year, category, price, pieces, ancient)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_ADD_PURCHASE =
            "INSERT INTO SoldBookInstances" +
                    "(id_book, id_customer, sellDate)" +
                    "VALUES (?, ?, ?)";

    // SQL lekérdezés, a Customerek lekérdezéséhez
    private static final String SQL_LIST_CUSTOMERS = "SELECT * FROM Customer";

    private static final String SQL_LIST_BOOKS = "SELECT * FROM Book";

    private static final String SQL_LIST_PURCHASES = "SELECT * FROM SoldBookInstances INNER JOIN Book ON id_book = Book.id INNER JOIN Customer ON id_customer = Customer.id";

    // A konstruktorban inicializáljuk az adatbázist
    public BookShopDAODBImpl() {
        try {
            // Betoltjuk az SQLite JDBC drivert, ennek segítségével érjük majd
            // el az SQLite adatbázist
            // kulso/java/sqlitejdbc-v054.jar - a classpath-ba is bekerült
            // build.xml - javac tasknál classpath attribútum (nézzük meg)
            // Valamint ezt megadtuk a disztribúció futattásánál is a
            // run.bat-ban! (nézzük meg)
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load SQLite JDBC driver.");
            e.printStackTrace();
        }
    }

    /**
     * Hozzáad egy {@link Customer}-t az adattárhoz.
     *
     * @param customer A tárolandó {@link Customer}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    @Override
    public boolean addCustomer(Customer customer) {
        boolean rvSucceeded = false;

        // Az adatbázis kapcsolatunkat a DriverManager segítségével hozzuk létre
        // Megadjuk hogy a JDBC milyen driveren keresztül milyen fájlt keressen
        //
        // új Customer felvétele esetén egy PreparedStatement objektumot kérünk a kapcsolat objektumtól
        // Ez egy paraméterezhetõ SQL utasitást vár, a paraméterek ?-ként jelennek meg
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
             PreparedStatement pst = conn.prepareStatement(SQL_ADD_CUSTOMER);) {

            // Az egyes parametéreket sorban kell megadni, pozíció alapján, ami
            // 1-tõl indul
            // Célszerû egy indexet inkrementálni, mivel ha az egyik paraméter
            // kiesik, akkor nem kell az utána következõeket újra számozni...
            int index = 1;
            pst.setString(index++, customer.getName());
            pst.setInt(index++, customer.getAge());
            pst.setInt(index++, customer.isFemale() ? 1 : 0);
            pst.setInt(index++, customer.isRented() ? 1 : 0);
            pst.setInt(index++, customer.isStudent() ? 1 : 0);
            pst.setInt(index++, customer.isGrantee() ? 1 : 0);
            pst.setString(index++, customer.getQualification());

            // Az ExecuteUpdate paranccsal végrehajtjuk az utasítást
            // Az executeUpdate visszaadja, hogy hány sort érintett az SQL ha
            // DML-t hajtunk végre (DDL esetén 0-t ad vissza)
            int rowsAffected = pst.executeUpdate();

            // csak akkor sikeres, ha valóban volt érintett sor
            if (rowsAffected == 1) {
                rvSucceeded = true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute adding customer.");
            e.printStackTrace();
        }
        return rvSucceeded;
    }

    /**
     * Visszaadja a tárolt {@link Customer} példányokat.
     *
     * @return A tárolt {@link Customer}-ek listája.
     */
    public List<Customer> getCustomers(){

        // Töröljük a memóriából a customereket (azért tartjuk bennt, mert
        // lehetnek késõbb olyan mûveletek, melyekhez nem kell frissíteni)
        customers.clear();

        // Az adatbázis kapcsolatunkat a DriverManager segítségével hozzuk létre
        // Megadjuk, hogy a JDBC milyen driveren keresztul milyen fájlt keressen
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
             // A kapcsolat (conn) objektumtól kérünk egy egyszerû (nem paraméterezhetõ) utasítást
             Statement st = conn.createStatement();
             // Az utasítás objektumon keresztül indítunk egy query-t (az eredményeket egy ResultSet objektumban kapjuk vissza)
             ResultSet rs = st.executeQuery(SQL_LIST_CUSTOMERS);) {

            // Bejárjuk a visszakapott ResultSet-et (ami a customereket tartalmazza)
            while (rs.next()) {
                // új Customert hozunk létre
                Customer customer = new Customer();

                // A customer nevét a ResultSet aktuális sorából olvassuk (name column)
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));

                // A customer korát a ResultSet aktuális sorából olvassuk (age column)
                customer.setAge(rs.getInt("age"));
                customer.setFemale(rs.getInt("female") == 1);
                customer.setRented(rs.getInt("rented") == 1);
                customer.setStudent(rs.getInt("student") == 1);
                customer.setGrantee(rs.getInt("grantee") == 1);
                customer.setQualification(rs.getString("qualification"));

                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute listing customers.");
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * Hozzáad egy {@link Book}-ot az adattárhoz.
     *
     * @param book A tárolandó {@link Book}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    public boolean addBook(Book book) {
        boolean rvSucceeded = false;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
             PreparedStatement pst = conn.prepareStatement(SQL_ADD_BOOK)) {

            int index = 1;
            pst.setString(index++, book.getAuthor());
            pst.setString(index++, book.getTitle());
            pst.setInt(index++, book.getYear());
            pst.setString(index++, book.getCategory());
            pst.setInt(index++, book.getPrice());
            pst.setInt(index++, book.getPiece());
            pst.setBoolean(index, book.isAncient());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 1) {
                rvSucceeded = true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute adding book.");
            e.printStackTrace();
        }
        return rvSucceeded;
    }

    /**
     * Visszaadja a tárolt {@link Book} példányokat.
     *
     * @return A tárolt {@link Book}-ek listája.
     */
    public List<Book> getBooks() {
        books.clear();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SQL_LIST_BOOKS)) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setAuthor(rs.getString("author"));
                book.setTitle(rs.getString("title"));
                book.setYear(rs.getInt("year"));
                book.setCategory(rs.getString("category"));
                book.setPrice(rs.getInt("price"));
                book.setPiece(rs.getInt("pieces"));
                book.setAncient(rs.getInt("ancient") == 1);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute listing customers.");
            e.printStackTrace();
        }
        return books;
    }

    /**
     * Hozzáad egy {@link Purchase}-et az adattárhoz.
     *
     * @param purchase A tárolandó {@link Purchase}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    @Override
    public boolean addPurchase(Purchase purchase) {
        boolean rvSucceeded = false;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
             PreparedStatement pst = conn.prepareStatement(SQL_ADD_PURCHASE)) {

            //(id_book, id_customer, sellDate)
            int index = 1;
            pst.setInt(index++, purchase.getBook().getId());
            pst.setInt(index++, purchase.getCustomer().getId());

            pst.setTimestamp(index, Timestamp.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 1) {
                rvSucceeded = true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute adding book.");
            e.printStackTrace();
        }
        return rvSucceeded;
    }

    /**
     * Visszaadja a tárolt {@link Purchase} példányokat.
     *
     * @return A tárolt {@link Purchase}-ek listája.
     */
    @Override
    public List<Purchase> getPurchases() {
        purchases.clear();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SQL_LIST_PURCHASES)) {

            while (rs.next()) {
                Purchase purchase = new Purchase();
                Book book = new Book();
                Customer customer = new Customer();
                purchase.setBook(book);
                purchase.setCustomer(customer);
                customer.setName(rs.getString("name"));
                customer.setFemale(rs.getInt("female") == 1);
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getInt("price"));
                purchases.add(purchase);
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute listing customers.");
            e.printStackTrace();
        }
        return purchases;
    }

}
