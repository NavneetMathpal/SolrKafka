package com.tata.service.producer;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import com.tata.kafka.model.ProductSpecifications;

public class ObjectToJsonString {

	public static String changeObjectToJsonString(ProductSpecifications productSpecifications) {

		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = Obj.writeValueAsString(productSpecifications);
			System.out.println(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonStr;

	}
}
