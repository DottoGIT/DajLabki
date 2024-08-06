CREATE TABLE Studenci(
    indeks      VARCHAR(100)    PRIMARY KEY,
    imie        VARCHAR(100)    NOT NULL,
    nazwisko    VARCHAR(100)    NOT NULL,
    mail        VARCHAR(100)    NOT NULL
);

CREATE TABLE Wymiany(
    wymiana_id      INT             PRIMARY KEY AUTO_INCREMENT,
    indeks_1        VARCHAR(100)    NOT NULL,
    indeks_2        VARCHAR(100)    ,
    pokoj_name      VARCHAR(100)    NOT NULL,
    dzien_1         VARCHAR(100)    NOT NULL,
    godz_1          VARCHAR(100)    NOT NULL,
    dzien_2         VARCHAR(100)    NOT NULL,
    godz_2          VARCHAR(100)    NOT NULL,
    status          VARCHAR(100)    ,

    FOREIGN KEY (indeks_1) REFERENCES Studenci(indeks),
    FOREIGN KEY (indeks_2) REFERENCES Studenci(indeks)
);

CREATE TABLE Logowanie(
    logowanie_id    INT            PRIMARY KEY AUTO_INCREMENT,
    request_token   VARCHAR(100)   NOT NULL,
    token_secret    VARCHAR(100)   NOT NULL
);

CREATE TABLE Sesja(
    sesja_id        INT             PRIMARY KEY AUTO_INCREMENT,
    stud_indeks     VARCHAR(100)    NOT NULL,
    access_token    VARCHAR(100)    NOT NULL,
    token_secret    VARCHAR(100)    NOT NULL,
    FOREIGN KEY (stud_indeks) REFERENCES Studenci(indeks)
);
