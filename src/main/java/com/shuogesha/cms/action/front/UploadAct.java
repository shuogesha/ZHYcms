package com.shuogesha.cms.action.front;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mongodb.gridfs.GridFSDBFile;
import com.shuogesha.cms.service.DbFileService;
import com.shuogesha.common.image.ImageScale;

@Controller
@RequestMapping("/upload")
public class UploadAct {
	@RequestMapping(value = "/**/*.*")
	public void upload(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		String fid=request.getServletPath().replaceAll("/upload/", "");
		if (StringUtils.isBlank(fid)) {
			return;
		}
		GridFSDBFile file = dbFileService.findByFileName(fid);
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.getLength());
        file.writeTo(bos);
        byte[] buffer = bos.toByteArray();
        bos.close();
        response.getOutputStream().write(buffer);
        response.getOutputStream().flush();
        response.getOutputStream().close();
	}
	 
	@Autowired
	private DbFileService dbFileService;
	@Autowired
	private ImageScale imageScale;
}
