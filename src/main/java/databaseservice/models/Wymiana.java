package databaseservice.models;
import jakarta.persistence.*;


@Entity
@Table(name = "Wymiany")
public class Wymiana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wymiana_id")
    private Long wymiana_id;
    private String indeks_1;
    private String indeks_2;
    private String pokoj_name;
    private String dzien_1;
    private String godz_1;
    private String dzien_2;
    private String godz_2;
    private String status;

    public Wymiana(){}

    public Wymiana(String indeks_1, String pokoj_name, String dzien_1, String godz_1, String dzien_2, String godz_2) {
        this.indeks_1 = indeks_1;
        this.pokoj_name = pokoj_name;
        this.dzien_1 = dzien_1;
        this.godz_1 = godz_1;
        this.dzien_2 = dzien_2;
        this.godz_2 = godz_2;
        this.status = "Otwarte";
    }

    public Long getWymiana_id() {
        return wymiana_id;
    }

    public void setWymiana_id(Long wymiana_id) {
        this.wymiana_id = wymiana_id;
    }


    public String getIndeks_1() {
        return indeks_1;
    }

    public void setIndeks_1(String indeks_1) {
        this.indeks_1 = indeks_1;
    }

    public String getIndeks_2() {
        return indeks_2;
    }

    public void setIndeks_2(String indeks_2) {
        this.indeks_2 = indeks_2;
    }

    public String getPokoj_name() {
        return pokoj_name;
    }

    public void setPokoj_name(String pokoj_name) {
        this.pokoj_name = pokoj_name;
    }

    public String getDzien_1() {
        return dzien_1;
    }

    public void setDzien_1(String dzien_1) {
        this.dzien_1 = dzien_1;
    }

    public String getGodz_1() {
        return godz_1;
    }

    public void setGodz_1(String godz_1) {
        this.godz_1 = godz_1;
    }

    public String getDzien_2() {
        return dzien_2;
    }

    public void setDzien_2(String dzien_2) {
        this.dzien_2 = dzien_2;
    }

    public String getGodz_2() {
        return godz_2;
    }

    public void setGodz_2(String godz_2) {
        this.godz_2 = godz_2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Wymiana{" +
                "wymiana_id=" + wymiana_id +
                ", indeks_1='" + indeks_1 + '\'' +
                ", indeks_2='" + indeks_2 + '\'' +
                ", pokoj_name='" + pokoj_name + '\'' +
                ", dzien_1='" + dzien_1 + '\'' +
                ", godz_1='" + godz_1 + '\'' +
                ", dzien_2='" + dzien_2 + '\'' +
                ", godz_2='" + godz_2 + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
