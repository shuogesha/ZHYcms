package com.shuogesha.cms.web.shuogeshatag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.shuogesha.cms.entity.Role;
import com.shuogesha.cms.entity.User;
import com.shuogesha.cms.filter.AdminInterceptor;
import com.shuogesha.cms.service.RoleService;
import com.shuogesha.cms.web.CmsUtils;

public class ButtonUrlTag extends TagSupport {
	private static final long serialVersionUID = -7811902545513255473L;
 
	// 标签属性操作url
	private String url = null;

	/* 标签初始方法 */
	public int doStartTag() throws JspTagException {
	  boolean result = false;
      User user = CmsUtils.getUser((HttpServletRequest) pageContext.getRequest());//WebUtil是自定义的工具类，获取此时登陆的用户
      if(user!=null){
    	  if(user.getUsername().equals(AdminInterceptor.admin)){
        	  result=true;
          }else {
        	  if (user.getRoles()!=null) {
        		  for (Role role : user.getRoles()) {
            		  if (role.isAllPerms()) {
            			  result=true;
            			  break;
            		  }
            	  }
        	  }
        	  List<String> list= user.getPerms();
              if (list!=null) {
            	 for (int i = 0;i < list.size(); i++) {
          			if (url.startsWith(list.get(i))) {
          				result=true;
          				break;
          			}
          		}
              }
          }
      }
      return result? EVAL_BODY_INCLUDE : SKIP_BODY;//真：返回EVAL_BODY_INCLUDE（执行标签）；假：返回SKIP_BODY（跳过标签不执行）
	}
	public int doEndTag() throws JspTagException {
		 return super.SKIP_BODY;
	}

	/* 释放资源 */
	public void release() {
		super.release();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Autowired
	public RoleService roleService;
}
