package com.tata.service.search;

import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class FlatJsonDocument {

	public static JSONObject processJsonString(String message) {

		JSONObject commerceJsonObj = new JSONObject(message);

		JSONObject priceChildObject = (JSONObject) commerceJsonObj.get("price");
		JSONObject workFlow = (JSONObject) commerceJsonObj.get("workFlow");

		// Flat price object
		commerceJsonObj.put("minPrice", priceChildObject.get("minPrice"));
		commerceJsonObj.put("maxPrice", priceChildObject.get("maxPrice"));
		commerceJsonObj.put("range", priceChildObject.get("range"));

		// Flat workFlow object
		commerceJsonObj.put("status", workFlow.get("status"));

		// Flat metaFields (meta Fields has nested values ; need to get each document
		// inside meta Field
		JSONArray metaObj = commerceJsonObj.getJSONArray("metaFields");
		for (int metaObjIterator = 0; metaObjIterator < metaObj.length(); metaObjIterator++) {

			JSONObject metaObjVal = metaObj.getJSONObject(metaObjIterator);
			commerceJsonObj.put("metaObj" + metaObjIterator + "_key", metaObjVal.get("key"));
			commerceJsonObj.put("metaObj" + metaObjIterator + "_value", metaObjVal.get("value"));
		}

		// remove all the nested Object as we have flatern the structure
		commerceJsonObj.remove("price");
		commerceJsonObj.remove("metaFields");
		commerceJsonObj.remove("workFlow");

		// Change String Date format to Date
		Date publishedAt = ChangesolrCompatibleDate.changeStringToDate(commerceJsonObj.get("publishedAt").toString());
		Date createdAt = ChangesolrCompatibleDate.changeStringToDate(commerceJsonObj.get("createdAt").toString());
		Date updatedAt = ChangesolrCompatibleDate.changeStringToDate(commerceJsonObj.get("updatedAt").toString());

		// remove old Json fields
		commerceJsonObj.remove("publishedAt");
		commerceJsonObj.remove("createdAt");
		commerceJsonObj.remove("updatedAt");

		// Adding formated date
		commerceJsonObj.put("publishedAt", publishedAt);
		commerceJsonObj.put("createdAt", createdAt);
		commerceJsonObj.put("updatedAt", updatedAt);

		return commerceJsonObj;

	}

}
