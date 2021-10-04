package testModule;

import org.testng.annotations.Test;

import actions.common.TokenActions;
import actions.usersManagementActions.ClientDetailActions;
import io.restassured.path.json.JsonPath;

public class ClientDetailTest {

	@Test
	public static void verifyPersonalInfo() {
		
		String token = TokenActions.getToken("leapxpert", "jes2021", "111111");

		JsonPath searchResponse = ClientDetailActions.getClientSearch(token, "jenife", 20, 1, "ACTIVE", "");
		String userId = ClientDetailActions.getUserId(searchResponse);
		
		JsonPath clientDetailSearchResponse = ClientDetailActions.getClientDetail(token, userId);
		
		ClientDetailActions.verifyEmail(clientDetailSearchResponse, "nhung.nguyen+d@leap.expert");
		ClientDetailActions.verifyMobilePhoneNumber(clientDetailSearchResponse, "84963282927");
	}

}