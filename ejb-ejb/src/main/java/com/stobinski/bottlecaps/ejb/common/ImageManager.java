package com.stobinski.bottlecaps.ejb.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ImageManager {

	public static final String PATH = "C:\\Users\\user\\workspace\\ejb\\ADDED";
	public static final String EXT = "JPG";
	
	@Inject
	private DaoService daoService;
	
	public void saveImage(String base64) throws IOException {
		saveFile(base64ToByteArray(base64), generateFileName());
	}
	
	private byte[] base64ToByteArray(String base64) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(base64).getAsJsonObject();
		String sBase64 = jsonObject.get("baseimage").getAsString();
		int index = sBase64.indexOf("base64,") + "base64,".length();
		sBase64 = sBase64.substring(index);
		return Base64.getDecoder().decode(sBase64);
	}
	
	private Integer getLastFileNameNumber() {
		return daoService.getLastFileName();
	}
	
	private String generateFileName() {
		Integer lastFileName = getLastFileNameNumber();
		Integer currentFileName = lastFileName++;
		return ImageManager.PATH + "\\" + currentFileName + "." + ImageManager.EXT;
	}
	
	private void saveFile(byte[] image, String path) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(image);
		
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		ImageIO.write(bufferedImage, ImageManager.EXT, new File(path));
	}
	
}
