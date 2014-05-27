package com.action;

import javax.annotation.Resource;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.model.Infos;
import com.service.InfosService;

@Controller
@Scope("prototype")
public class InfosAction {

	@Resource
	InfosService infosService;

	String verify;
	
	// ��������
	private String result;
	private String error;
	
	/**
	 * �Żݴ�����֤
	 * @return
	 */
	public String checkVerify() {
		Infos infos = infosService.checkVerify(verify);
		if (infos != null) {
			result = "[{\"message\":\"�޴���\"}]";
			return "success";
		}
		result = "[{\"message\":\"�Żݴ��벻����\"}]";
		return "success";
	}

	public InfosService getInfosService() {
		return infosService;
	}

	public void setInfosService(InfosService infosService) {
		this.infosService = infosService;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
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
