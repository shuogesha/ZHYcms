package com.shuogesha.cms.dao.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.shuogesha.cms.dao.DbFileDao;
import com.shuogesha.cms.entity.DbFile;
import com.shuogesha.cms.web.mongo.Updater;
@Repository
public class DbFileDaoImpl implements DbFileDao{
	public DbFile findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		GridFSFile entity = gridFsTemplate.findOne(query);
		return (DbFile) entity;
	}
	
	@Override
	public GridFSDBFile findByFileName(String filename) {
		Query query = new Query(Criteria.where("filename").is(filename));
		GridFSDBFile entity = gridFsTemplate.findOne(query);
		return entity;
	}
	public String save(String filename, InputStream in) {
		return gridFsTemplate.store(in, filename).getId().toString();
	}

	public void deleteById(String id) {
		if (id != null) {
			Query query = new Query(Criteria.where("_id").is(id));
			gridFsTemplate.delete(query);
		}
	}

	/**
	 * spring mongodb　集成操作类　
	 */
	@Autowired
	protected GridFsTemplate gridFsTemplate;

}
