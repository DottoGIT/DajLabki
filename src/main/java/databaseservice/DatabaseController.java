package databaseservice;
import databaseservice.DAOs.*;
import databaseservice.models.*;

import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DatabaseController {
    private final LogowanieDAO logowanieDAO;
    private final StudentDAO studentDAO;
    private final SesjaDAO sesjaDAO;
    private final WymianaDAO wymianaDAO;
    @Autowired
    public DatabaseController(LogowanieDAO logowanieDAO, StudentDAO studentDAO, SesjaDAO sesjaDAO, WymianaDAO wymianaDAO) {
        this.logowanieDAO = logowanieDAO;
        this.studentDAO = studentDAO;
        this.sesjaDAO = sesjaDAO;
        this.wymianaDAO = wymianaDAO;
    }
    /*-----------------------------------------------------------------------------------------------*/
    /*                                          LOGOWANIE                                            */
    /*-----------------------------------------------------------------------------------------------*/
    public void AddLogowanie(Logowanie logowanie, QueryResultMessage queryResult) {
        try{
            logowanieDAO.AddLogowanie(logowanie);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
    }
    public void DeleteLogowanie(String request_token, QueryResultMessage queryResult) {
        try {
            logowanieDAO.DeleteLogowanie(request_token);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
    }
    public Logowanie FindLogowanieByRequestToken(String requestToken, QueryResultMessage queryResult){
        try{
            return logowanieDAO.FindLogowanieByRequestToken(requestToken);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
            return null;
        }
    }

    /*-----------------------------------------------------------------------------------------------*/
    /*                                          SESJA                                                */
    /*-----------------------------------------------------------------------------------------------*/
    public void AddSesja(Sesja sesja, QueryResultMessage queryResult) {
        try{
            sesjaDAO.AddSesja(sesja);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
    }

    public void DeleteSesja(long id, QueryResultMessage queryResult) {
        try{
            sesjaDAO.DeleteSesja(id);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
    }
    public Sesja FindSesjaByAccessToken(String accessToken, QueryResultMessage queryResult){
        Sesja retSesja;
        try{
            retSesja = sesjaDAO.FindSesjaByAccessToken(accessToken);
        }catch(DataAccessException e) {
            queryResult.result = ResultCode.NO_CONNECTION;
            return null;
        }
        if(retSesja == null)
            queryResult.result = ResultCode.NO_DATA_FOUND;
        return retSesja;
    }
    /*-----------------------------------------------------------------------------------------------*/
    /*                                          STUDENT                                              */
    /*-----------------------------------------------------------------------------------------------*/
    public void AddStudent(Student student, QueryResultMessage queryResult) {
        try{
            studentDAO.AddStudent(student);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
    }

    public void DeleteStudent(String indeks, QueryResultMessage queryResult) {
        try{
            studentDAO.DeleteStudent(indeks);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
    }
    public Student FindStudentByIndeks(String indeks, QueryResultMessage queryResult)
    {
        Student retStudent;
        try{
            retStudent = studentDAO.FindStudentByIndeks(indeks);
        }catch(DataAccessException e) {
            queryResult.result = ResultCode.NO_CONNECTION;
            return null;
        }
        if(retStudent == null)
            queryResult.result = ResultCode.NO_DATA_FOUND;
        return retStudent;
    }

	public void ChangeMail(String indeks, String mail, QueryResultMessage queryResult) {
        if (!DataVerifier.isEmailValid(mail))
        {
            queryResult.result = ResultCode.INVALID_DATA;
            return;
        }

        try{
            studentDAO.ChangeMail(indeks, mail);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
	}

    /*-----------------------------------------------------------------------------------------------*/
    /*                                          WYMIANA                                              */
    /*-----------------------------------------------------------------------------------------------*/
    public void AddWymiana(Wymiana wymiana, QueryResultMessage queryResult) {
        if(!DataVerifier.isWymianaValid(wymiana))
        {
            queryResult.result = ResultCode.INVALID_DATA;
            return;
        }

        try{
            wymianaDAO.AddWymiana(wymiana);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
    }

    public void DeleteWymiana(long id, QueryResultMessage queryResult) {
        try{
            wymianaDAO.DeleteWymiana(id);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
    }
    public Wymiana FindWymianaMatch(String pokoj_nazwa,
                                    String dzien_1,
                                    String dzien_2,
                                    String godz_1,
                                    String godz_2,
                                    QueryResultMessage queryResult) {
        Wymiana retWymiana;
        try{
            retWymiana = wymianaDAO.FindMatch(pokoj_nazwa,dzien_1,dzien_2,godz_1,godz_2);
        }catch(DataAccessException e) {
            queryResult.result = ResultCode.NO_CONNECTION;
            return null;
        }
        if(retWymiana == null)
            queryResult.result = ResultCode.NO_DATA_FOUND;
        return retWymiana;
    }

    public List<Wymiana> FindWymianyByIndex(String index, int pageSize, int pageNum, QueryResultMessage queryResult)
    {
        List<Wymiana> retWymiany;
        try{
            retWymiany = wymianaDAO.FindWymianyByIndex(index, pageSize, pageNum);
        }catch(DataAccessException e) {
            queryResult.result = ResultCode.NO_CONNECTION;
            return null;
        }
        if(retWymiany.size() == 0)
        {
            queryResult.result = ResultCode.NO_DATA_FOUND;
            return null;
        }
        return retWymiany;
    }

    public List<Wymiana> FindWymianyByPartnerIndex(String index, int pageSize, int pageNum,QueryResultMessage queryResult)
    {
        List<Wymiana> retWymiany;
        try{
            retWymiany = wymianaDAO.FindWymianyByPartnerIndex(index, pageSize, pageNum);
        }catch(DataAccessException e) {
            queryResult.result = ResultCode.NO_CONNECTION;
            return null;
        }
        if(retWymiany.size() == 0)
        {
            queryResult.result = ResultCode.NO_DATA_FOUND;
            return null;
        }

		for (Wymiana wymiana : retWymiany) {
			String temp = wymiana.getIndeks_1();
			wymiana.setIndeks_1(wymiana.getIndeks_2());
			wymiana.setIndeks_2(temp);
			temp = wymiana.getGodz_1();
			wymiana.setGodz_1(wymiana.getGodz_2());
			wymiana.setGodz_2(temp);
			temp = wymiana.getDzien_1();
			wymiana.setDzien_1(wymiana.getDzien_2());
			wymiana.setDzien_2(temp);
		}
		return retWymiany;
    }

	public void FinalizeWymiana(long id, String index, QueryResultMessage queryResult) {
        try{
            wymianaDAO.Finalize(id, index);
        }catch(DataAccessException e)
        {
            queryResult.result = ResultCode.NO_CONNECTION;
        }
	}
}
