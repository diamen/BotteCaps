package com.stobinski.bottlecaps.ejb.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.entities.Brands;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.entities.Countries;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Cap;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImageManager {
	
	@Inject
	private Logger log;
	
	@Inject
	private DaoService daoService;
	
	@Inject
	private ImageFileHandler fileHandler;
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveImage(byte[] b, String captext, String capbrand, Boolean isBeer, String country) throws IOException {
		Integer oldFileName = fileHandler.getLastFileNameNumber();
		Integer newFileName = fileHandler.getNewFileNameNumber(oldFileName);
		Long brandId = getBrandId(capbrand);
		Long countryId = getCountryId(country);
		String filePath = fileHandler.generateFilePath(newFileName, country);
		saveFile(b, fileHandler.generateFullFilePath(newFileName, country));
		insertDBEntry(String.valueOf(newFileName), captext, brandId, isBeer, countryId, filePath);
		
		log.debug("File {" + newFileName + "} added to database");
	}

	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Base64Cap> loadFiles(String country) {
		Long countryId = getCountryId(country);
		return this.loadFiles(countryId);
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Base64Cap> loadFiles(Long countryId) {
		return daoService.retrieveData(new QueryBuilder().select().from(Caps.class).where(Caps.COUNTRY_ID_NAME).eq(countryId).build())
				.stream().map(e -> (Caps) e).map(e -> new Base64Cap(e.getId(), Base64Service.fromByteArrayToBase64(retrieveImage(e.getPath(), e.getFile_name()))))
				.collect(Collectors.toList());
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Base64Cap loadFile(String country, Long capId) {
		Long countryId = getCountryId(country);
		return this.loadFile(countryId, capId);
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Base64Cap loadFile(Long countryId, Long capId) {
		Caps cap = (Caps) daoService.retrieveSingleData
				(new QueryBuilder().select().from(Caps.class).where(Caps.COUNTRY_ID_NAME, Caps.ID_NAME).eq(countryId, capId).build());
		
		return new Base64Cap(cap.getId(), Base64Service.fromByteArrayToBase64(retrieveImage(cap.getPath(), cap.getFile_name())));
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
	
	private void saveFile(byte[] image, String path) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(image);
		
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		ImageIO.write(bufferedImage, ImageFileHandler.EXT, new File(path));
	}
	
	private byte[] retrieveImage(String path, String fileName) {
		String filePath = path + File.separatorChar + fileName + '.' + ImageFileHandler.EXT;
		try {
			InputStream inputStream = new FileInputStream(new File(filePath));
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, ImageFileHandler.EXT, baos);
			baos.flush();
			byte[] b = baos.toByteArray();
			baos.close();
			return b;
		} catch(IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	
	protected void insertDBEntry(String fileName, String captext, Long brandId, Boolean isBeer, Long countryId, String filePath) {
		Caps caps = new Caps();
		caps.setCountry_id(countryId);
		caps.setBrand_id(brandId);
		caps.setBeer(isBeer ? 1 : 0);
		caps.setAdded_date(new Date());
		caps.setPath(filePath);
		caps.setFile_name(fileName);
		caps.setExtension(ImageFileHandler.EXT);
		caps.setCap_text(captext);
		daoService.persist(caps);
	}
	
}
