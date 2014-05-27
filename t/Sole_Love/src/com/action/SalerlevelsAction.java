package com.action;

import javax.annotation.Resource;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.model.Salerlevels;
import com.model.Users;
import com.service.SalerlevelsService;

@Controller
@Scope("prototype")
public class SalerlevelsAction {

	@Resource
	SalerlevelsService salerlevelsService;

	// ��������
	private String result;
	private String error;

	/**
	 * ʹ�ߵȼ�����
	 * 
	 * @return
	 */
	public String findSalerlevelsInfo() {
		Salerlevels salerlevels = salerlevelsService.findSalerlevelsInfo();
		JSONObject jo = JSONObject.fromObject(salerlevels);
		StringBuffer sb=new StringBuffer();
		sb.append(jo.toString());
		sb.append("{\"message\":\"�޴���\"}");
		result=sb.toString();
		return "success";
	}

	/**
	 * ��ǰ�û�ʹ�ߵȼ�
	 * 
	 * @return
	 */
	public String userSalerLevel() {
		
		Users user = (Users) ServletActionContext.getRequest().getSession()
				.getAttribute("User");
		if (user != null) {
			Salerlevels salerlevels = salerlevelsService.userSalerLevel(user
					.getId());
			JSONObject jo = JSONObject.fromObject(salerlevels);
			StringBuffer sb=new StringBuffer();
			sb.append(jo.toString());
			sb.append("{\"message\":\"�޴���\"}");
			result=sb.toString();
			return "success";
		}
		result = "{\"message\":\"�û�δ��¼\"}";
		return "success";
	}

	public SalerlevelsService getSalerlevelsService() {
		return salerlevelsService;
	}

	public void setSalerlevelsService(SalerlevelsService salerlevelsService) {
		this.salerlevelsService = salerlevelsService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
