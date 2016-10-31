package com.shuogesha.cms.dao;

import java.io.InputStream;

import com.mongodb.gridfs.GridFSDBFile;
import com.shuogesha.cms.entity.DbFile;
import com.shuogesha.cms.web.mongo.Updater;

public interface DbFileDao {
	public DbFile findById(String id);

	public String save(String filename, InputStream in);

	public void deleteById(String id);

	public GridFSDBFile findByFileName(String filename);
}
