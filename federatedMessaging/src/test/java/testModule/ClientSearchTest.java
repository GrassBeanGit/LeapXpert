package testModule;

import org.testng.annotations.Test;

import actions.common.TokenActions;
import actions.usersManagementActions.ClientSearchActions;
import io.restassured.path.json.JsonPath;

public class ClientSearchTest {

	@Test
	public static void verifyUserCanSearchWithTwoSpacesAndResultIsPaggged() {
	
		String token = TokenActions.getToken("leapxpert", "jes2021", "111111");
		
		JsonPath response = ClientSearchActions.searchAndGetResponse(token, "  ", 20, 1, "ACTIVE", "");
	
		ClientSearchActions.verifyTotalRecords(response, 41);
		ClientSearchActions.verifyPagging(response, 3, 1, 20);
	}
	
	@Test
	public static void verifyUserCanSearchWithValidClientName() {

		String token = TokenActions.getToken("leapxpert", "jes2021", "111111");
		
		JsonPath response = ClientSearchActions.searchAndGetResponse(token, "jenife", 20, 1, "ACTIVE", "");

		ClientSearchActions.verifyTotalRecords(response, 1);
		ClientSearchActions.verifyFirstRecordName(response, "jenife");
	}

	@Test
	public static void verifyNoRecordIsReturnedWhenStatusIsNoUpperCase() {
		String token = TokenActions.getToken("leapxpert", "jes2021", "111111");
		JsonPath response = ClientSearchActions.searchAndGetResponse(token, "jenife", 20, 1, "active", "");

		ClientSearchActions.verifyTotalRecords(response, 0);

	}
}
