package databaseservice.DAOs;
import databaseservice.models.Wymiana;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WymianaDAO {

    private final JdbcTemplate jdbcTemplate;

    public WymianaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void AddWymiana(Wymiana wymiana) {
        String sql = "INSERT INTO Wymiany (indeks_1, pokoj_name, dzien_1, godz_1, dzien_2, godz_2, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, wymiana.getIndeks_1(), wymiana.getPokoj_name(), wymiana.getDzien_1(), wymiana.getGodz_1(), wymiana.getDzien_2(), wymiana.getGodz_2(), wymiana.getStatus());
    }
    public void DeleteWymiana(Long id) {
        String sql = "DELETE FROM Wymiany WHERE wymiana_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Wymiana FindMatch(String pokoj_nazwa, String dzien_1, String dzien_2, String godz_1, String godz_2)
    {
        String sql = "SELECT * FROM Wymiany WHERE indeks_2 IS NULL AND pokoj_name = ? AND dzien_2 = ? AND dzien_1 = ? AND godz_2 = ? AND godz_1 = ? FETCH FIRST ROW ONLY";
        return jdbcTemplate.queryForObject(sql, new Object[]{pokoj_nazwa, dzien_1, dzien_2, godz_1, godz_2}, new BeanPropertyRowMapper<>(Wymiana.class));
    }

    public List<Wymiana> FindWymianyByIndex(String index, int pageSize, int pageNum)
    {
        int offset = pageSize * pageNum;
        String sql = "SELECT * FROM Wymiany WHERE indeks_1 = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Wymiana.class), index, pageSize, offset);
    }

    public List<Wymiana> FindWymianyByPartnerIndex(String index, int pageSize, int pageNum)
    {
        int offset = pageSize * pageNum;
        String sql = "SELECT * FROM Wymiany WHERE indeks_2 = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Wymiana.class), index, pageSize, offset);
    }


	public void ChangeStatus(long id) {
		final String finished = "Finished";
        String sql = "UPDATE Wymiany SET status = ? WHERE wymiana_id = ?";
        jdbcTemplate.update(sql, finished, id);
    }

	public void Finalize(long id, String indeks_2) {
		String sql = "UPDATE Wymiany SET indeks_2 = ? WHERE wymiana_id = ?";
        jdbcTemplate.update(sql, indeks_2, id);
		ChangeStatus(id);
	}
}
