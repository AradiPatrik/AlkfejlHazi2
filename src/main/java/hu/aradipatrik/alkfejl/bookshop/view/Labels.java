package hu.aradipatrik.alkfejl.bookshop.view;

/**
 * Ez az osztály tartalmazza a programban elõforduló {@link String} konstansokat.
 * Csak itt fordulhat elõ {@link String} konstans!
 * <p>
 * Általánosságban elfogadott és szokás, hogy a kód nem tartalmaz olyan
 * {@link String} konstansokat, melyek a felhasználói felületen jelennek meg.
 * Ezeket mindig egy közös helyre gyûjtjük, így késõbb lokalizálható az alkalmazás
 * és eleve átláthatóbb így.
 * </p>
 */
public class Labels {

    // General labels
    public final static String name = "Name";
    public final static String ok = "OK";
    public final static String cancel = "Cancel";
    public final static String error = "Error";
    public final static String empty = "";
    public final static String unknown = "Unknown";

    // Main window
    public static final String main_window_title = "Book Shop";

    // Customer labels
    public final static String customer = "Customer";
    public final static String add_customer = "Add customer";
    public final static String list_customers = "List customers";
    public final static String gender = "Gender";
    public final static String female = "Female";
    public final static String male = "Male";
    public final static String age = "Age";
    public final static String grantee = "Grantee";
    public final static String university = "University";
    public final static String college = "College";
    public final static String high_school = "High school";
    public final static String elementary_school = "Elementary school";
    public final static String[] qualifications = {
            Labels.university,
            Labels.college,
            Labels.high_school,
            Labels.elementary_school
    };
    public final static String qualification = "Qualification";
    public final static String student = "Student";
    public final static String rented = "Rented";
    public final static String customer_name_is_required = "Customer name is required!";
    public final static String customer_exists = "Customer already exists!";

    // Book labels
    public final static String book = "Book";
    public final static String buy_book = "Buy book";
    public final static String list_books = "List books";
    public final static String author = "Author";
    public final static String title = "Title";
    public final static String year = "Year";
    public final static String category = "Category";
    public final static String price = "Price";
    public final static String piece = "Piece";
    public final static String sci_fi = "sci-fi";
    public final static String horror = "horror";
    public final static String drama = "Dráma";
    public final static String[] categories = new String[] {
            Labels.sci_fi,
            Labels.horror,
            Labels.drama
    };
    public final static String ancient = "Ancient";
    public final static String book_title_is_required = "Book title is required!";
    public final static String book_exists = "Such book is already bought!";

    // Sell labels
    public final static String sell = "Sell";
    public final static String sell_book = "Sell book";
    public final static String list_sold_books = "List sold books";
    public final static String purchase_choose_book_and_customer =
            "Válassz ki könyvet és vásárlót!";
    public final static String purchase_failed =
            "Nem sikerült rögzíteni a vásárlást!";
}
