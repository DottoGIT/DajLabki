package databaseservice.DAOs;
import databaseservice.models.Sesja;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SesjaDAO {

    private final JdbcTemplate jdbcTemplate;

    public SesjaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void AddSesja(Sesja sesja) {
        String sql = "INSERT INTO Sesja (stud_indeks, access_token, token_secret) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, sesja.getStud_indeks(), sesja.getAccess_token(), sesja.getToken_secret());
    }
    public void DeleteSesja(Long id) {
        String sql = "DELETE FROM Sesja WHERE sesja_id = ?";
        jdbcTemplate.update(sql, id);
    }
    public Sesja FindSesjaByAccessToken(String accessToken) {
        String sql = "SELECT * FROM Sesja WHERE access_token = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{accessToken}, new BeanPropertyRowMapper<>(Sesja.class));
    }
}