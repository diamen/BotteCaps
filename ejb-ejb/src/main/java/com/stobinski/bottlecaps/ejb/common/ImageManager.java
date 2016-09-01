package com.stobinski.bottlecaps.ejb.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.imgscalr.Scalr;
import org.jboss.logging.Logger;

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

	public void saveFile(BufferedImage image, String path) throws IOException {
		ImageIO.write(image, getExt(), new File(path));
	}
	
	public void saveFile(byte[] base64, String path) throws IOException {
		ImageIO.write(byteArrayToBufferedImage(base64), getExt(), new File(path));
	}
	
	public String getExt() {
		return ext;
	}
	
	public String generateFilePath(String country) {
		return fileHandler.generateFilePath(country);
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
		String filePath = path + File.separatorChar + fileName + '.' + getExt();
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

	private byte[] bufferedImageToByteArray(BufferedImage bufferedImage) throws IOException {
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, getExt(), baos);
			baos.flush();
			byte[] b = baos.toByteArray();
			baos.close();
			return b;
	}
	
}
