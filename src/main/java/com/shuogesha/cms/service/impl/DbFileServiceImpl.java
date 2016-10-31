package com.shuogesha.cms.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.shuogesha.cms.dao.DbFileDao;
import com.shuogesha.cms.entity.DbFile;
import com.shuogesha.cms.service.DbFileService;
import com.shuogesha.common.file.UploadUtils;
@Service("dbService") 
public class DbFileServiceImpl implements DbFileService{
	
	@Override
	public String store(String ext, MultipartFile file) throws IOException{
		String filename;
		GridFSDBFile dbfile;
		// 判断文件是否存在
		do {
			filename = UploadUtils.generateFilename("", ext);
			dbfile = findByFileName(filename);
		} while (file == null);
		dao.save(filename, file.getInputStream());
		return filename;
	}
	
	@Override
	public GridFSDBFile findByFileName(String filename) {
		return dao.findByFileName(filename);
	}

	@Override
	public DbFile findById(String id) {
		return dao.findById(id);
	}
 
	@Override
	public String storeByFilename(String filename, InputStream in)
			throws IOException {
		dao.save(filename, in);
		return filename;
	}

	@Override
	public File retrieve(String name) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean restore(String name, File file)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteById(String id) {
		dao.deleteById(id);;
	}

	@Override
	public DbFile[] deleteByIds(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Autowired
	private DbFileDao dao;

}
