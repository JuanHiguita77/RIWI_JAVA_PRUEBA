package Entity;

public class Coder
{
    private int id_coder;
    private String name;
    private String surname;
    private String document;
    private int cohorte;
    private String cv;

    public Coder() {
    }

    public Coder(String name, String surname, String document, int cohorte, String cv) {
        this.name = name;
        this.surname = surname;
        this.document = document;
        this.cohorte = cohorte;
        this.cv = cv;
    }

    public int getId_coder() {
        return id_coder;
    }

    public void setId_coder(int id_coder) {
        this.id_coder = id_coder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public int getCohorte() {
        return cohorte;
    }

    public void setCohorte(int cohorte) {
        this.cohorte = cohorte;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return "Coder{" +
                "id_coder=" + id_coder +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", document='" + document + '\'' +
                ", cohorte=" + cohorte +
                ", cv='" + cv + '\'' +
                '}';
    }
}
