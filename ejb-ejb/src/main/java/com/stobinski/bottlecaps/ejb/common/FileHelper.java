package com.stobinski.bottlecaps.ejb.common;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class FileHelper {

	private FileHelper() {}
	
	public static String joinPath(String...directory) {
		return StringUtils.join(directory, File.separatorChar);
	}
	
	public static String getFullPath(String path, String filename, String extension) {
		return path + File.separatorChar + filename + "." + extension;
	}
	
}
