package model.request.userManagement.clientSearchRequestModel;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class SimpleClientSearchRequestModel {

	public static String createRequest(String searchString, int pageSize, int pageNumber, String statusesString,
			String filtersString) {
		String request = "";

		JSONObject json = new JSONObject();
		json.put("searchString", searchString);
		json.put("pageSize", pageSize);
		json.put("pageNumber", pageNumber);

		if (statusesString.isBlank()) {
			json.put("statuses", new JSONArray());
		} else {
			JSONArray statusesArray = new JSONArray();
			statusesArray.add(statusesString);
			json.put("statuses", statusesArray);
		}

		if (filtersString.isBlank()) {
			json.put("filters", new JSONArray());
		} else {
			JSONArray filtersArray = new JSONArray();
			filtersArray.add(filtersString);
			json.put("filters", filtersArray);
		}

		request = json.toString();

		return request;
	}
}
