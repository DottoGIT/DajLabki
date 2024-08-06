package databaseservice.DAOs;
import databaseservice.models.Logowanie;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LogowanieDAO {

    private final JdbcTemplate jdbcTemplate;

    public LogowanieDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void AddLogowanie(Logowanie logowanie) {
        String sql = "INSERT INTO Logowanie (request_token, token_secret) VALUES (?, ?)";
        jdbcTemplate.update(sql, logowanie.getRequest_token(), logowanie.getToken_secret());
    }
    public void DeleteLogowanie(String requestToken) {
        String sql = "DELETE FROM Logowanie WHERE request_token = ?";
        jdbcTemplate.update(sql, requestToken);
    }
    public Logowanie FindLogowanieByRequestToken(String requestToken) {
        String sql = "SELECT * FROM Logowanie WHERE request_token = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{requestToken}, new BeanPropertyRowMapper<>(Logowanie.class));
    }
}