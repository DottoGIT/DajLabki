package pap.proj.papdemo.usosservice;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import pap.proj.papdemo.usosservice.UsosApi;

import org.springframework.stereotype.Service;

/* USOSAuth class to handle OAuth requests to USOS Api endpoints
 * ScribeJava library is used to create and send requests.*/

@Service
public class USOSOAuth {
	private static final String CONSUMER_KEY = "";
	private static final String CONSUMER_SECRET = "";
	private static final String CALLBACK_URL = "http://localhost:8080/oauth/authcallback";
	private static final String AUTH_BASE_URL = "https://apps.usos.pw.edu.pl/services/oauth/authorize?oauth_token=";

	private static final String USER_URL = "https://apps.usos.pw.edu.pl/services/users/user";
	private static final String PARTICIPANT_URL = "https://apps.usos.pw.edu.pl/services/groups/participant";

	private OAuth10aService auth;

	public USOSOAuth() {
		auth = new ServiceBuilder(CONSUMER_KEY)
			.apiSecret(CONSUMER_SECRET)
			.callback(CALLBACK_URL)
			.build(UsosApi.instance());
	}
		
	// Get OAuth1.0a Request Token using full consumer token
	public OAuthController.RequestTokenResponse getRequestToken() {
		try {
			final OAuth1RequestToken requestToken = auth.getRequestToken();
			String token = requestToken.getToken();
			String tokenSecret = requestToken.getTokenSecret();
			return new OAuthController.RequestTokenResponse(token, tokenSecret);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// generate authorization url
	public String getAuthUrl(String requestToken) {
		return AUTH_BASE_URL + requestToken;
	}

	// retrieve access token
	// similiar to getRequestToken, but requestToken and verifier are used
	public OAuthController.AccessTokenResponse getAccessToken(
		String verifier, String token, String tokenSecret) {

		try {
			OAuth1AccessToken accessToken = auth.getAccessToken(
				new OAuth1RequestToken(token, tokenSecret),
				verifier
			);
			token = accessToken.getToken();
			tokenSecret = accessToken.getTokenSecret();
			return new OAuthController.AccessTokenResponse(token, tokenSecret);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}


	}

	public String executeRequest(OAuthRequest request) {
		try {
			final Response response = auth.execute(request);
			return response.getBody();
			//JsonObject responseJson = JsonParser.parseString(response.getBody()).getAsJsonObject();
			//return responseJson.get("id").getAsString();
		} catch (Exception e) {
			return "Exception occured";
		}
	}

	public OAuthRequest signRequest(OAuthRequest request, String accessToken, String accessTokenSecret) {
		final OAuth1AccessToken token = new OAuth1AccessToken(accessToken, accessTokenSecret);
		auth.signRequest(token, request);
		return request;
	}

	public String getUserId(String accessToken, String accessTokenSecret) {
		final OAuthRequest request = new OAuthRequest(Verb.GET, USER_URL);
		final OAuth1AccessToken token = new OAuth1AccessToken(accessToken, accessTokenSecret);
		auth.signRequest(token, request);
		return executeRequest(request);
		
	}

	public String getUserGroups(String accessToken, String accessTokenSecret) {
		final OAuthRequest request = new OAuthRequest(Verb.GET, PARTICIPANT_URL);
		request.addQuerystringParameter("active_terms", "true");
		request.addQuerystringParameter("fields", "course_id");
		final OAuth1AccessToken token = new OAuth1AccessToken(accessToken, accessTokenSecret);
		auth.signRequest(token, request);
		return executeRequest(request);
	}

}

// course ID: 1270139
