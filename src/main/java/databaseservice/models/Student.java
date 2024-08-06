package databaseservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
    private String indeks;
    private String imie;
    private String nazwisko;
    private String mail;

    public Student(){}
    public Student(String indeks, String imie, String nazwisko, String mail) {
        this.indeks = indeks;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.mail = mail;
    }

    public String getIndeks() {
        return indeks;
    }

    public void setIndeks(String indeks) {
        this.indeks = indeks;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Student{" +
                "indeks='" + indeks + '\'' +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
