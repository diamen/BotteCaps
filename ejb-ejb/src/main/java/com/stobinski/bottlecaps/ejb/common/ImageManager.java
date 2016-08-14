package com.stobinski.bottlecaps.ejb.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.stobinski.bottlecaps.ejb.dao.CapsDaoService;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Cap;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImageManager {
	
	@Inject
	private Logger log;
	
	@Inject
	private CapsDaoService capsDao;
	
	@Inject
	private ImageFileHandler fileHandler;
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveImage(byte[] b, String captext, String capbrand, Boolean isBeer, String country) throws IOException {
		Integer oldFileName = fileHandler.getLastFileNameNumber();
		Integer newFileName = fileHandler.getNewFileNameNumber(oldFileName);
		Long brandId = capsDao.getBrandId(capbrand);
		Long countryId = capsDao.getCountryId(country);
		String filePath = fileHandler.generateFilePath(newFileName, country);
		saveFile(b, fileHandler.generateFullFilePath(newFileName, country));
		capsDao.insertCap(String.valueOf(newFileName), captext, brandId, isBeer, countryId, filePath, ImageFileHandler.EXT);
		
		log.debug("File {" + newFileName + "} added to database");
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Base64Cap> loadFiles(List<Caps> caps) {
		return caps.stream().map(e -> new Base64Cap(e, Base64Service.fromByteArrayToBase64(retrieveImage(e.getPath(), e.getFile_name()))))
				.collect(Collectors.toList());
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Base64Cap loadFile(Caps cap) {
		return new Base64Cap(cap, Base64Service.fromByteArrayToBase64(retrieveImage(cap.getPath(), cap.getFile_name())));
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void removeCap(String country, Long capId) {
		capsDao.removeCap(country, capId);
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateCap(Long id, String country, String captext, String capbrand, Integer beer) {
		capsDao.updateCap(id, country, captext, capbrand, beer);
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
	
}
