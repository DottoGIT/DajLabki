package pap.proj.papdemo.usosservice;

import databaseservice.*;
import databaseservice.models.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/* Rest Controller for endpoints dealing with authorizing USOS Api */

@RestController
@RequestMapping("/oauth")
public class OAuthController {
	
	private ApplicationContext context;
	private DatabaseController database;

	private final USOSOAuth usosOAuth;

	private final String LOGIN_REDIRECT = "http://localhost:5173/";  // CHANGE THIS TO APP INTERFACE

	@Autowired
	public OAuthController(USOSOAuth usosOAuth, ApplicationContext context) {
		this.usosOAuth = usosOAuth;
		this.context = context;
		database = context.getBean(DatabaseController.class);
	}
	
/* ---------------------------------------------------------------------------------------------- */
/*                                     INITIAL AUTH                                               */
/* ---------------------------------------------------------------------------------------------- */

	@GetMapping("/request")
	public RedirectView getRequestToken() {
		RequestTokenResponse response = usosOAuth.getRequestToken();

		Logowanie storeToken = new Logowanie(
				response.getRequestToken(),
				response.getRequestTokenSecret()
			);
		database.AddLogowanie(storeToken);

		RedirectView redirect = new RedirectView();
		redirect.setUrl(usosOAuth.getAuthUrl(response.getRequestToken()));
		return redirect;
	}

	// this will be called back by the usos api with the oauth verifier pin and request token
	@GetMapping("/authcallback")
	public RedirectView authCallback(
			@RequestParam(name="oauth_verifier") String verifier,
			@RequestParam(name="oauth_token") String requestToken,
			RedirectAttributes redirectAttributes) {

		// retrive the secret request token from database
		Logowanie logowanie = database.FindLogowanieByRequestToken(requestToken);
		String tokenSecret = logowanie.getToken_secret().trim();

		// get access token from verifier and full consumer and request tokens
		AccessTokenResponse response = usosOAuth.getAccessToken(verifier, requestToken, tokenSecret);

		// get user info
		String userInfo = getUserInfoJson(response.getAccessToken(), response.getAccessTokenSecret());
		JsonObject responseJson = JsonParser.parseString(userInfo).getAsJsonObject();
		String id = responseJson.get("id").getAsString();

		// add user to db if not already there
		if (!checkIfStudentExists(id)) {
			String name = responseJson.get("first_name").getAsString();
			String lastName = responseJson.get("last_name").getAsString();
			Student student = new Student(id, name, lastName, "EMAIL_PLACEHOLDER");
			database.AddStudent(student);
		}

		// store id in database
		Sesja checkSession = database.FindSesjaByAccessToken(response.getAccessToken());
		if (checkSession == null) {
			Sesja session = new Sesja(id, response.getAccessToken(), response.getAccessTokenSecret());
			database.AddSesja(session);
		}


		// delete request token from db
		database.DeleteLogowanie(requestToken);


		// prepare redirect
		redirectAttributes.addAttribute("token", response.getAccessToken());
		RedirectView redirect = new RedirectView();
		redirect.setUrl(LOGIN_REDIRECT);
		
		return redirect;
	}

	// DEBUG METHOD FOR TESTING REQUESTS
	@GetMapping("/debugcallback")
	public AccessTokenResponse debugCallback(
			@RequestParam(name="oauth_verifier") String verifier,
			@RequestParam(name="oauth_token") String requestToken) {

		// retrive the secret request token from database
		Logowanie logowanie = database.FindLogowanieByRequestToken(requestToken);
		String tokenSecret = logowanie.getToken_secret().trim();


		// get access token from verifier and full consumer and request tokens
		AccessTokenResponse response = usosOAuth.getAccessToken(verifier, requestToken, tokenSecret);
		
		return response;
	}
/* ---------------------------------------------------------------------------------------------- */
/*                                    USER INFO METHODS                                           */
/* ---------------------------------------------------------------------------------------------- */

	public String getUserInfoJson(
		@RequestParam(name="oauth_token") String token,
		@RequestParam(name="token_secret") String tokenSecret) {	
		
		return usosOAuth.getUserId(token, tokenSecret);
	
	}

	public Boolean checkIfStudentExists(String id) {
		return (database.FindStudentByIndeks(id) != null);
	}

	@GetMapping("/groups")
	@CrossOrigin(origins = "http://localhost:5173")
	public String getUserGroups(
		@RequestParam(name="oauth_token") String token) {	
		
		Sesja sesja = database.FindSesjaByAccessToken(token);
		if (sesja == null) {
			return "Error: Session expired or not found (log in again)";
		}
		String tokenSecret = sesja.getToken_secret();

		String response = usosOAuth.getUserGroups(token, tokenSecret);

		JsonObject responseJson = JsonParser.parseString(response).getAsJsonObject();
		JsonObject groups = responseJson.getAsJsonObject("groups");
		JsonArray terms = groups.getAsJsonArray("2023Z");
		JsonArray filteredTerms = new JsonArray();

		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < terms.size(); i++) {
			JsonObject jsonObject = terms.get(i).getAsJsonObject();
			if (jsonObject.has("course_id")) {
				String courseId = jsonObject.get("course_id").getAsString();
				int index = courseId.lastIndexOf("-");
				courseId = courseId.substring(index + 1);
				if (!ids.contains(courseId)) {
					ids.add(courseId);
					filteredTerms.add(courseId);
				}
			}
    }
		return filteredTerms.toString();
	
	}

	@PostMapping("/logout")
	@CrossOrigin(origins = "http://localhost:5173")
	public String logOut(@RequestParam(name="token") String token) {
		Sesja sesja = database.FindSesjaByAccessToken(token);
		if (sesja == null) {
			return "Error: User token not found";
		}

		database.DeleteSesja(sesja.getSesja_id());
		return "Logout successful!";
	}

/* ---------------------------------------------------------------------------------------------- */
/*                                    RESPONSE CLASSES                                            */
/* ---------------------------------------------------------------------------------------------- */

	public static class RequestTokenResponse {
		private final String requestToken;
		private final String requestTokenSecret;

		public RequestTokenResponse(String requestToken, String requestTokenSecret) {
			this.requestToken = requestToken;
			this.requestTokenSecret = requestTokenSecret;
		}

		public String getRequestToken() {
			return requestToken;
		}

		public String getRequestTokenSecret() {
			return requestTokenSecret;
		}
	}

	public static class AccessTokenResponse {
		private final String accessToken;
		private final String accessTokenSecret;

		public AccessTokenResponse(String accessToken, String accessTokenSecret) {
			this.accessToken = accessToken;
			this.accessTokenSecret = accessTokenSecret;
		}

		public String getAccessToken() {
			return accessToken;
		}

		public String getAccessTokenSecret() {
			return accessTokenSecret;
		}
	}
}
