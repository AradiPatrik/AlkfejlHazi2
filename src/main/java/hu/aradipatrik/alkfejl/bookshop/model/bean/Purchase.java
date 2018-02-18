package hu.aradipatrik.alkfejl.bookshop.model.bean;

public class Purchase {

    private int id;
    private Book book;
    private Customer customer;

    // id

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // book

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // customer
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Purchase [" +
                "book=" + book.toString() +
                ", customer=" + customer.toString() +
                "]";
    }
}
