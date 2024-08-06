package databaseservice.models;

import jakarta.persistence.Entity;

@Entity
public class Logowanie {
    private Long logowanie_id;
    private String request_token;
    private String token_secret;

    public Logowanie(){}
    public Logowanie(String requestToken, String tokenSecret) {
        this.request_token = requestToken;
        this.token_secret = tokenSecret;
    }

    public Long getLogowanie_id() {
        return logowanie_id;
    }

    public void setLogowanie_id(Long logowanie_id) {
        this.logowanie_id = logowanie_id;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    public String getToken_secret() {
        return token_secret;
    }

    public void setToken_secret(String token_secret) {
        this.token_secret = token_secret;
    }

    @Override
    public String toString() {
        return "Logowanie{" +
                "idLogowania=" + logowanie_id +
                ", requestToken='" + request_token + '\'' +
                ", tokenSecret='" + token_secret + '\'' +
                '}';
    }
}
