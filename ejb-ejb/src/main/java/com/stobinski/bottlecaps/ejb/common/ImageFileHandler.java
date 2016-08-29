package com.stobinski.bottlecaps.ejb.common;

import java.io.File;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.dao.DaoService;
import com.stobinski.bottlecaps.ejb.dao.QueryBuilder;
import com.stobinski.bottlecaps.ejb.dao.exceptions.QueryBuilderException;
import com.stobinski.bottlecaps.ejb.entities.Caps;

class ImageFileHandler {

	protected static final String PATH = "D:\\KAPSLE\\ZAGRANICA";
	protected static final String EXT = "JPG";
	
	@Inject
	private DaoService daoService;
	
	@Inject
	private Logger log;
	
	protected Integer getLastFileNameNumber() {
		try {
			return daoService.retrieveData(new QueryBuilder().select().from(Caps.class).build())
				.stream().map(e -> (Caps) e).map(e -> e.getFile_name())	// List<Serializable> -> List<Caps> -> List<Caps.getFile_name()>
				.mapToInt(e -> Integer.valueOf(e)).max().getAsInt();
		} catch (QueryBuilderException e) {
			log.error(e);
			return null;
		}
	}
	
	protected Integer getNewFileNameNumber(Integer oldFileName) {
		return oldFileName + 1;
	}
	
	protected String generateFilePath(Integer newFileName, String country) {
		return ImageFileHandler.PATH + File.separatorChar + country;
	}

	protected String generateFullFilePath(Integer newFileName, String country) {
		return generateFilePath(newFileName, country) + File.separatorChar + newFileName + "." + ImageFileHandler.EXT;
	}
	
}
