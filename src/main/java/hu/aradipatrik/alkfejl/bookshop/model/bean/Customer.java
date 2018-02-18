package hu.aradipatrik.alkfejl.bookshop.model.bean;

/**
 * Az oszt�ly egy v�s�rl�t/�gyfelet �r le a JavaBean konvenci�k betart�s�val:
 * Jobb klikk+source+generate getters/setters.
 * Megh�vasa az �sszes nem statikus, nem transient attrib�tumra.
 */
public class Customer {

    private int id;
    private String name;
    private int age;
    private boolean female;
    private boolean rented = false;
    private boolean student = false;
    private boolean grantee = false;
    private String qualification;

    // id

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // age

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // gender

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    // rented

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    // student

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    // grantee

    public boolean isGrantee() {
        return grantee;
    }

    public void setGrantee(boolean grantee) {
        this.grantee = grantee;
    }

    // qualification

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return
                id + "-" + name + "-" + age + "-" +
                female + "-" + student + "-" + rented;
    }

}
