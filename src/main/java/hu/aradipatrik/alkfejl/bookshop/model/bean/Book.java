package hu.aradipatrik.alkfejl.bookshop.model.bean;

/**
 * Az oszt�ly egy k�nyvet �r le a JavaBean konvenci�k betart�s�val:
 */
public class Book {
    private int id;
    private String author;
    private String title;
    private int year;
    private String category;
    private int price;
    private int piece;
    private boolean ancient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public boolean isAncient() {
        return ancient;
    }

    public void setAncient(boolean ancient) {
        this.ancient = ancient;
    }

    @Override
    public String toString() {
        return "Book [" +
                "author=" + author + ", title=" + title + ", year=" + year +
                ", category=" + category + ", price=" + price +
                ", piece=" + piece + ", ancient=" + ancient + "]";
    }
}
