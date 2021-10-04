package actions.common;

import constants.UrlConstants;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

public class TokenActions {
	
	public static String getToken(String company, String userName, String otp) {
		String token = "";

		RestAssured.baseURI = UrlConstants.API_BASE_URL + "authentication/login/mfa/verify";
		RequestSpecification request = RestAssured.given();
		request.header("content-type", "application/json");

		String requestBody = createRequestBodyToGetToken(company, userName, otp);
		Response response = request.body(requestBody).post();
		
		token = response.body().jsonPath().get("accessToken");
		return token;
	}

	private static String createRequestBodyToGetToken(String company, String userName, String otp) {
		String requestBody = "";
		
		JsonPath authenticationInfo = getAuthenticationInfo(company, userName);

		JSONObject requestParams = new JSONObject();
		requestParams.put("ticket", authenticationInfo.get("ticket"));
		requestParams.put("passcode", otp);
		requestParams.put("userId", authenticationInfo.get("userId"));
		requestParams.put("companyId", authenticationInfo.get("companyId"));
		requestParams.put("role", authenticationInfo.get("role"));
		
		requestBody = requestParams.toString();
		
		return requestBody;
	}
	
	private static JsonPath getAuthenticationInfo(String company, String userName) {

		RestAssured.baseURI = UrlConstants.API_BASE_URL + "authentication/login";
		RequestSpecification request = RestAssured.given();
		request.header("authorization",
				"DigestLeapXpert username=\"" + userName + "\"," + "company=\"" + company + "\"," + "realm=\"" + company
						+ "\"," + "nonce=\"6159b8996afc173c8e04f812:6159b8996afc173c8e04f813\"," + "qop=\"auth-int\","
						+ "deviceUniqueIdentifier=\"85b3a996-5778-47a2-aa4f-beb3acc7f32f\"," + "nc=\"00000004\","
						+ "cnonce=\"12d15718\"," + "uri=\"/v1/authentication/login\"," + "algorithm=\"md5\","
						+ "response=\"6d39bbcd3e7e50433409f33819d989a8\"");
		Response response = request.body("{}").post();

		JsonPath authenticationInfo = response.body().jsonPath();
		return authenticationInfo;
	}

}