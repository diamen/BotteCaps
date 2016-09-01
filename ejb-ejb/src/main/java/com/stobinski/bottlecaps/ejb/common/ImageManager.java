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

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.imgscalr.Scalr;
import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.entities.TradeCaps;
import com.stobinski.bottlecaps.ejb.wrappers.Base64Cap;
import com.stobinski.bottlecaps.ejb.wrappers.Base64TradeCap;

@Stateless
public class ImageManager {
	
	private final Logger log;
	
	private final ImageFileHandler fileHandler;

	private final String ext;
	
	public ImageManager() {	
		this.log = null;
		this.fileHandler = null;
		this.ext = null;
	}
	
	@Inject
	public ImageManager(ConfigurationBean configBean, ImageFileHandler fileHandler, Logger log) {
		this.fileHandler = fileHandler;
		this.log = log;
		this.ext = configBean.getValue(EConfigKeys.EXT.toString());
	}
	
	public void saveImage(byte[] b, Long fileNameSequence, String filePath) throws IOException {
		String fullFilePath = fileHandler.generateFullFilePath(filePath, fileNameSequence);
		saveFile(b, fullFilePath);
		
		log.debug(String.format("File %d saved in %s", fileNameSequence, fullFilePath));
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Base64Cap> loadCapFiles(List<Caps> caps) {
		return caps.stream().map(e -> new Base64Cap(e, Base64Service.fromByteArrayToBase64(retrieveImage(e.getPath(), e.getFile_name()))))
				.collect(Collectors.toList());
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Base64TradeCap> loadTradeCapFiles(List<TradeCaps> caps) {
		return caps.stream().map(e -> new Base64TradeCap(e, Base64Service.fromByteArrayToBase64(retrieveScaledImage(e.getPath(), e.getFile_name()))))
				.collect(Collectors.toList());
	}
	
	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Base64Cap loadCapFile(Caps cap) {
		return new Base64Cap(cap, Base64Service.fromByteArrayToBase64(retrieveImage(cap.getPath(), cap.getFile_name())));
	}

	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Base64TradeCap loadTradeCapFile(TradeCaps cap) {
		return new Base64TradeCap(cap, Base64Service.fromByteArrayToBase64(retrieveImage(cap.getPath(), cap.getFile_name())));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveFile(byte[] base64, String path) throws IOException {
		ImageIO.write(byteArrayToBufferedImage(base64), ext, new File(path));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveFile(BufferedImage image, String path) throws IOException {
		ImageIO.write(image, ext, new File(path));
	}
	
	public String getExt() {
		return ext;
	}
	
	public String generateFilePath(Long fileNameSequence, String country) {
		return fileHandler.generateFilePath(fileNameSequence, country);
	}
	
	public Long generateFileNameSequence() {
		Long oldFileName = fileHandler.getLastFileNameNumber();
		return fileHandler.getNewFileNameNumber(oldFileName);
	}
	
	public BufferedImage miniaturizeImage(byte[] base64) throws IOException {
		return miniaturizeImage(byteArrayToBufferedImage(base64));
	}
	
	public BufferedImage miniaturizeImage(BufferedImage image) {
		return Scalr.resize(image, 300);
	}
	
	public byte[] retrieveImage(String path, String fileName) {
		String filePath = path + File.separatorChar + fileName + '.' + ext;
		try {
			InputStream inputStream = new FileInputStream(new File(filePath));
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			return bufferedImageToByteArray(bufferedImage);
		} catch(IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}		
	}
	
	private BufferedImage byteArrayToBufferedImage(byte[] base64) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(base64);
		BufferedImage image = ImageIO.read(inputStream);
		inputStream.close();
		
		return image;
	}
	
	private byte[] retrieveScaledImage(String path, String fileName) {
		String filePath = path + File.separatorChar + fileName + '.' + ext;
		try {
			InputStream inputStream = new FileInputStream(new File(filePath));
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			BufferedImage scaledImage = Scalr.resize(bufferedImage, 300);
			return bufferedImageToByteArray(scaledImage);
		} catch(IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}	
	}
	
	private byte[] bufferedImageToByteArray(BufferedImage bufferedImage) throws IOException {
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, ext, baos);
			baos.flush();
			byte[] b = baos.toByteArray();
			baos.close();
			return b;
	}
	
}
