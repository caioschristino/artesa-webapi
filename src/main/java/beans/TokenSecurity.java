package beans;

import org.glassfish.jersey.internal.util.Base64;

import com.google.gson.Gson;

public class TokenSecurity {
	private String Authorization;

	public TokenSecurity(String username, String password) {
		this.Authorization = issueToken(username, password);
	}

	private String issueToken(String username, String password) {
		// Issue a token (can be a random String persisted to a database or a
		// JWT token)
		// The issued token must be associated to a user
		// Return the issued token
		return Base64.encodeAsString(String.format("%s:%s", username, password));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		return gson.toJson(Authorization);
	}

	public boolean isEmpty() {
		return Authorization == null || Authorization.isEmpty() ? true : false;
	}
}
