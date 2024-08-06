package databaseservice.models;

import jakarta.persistence.Entity;

@Entity
public class Sesja {
    private Long sesja_id;
    private String stud_indeks;
    private String access_token;
    private String token_secret;

    public Sesja() {}

    public Sesja(String indeks, String accessToken, String tokenSecret) {
        this.stud_indeks = indeks;
        this.access_token = accessToken;
        this.token_secret = tokenSecret;
    }

    public Long getSesja_id() {
        return sesja_id;
    }

    public void setSesja_id(Long sesja_id) {
        this.sesja_id = sesja_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_secret() {
        return token_secret;
    }

    public void setToken_secret(String token_secret) {
        this.token_secret = token_secret;
    }

    public String getStud_indeks() {
        return stud_indeks;
    }

    public void setStud_indeks(String stud_indeks) {
        this.stud_indeks = stud_indeks;
    }

    @Override
    public String toString() {
        return "Sesja{" +
                "idSesji=" + sesja_id +
                ", studIndeks='" + stud_indeks + '\'' +
                ", accessToken='" + access_token + '\'' +
                ", tokenSecret='" + token_secret + '\'' +
                '}';
    }
}
