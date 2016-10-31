package com.shuogesha.cms.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.shuogesha.cms.entity.DbFile;

public interface DbFileService {
	public String store(String ext, MultipartFile file)throws IOException;
	
	public DbFile findById(String id);

	public String storeByFilename(String filename, InputStream in)
			throws IOException;

	public File retrieve(String name) throws IOException;

	public boolean restore(String name, File file)
			throws FileNotFoundException, IOException;

	public void deleteById(String id);

	public DbFile[] deleteByIds(String[] ids);
	
	/**
	 * 根据图片地址获取图片
	 * @param origName
	 */
	public GridFSDBFile findByFileName(String filename);

}
