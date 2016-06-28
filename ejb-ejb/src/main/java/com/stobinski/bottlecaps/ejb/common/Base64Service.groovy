package com.stobinski.bottlecaps.ejb.common

import com.google.gson.JsonObject
import com.google.gson.JsonParser

class Base64Service {

	static String fromJsonBase64ToBase64(String base64Json) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(base64Json).getAsJsonObject();
		String base64 = jsonObject.get("baseimage").getAsString();
		int index = base64.indexOf("base64,") + "base64,".length();
		return base64.substring(index);
	}

	static byte[] fromBase64JsonToByteArray(String base64Json) {
		return fromJsonBase64ToBase64(base64Json).decodeBase64();
	}

	static String fromByteArrayToBase64(byte[] b) {
		return b.encodeBase64();
	}
	
}
