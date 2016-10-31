package com.shuogesha.cms.action.front;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.shuogesha.cms.Constants;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.service.DbFileService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.common.image.ImageScale;
import com.shuogesha.common.image.ImageUtils;

@Controller
public class ImageUploadAct {
	@RequestMapping(value = "/file/ck_image_Up.jhtml", method = RequestMethod.POST)
	public void ck(String uploadContentType,String uploadFileName,
			@RequestParam(value = "upload", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		String origName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(origName).toLowerCase(
				Locale.ENGLISH);
		if (!ImageUtils.isValidImageExt(ext)) {
			// TODO 异常处理（图片格式不正确）
		}
		String fileUrl = null;
		// 图片上传到mongodb
		try {
			if (!ImageUtils.isImage(file.getInputStream())) {
				// TODO 异常处理（不是图片异常）
			}
			String ctx = request.getContextPath();
			fileUrl = dbFileService.store(ext, file);// 上传原图片大小
			// 加上部署路径
//			Site site = CmsUtils.getSite(request);
//			fileUrl = site.getImageUrl() + fileUrl;
			fileUrl = "/upload/" + fileUrl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("uploadPath", fileUrl);
		StringBuffer sb = new StringBuffer(400);
		sb.append("<script type=\"text/javascript\">//try{//document.domain='shuogesha.com';}catch(e){} \n");
		String backFunc = (String) request.getParameter("CKEditorFuncNum");
		sb.append("window.parent.CKEDITOR.tools.callFunction("+ backFunc + ",'"+ fileUrl + "','')");
		sb.append("</script>");
		response.setContentType("text/html");
		response.getWriter().print(sb.toString());
		response.getWriter().flush();
	}
	
	private BufferedImage getBufferedImage(MultipartFile file) throws Exception {
		BufferedImage sourceImg = ImageIO.read(file.getInputStream());
		return sourceImg;
	}

	/**
	 * 把数据库图片缩小裁剪成指定大小图片
	 * 
	 * @param file
	 * @param boxWidth
	 * @param boxHeight
	 * @return
	 * @throws Exception
	 */
	private File resizeFix(GridFSDBFile bdFile, int boxWidth,
			int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
			throws Exception {
		String path = System.getProperty("java.io.tmpdir");
		File tempFile = new File(path, String.valueOf(System
				.currentTimeMillis()));
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) bdFile.getLength());
		bdFile.writeTo(bos);
	    byte[] b = bos.toByteArray();
	    bos.close();
		FileUtils.writeByteArrayToFile(tempFile, b);
		imageScale.resizeFix(tempFile, tempFile, boxWidth, boxHeight, cutTop,
				cutLeft, cutWidth, catHeight);
		return tempFile;
	}

	/**
	 * 把上传图片缩小成指定大小图片
	 * 
	 * @param file
	 * @param boxWidth
	 * @param boxHeight
	 * @return
	 * @throws Exception
	 */
	private File resize(MultipartFile file, Integer boxWidth, Integer boxHeight)
			throws Exception {
		String path = System.getProperty("java.io.tmpdir");
		File tempFile = new File(path, String.valueOf(System
				.currentTimeMillis()));
		file.transferTo(tempFile);
		imageScale.resizeFix(tempFile, tempFile, boxWidth, boxHeight);
		return tempFile;
	}
	
	@Autowired
	private DbFileService dbFileService;
	@Autowired
	private ImageScale imageScale;
}
