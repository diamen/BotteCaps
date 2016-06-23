package com.stobinski.bottlecaps.ejb.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.entities.Brands;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.entities.Countries;

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
	public void saveImage(String base64, String captext, String capbrand, Boolean isBeer, String country) throws IOException {
		Integer oldFileName = getLastFileNameNumber();
		Integer newFileName = getNewFileNameNumber(oldFileName);
		Long brandId = getBrandId(capbrand);
		Long countryId = getCountryId(country);
		saveFile(base64ToByteArray(base64), generateFilePath(newFileName));
		insertDBEntry(String.valueOf(newFileName), captext, brandId, isBeer, countryId);
		
		log.debug("File {" + newFileName + "} added to database");
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
		return daoService.retrieveData(new QueryBuilder().select().from(Caps.class).build())
			.stream().map(e -> (Caps) e).map(e -> e.getFile_name())	// List<Serializable> -> List<Caps> -> List<Caps.getFile_name()>
			.mapToInt(e -> Integer.valueOf(e)).max().getAsInt();
	}
	
	protected Integer getNewFileNameNumber(Integer oldFileName) {
		return oldFileName + 1;
	}
	
	protected long getBrandId(String capbrand) {
		List<Brands> brands = daoService.retrieveData(new QueryBuilder().select().from(Brands.class).build())
										.stream().map(e -> (Brands) e).collect(Collectors.toList());
		boolean exists = brands.stream().map(e -> e.getName()).anyMatch(e -> e.equals(capbrand));
	
		if(exists)
			return brands.stream().filter(e -> e.getName().equals(capbrand)).findFirst().map(e -> e.getId()).get().intValue();
		
		Brands brand = new Brands();
		brand.setName(capbrand);
		daoService.persist(brand);
		
		return brands.stream().map(e -> e.getId()).mapToLong(e -> e).max().getAsLong() + 1;
	}
	
	protected long getCountryId(String country) {
		return ((Countries) daoService.retrieveSingleData(new QueryBuilder().select().from(Countries.class).where(Countries.NAME_NAME).eq(country).build())).getId();
	}
	
	protected String generateFilePath(Integer newFileName) {
		return ImageManager.PATH + File.separatorChar + newFileName + "." + ImageManager.EXT;
	}
	
	private void saveFile(byte[] image, String path) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(image);
		
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		ImageIO.write(bufferedImage, ImageManager.EXT, new File(path));
	}
	
	protected void insertDBEntry(String fileName, String captext, Long brandId, Boolean isBeer, Long countryId) {
		Caps caps = new Caps();
		caps.setCountry_id(countryId);
		caps.setBrand_id(brandId);
		caps.setBeer(isBeer ? 1 : 0);
		caps.setAdded_date(new Date());
		caps.setPath("jjj");
		caps.setFile_name(fileName);
		caps.setExtension(ImageManager.EXT);
		caps.setCap_text(captext);
		daoService.persist(caps);
	}
	
}
