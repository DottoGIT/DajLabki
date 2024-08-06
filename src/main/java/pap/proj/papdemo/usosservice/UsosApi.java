package pap.proj.papdemo.usosservice;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

public class UsosApi extends DefaultApi10a {
	private static final String AUTHORIZE_URL = "https://apps.usos.pw.edu.pl/services/oauth/authorize?oauth_token=";

	protected UsosApi() {
	}

	private static class InstanceHolder {
		private static final UsosApi INSTANCE = new UsosApi();
	}

	public static UsosApi instance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return "https://apps.usos.pw.edu.pl/services/oauth/access_token";
	}

	@Override
	public String getRequestTokenEndpoint() {
		return "https://apps.usos.pw.edu.pl/services/oauth/request_token?scopes=studies";
	}

	@Override
	public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
		return String.format(AUTHORIZE_URL, requestToken.getToken());
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return AUTHORIZE_URL;
	}
}
