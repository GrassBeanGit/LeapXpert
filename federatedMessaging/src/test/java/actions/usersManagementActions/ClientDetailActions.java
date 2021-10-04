package actions.usersManagementActions;

import org.testng.Assert;

import constants.UrlConstants;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ClientDetailActions {

	public static String getUserId(JsonPath response) {

		return response.get("records[0].id");
	}

	public static JsonPath getClientSearch(String token, String searchString, int pageSize, int pageNumber,
			String statusesString, String filtersString) {

		return ClientSearchActions.searchAndGetResponse(token, searchString, pageSize, pageNumber, statusesString,
				filtersString);
	}

	public static JsonPath getClientDetail(String token, String userId) {
	
		RestAssured.baseURI = UrlConstants.API_BASE_URL + "user_management/clients/" + userId;
		RequestSpecification request = RestAssured.given();
		request.header("authorization", "Bearer " + token);
		
		Response response = request.body("{}").get();
		
		return response.body().jsonPath();
	}
	
	public static void verifyEmail(JsonPath response, String expectedEmail) {
		String email = response.get("record.persona.email");
		Assert.assertEquals(email, expectedEmail);
	}
	
	public static void verifyMobilePhoneNumber(JsonPath response, String expectedMobilePhoneNumber) {
		String mobilePhoneNumber = response.get("record.persona.mobilePhoneNumber");
		Assert.assertEquals(mobilePhoneNumber, expectedMobilePhoneNumber);
	}
}