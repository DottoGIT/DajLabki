package databaseservice.DAOs;
import databaseservice.models.Logowanie;
import databaseservice.models.Student;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO {

    private final JdbcTemplate jdbcTemplate;

    public StudentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void AddStudent(Student student) {
        String sql = "INSERT INTO Studenci (indeks, imie, nazwisko, mail) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, student.getIndeks(), student.getImie(), student.getNazwisko(), student.getMail());
    }

    public void DeleteStudent(String indeks) {
        String sql = "DELETE FROM Studenci WHERE indeks = ?";
        jdbcTemplate.update(sql, indeks);
    }

    public Student FindStudentByIndeks(String indeks) {
        String sql = "SELECT * FROM Studenci WHERE indeks = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{indeks}, new BeanPropertyRowMapper<>(Student.class));
    }

	public void ChangeMail(String indeks, String mail) {
        String sql = "UPDATE Studenci SET mail = ? WHERE indeks = ?";
        jdbcTemplate.update(sql, mail, indeks);
    }


}
