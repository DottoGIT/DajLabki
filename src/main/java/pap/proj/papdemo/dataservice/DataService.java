package pap.proj.papdemo.dataservice;

import databaseservice.*;
import databaseservice.models.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/data")
public class DataService {
	
	private ApplicationContext context;
	private DatabaseController database;

	@Autowired
	public DataService(ApplicationContext context) {
		this.context = context;
		database = context.getBean(DatabaseController.class);
	}

    /*-----------------------------------------------------------------------------------------------*/
    /*                                         STUDENT NAME                                          */
    /*-----------------------------------------------------------------------------------------------*/

	@GetMapping("/name")
	@CrossOrigin(origins = "http://localhost:5173")
	public StudentData getStudentName(@RequestParam(name="token") String token) {
		String index = database.FindSesjaByAccessToken(token).getStud_indeks();
		Student stud = database.FindStudentByIndeks(index);
		StudentData data = new StudentData(stud.getImie(), stud.getNazwisko(), index);
		return data;
	}

	@GetMapping("/name_id")
	@CrossOrigin(origins = "http://localhost:5173")
	public StudentData getStudentNameByIndeks(@RequestParam(name="indeks") String index) {
		Student stud = database.FindStudentByIndeks(index);
		StudentData data = new StudentData(stud.getImie(), stud.getNazwisko(), index);
		return data;
	}

    /*-----------------------------------------------------------------------------------------------*/
    /*                                        EXCHANGES                                              */
    /*-----------------------------------------------------------------------------------------------*/

	@PostMapping("/addexchange")
	@CrossOrigin(origins = "http://localhost:5173")
	public void addWymiana(
			@RequestParam(name = "classname") String pokoj_name,
			@RequestParam(name = "currentclassday") String dzien_1,
			@RequestParam(name = "currentclasshour") String godz_1,
			@RequestParam(name = "desiredclassday") String dzien_2,
			@RequestParam(name = "desiredclasshour") String godz_2,
			@RequestParam(name = "username") String userName,
			@RequestParam(name = "userlastname") String userLastName,
			@RequestParam(name = "token") String token) {

		StudentData data = getStudentName(token);
		database.AddWymiana(new Wymiana(data.getIndex(), pokoj_name, dzien_1, godz_1, dzien_2, godz_2));
	}

	@GetMapping("/exchanges")
	@CrossOrigin(origins = "http://localhost:5173")
	public String getExchanges(@RequestParam(name = "token") String token) {
		Sesja sesja = database.FindSesjaByAccessToken(token);
		if (sesja == null) {
			return "[]";
		}
		List<Wymiana> exchanges = database.FindWymianyByIndex(sesja.getStud_indeks());
		Gson gson = new Gson();
		return gson.toJson(exchanges);
	}

	@GetMapping("/exchanges_partner")
	@CrossOrigin(origins = "http://localhost:5173")
	public String getExchangesIfPartner(@RequestParam(name = "token") String token) {
		Sesja sesja = database.FindSesjaByAccessToken(token);
		if (sesja == null) {
			return "[]";
		}
		List<Wymiana> exchanges = database.FindWymianyByPartnerIndex(sesja.getStud_indeks());
		Gson gson = new Gson();
		return gson.toJson(exchanges);
	}

	private boolean checkSessionValidity(String token) {
		Sesja sesja = database.FindSesjaByAccessToken(token);
		return sesja != null;
	}

	@PostMapping("/withdraw")
	@CrossOrigin(origins = "http://localhost:5173")
	public void removeWymiana(
			@RequestParam(name = "token") String token,
			@RequestParam(name = "id") long id
			) {
		if (checkSessionValidity(token)) database.DeleteWymiana(id);
	}

	@GetMapping("/findmatch")
	@CrossOrigin(origins = "http://localhost:5173")
	public Wymiana findMatch(
			@RequestParam(name = "token") String token,
			@RequestParam(name = "subject") String subject,
			@RequestParam(name = "day_1") String day_1,
			@RequestParam(name = "day_2") String day_2,
			@RequestParam(name = "hour_1") String hour_1,
			@RequestParam(name = "hour_2") String hour_2
			) {

		if (!checkSessionValidity(token)) {
			return new Wymiana();
		}
		return database.FindWymianaMatch(subject, day_1, day_2, hour_1, hour_2);

	}

	@GetMapping("finalize")
	@CrossOrigin(origins = "http://localhost:5173")
	public void finalizeExchange(
			@RequestParam(name = "token") String token,
			@RequestParam(name = "id") long id
			) {

		if (checkSessionValidity(token)) {
			database.FinalizeWymiana(id, database.FindSesjaByAccessToken(token).getStud_indeks());
		}
	}

    /*-----------------------------------------------------------------------------------------------*/
    /*                                         USER MAIL                                             */
    /*-----------------------------------------------------------------------------------------------*/

	@GetMapping("/checkmail") 
	@CrossOrigin(origins = "http://localhost:5173")
	public boolean checkMail(@RequestParam(name = "token") String token) {
		Sesja sesja = database.FindSesjaByAccessToken(token);
		if (sesja == null) {
			return false;
		}

		String mail = database.FindStudentByIndeks(sesja.getStud_indeks()).getMail();
		return mail != "EMAIL_PLACEHOLDER";

	}

	@GetMapping("/changemail")
	@CrossOrigin(origins = "http://localhost:5173")
	public void changeMail(
		@RequestParam(name = "token") String token,
		@RequestParam(name = "mail") String mail) {
		
		Sesja sesja = database.FindSesjaByAccessToken(token);
		if (sesja == null) return;
		database.ChangeMail(sesja.getStud_indeks(), mail);
	}

	@GetMapping("/getmail")
	@CrossOrigin(origins = "http://localhost:5173")
	public String getMail(
		@RequestParam(name = "token") String token,
		@RequestParam(name = "index") String index) {	
		if (checkSessionValidity(token)) {
			return database.FindStudentByIndeks(index).getMail();
		} else {
			return "INVALID_SESSION";
		}
	}


	public static class StudentData {
		private final String name;
		private final String lastName;
		private final String index;

		public StudentData(String name, String lastName, String index) {
			this.name = name;
			this.lastName = lastName;
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public String getLastName(){
			return lastName;
		}

		public String getIndex() {
			return index;
		}
	}
	
}
