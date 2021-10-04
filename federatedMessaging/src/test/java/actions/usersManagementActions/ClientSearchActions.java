package actions.usersManagementActions;

import org.testng.Assert;

import constants.UrlConstants;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.request.userManagement.clientSearchRequestModel.SimpleClientSearchRequestModel;

public class ClientSearchActions {

	public static JsonPath searchAndGetResponse(String token, String searchString, int pageSize,
			int pageNumber, String statusesString, String filtersString) {

		RestAssured.baseURI = UrlConstants.API_BASE_URL + "user_management/clients/search";
		RequestSpecification request = RestAssured.given();
		request.header("authorization", "Bearer " + token);

		String requestBody = SimpleClientSearchRequestModel.createRequest(searchString, pageSize, pageNumber,
				statusesString, filtersString);
		Response response = request.body(requestBody).post();

		return response.body().jsonPath();
	}

	public static void verifyTotalRecords(JsonPath response, int expectedRecordsTotal) {
		int total = response.getInt("total");
		Assert.assertEquals(total, expectedRecordsTotal);
	}

	public static void verifyMessage(JsonPath response, String expectedMessage) {
		String message = response.get("message");
		Assert.assertEquals(message, expectedMessage);
	}

	public static void verifyPagging(JsonPath response, int expectedNumberOfPages, int expectedPageNumber,
			int expectedPageSize) {
		int numberOfPages = response.getInt("numberOfPages");
		int pageNumber = response.getInt("pageNumber");
		int pageSize = response.getInt("pageSize");

		Assert.assertEquals(numberOfPages, expectedNumberOfPages);
		Assert.assertEquals(pageNumber, expectedPageNumber);
		Assert.assertEquals(pageSize, expectedPageSize);

	}
	
	public static void verifyFirstRecordName(JsonPath response, String expectedName) {
		String name = response.get("records[0].name");
		Assert.assertEquals(name, expectedName);
	}
}
