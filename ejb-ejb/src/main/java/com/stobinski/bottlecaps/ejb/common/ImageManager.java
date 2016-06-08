package com.stobinski.bottlecaps.ejb.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stobinski.bottlecaps.ejb.entities.Caps;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImageManager {

	public static final String PATH = "C:\\Users\\user\\workspace\\ejb\\ADDED";
	public static final String EXT = "JPG";
	
	@Inject
	private Logger log;
	
	@Inject
	private DaoService daoService;
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveImage(String base64) throws IOException {
		log.debug(base64);
		Integer oldFileName = getLastFileNameNumber();
		log.debug("OLD FILE NAME: " + oldFileName);
		Integer newFileName = getNewFileNameNumber(oldFileName);
		log.debug("NEW FILE NAME: " + newFileName);
		saveFile(base64ToByteArray(base64), generateFileNamePath(newFileName));
		insertDBEntry(String.valueOf(newFileName));
	}

	protected byte[] base64ToByteArray(String base64) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(base64).getAsJsonObject();
		String sBase64 = jsonObject.get("baseimage").getAsString();
		int index = sBase64.indexOf("base64,") + "base64,".length();
		sBase64 = sBase64.substring(index);
		return Base64.getDecoder().decode(sBase64);
	}
	
	protected Integer getLastFileNameNumber() {
		return daoService.getLastFileName();
	}
	
	protected Integer getNewFileNameNumber(Integer oldFileName) {
		return oldFileName + 1;
	}
	
	protected String generateFileNamePath(Integer newFileName) {
		return ImageManager.PATH + "\\" + newFileName + "." + ImageManager.EXT;
	}
	
	private void saveFile(byte[] image, String path) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(image);
		
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		ImageIO.write(bufferedImage, ImageManager.EXT, new File(path));
	}
	
	protected void insertDBEntry(String fileName) {
		Caps caps = new Caps();
		caps.setCountry_id(2);
		caps.setBrand_id(1);
		caps.setBeer(1);
		caps.setAdded_date(new Date());
		caps.setPath("jjj");
		caps.setFile_name(fileName);
		caps.setExtension(ImageManager.EXT);
		caps.setCap_text("TEST");
		daoService.persist(caps);
	}
	
}
