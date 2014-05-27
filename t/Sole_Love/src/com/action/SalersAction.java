package com.action;

import javax.annotation.Resource;
import javax.crypto.spec.IvParameterSpec;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dao.SalersDao;
import com.model.Salers;
import com.model.Users;
import com.service.InvcodesService;
import com.service.SalersService;
import com.service.UsersService;

@Controller
@Scope("prototype")
public class SalersAction {

	@Resource
	SalersService salersService;
	@Resource
	InvcodesService invcodesService;
	@Resource
	UsersService usersService;

	// ��������
	private String result;
	private String error;

	/**
	 * �ײ�ʹ����Ϣ
	 * 
	 * @return
	 */
	public String findSalersInfo() {
		
		Users user = (Users) ServletActionContext.getRequest().getSession()
				.getAttribute("User");
		if (user != null) {
			Salers salers = salersService.findSalersInfo(user.getId());
			JSONObject jo = JSONObject.fromObject(salers);
			StringBuffer sb=new StringBuffer();
			sb.append(jo.toString());
			sb.append("[{\"message\":\"�޴���\"}]");
			result=sb.toString();
			return "success";
		}
		result = "[{\"message\":\"�û�δ��¼\"}]";
		return "success";
	}

	String invcodes;// ������

	/**
	 * ע���ײ�ʹ��
	 * 
	 * @return
	 */

	String idcode;// ���֤
	String tel;// ��ϵ��ʽ
	String address;// ��ַ

	public String addSalersInfo() {
		Users user = (Users) ServletActionContext.getRequest().getSession()
				.getAttribute("User");
		if (user != null) {
			Salers salers = salersService.addSalersInfo(user.getId(), idcode,
					invcodes);
			user = usersService.updateUserInfo(user.getId(), tel, address);
			ServletActionContext.getRequest().getSession()
					.removeAttribute("User");
			ServletActionContext.getRequest().getSession()
					.setAttribute("User", user);
			ServletActionContext.getRequest().getSession()
					.setAttribute("Saler", salers);
			JSONArray ja = new JSONArray();
			ja.add(user);
			ja.add(salers);
			StringBuffer sb=new StringBuffer();
			sb.append(ja.toString());
			sb.append("[{\"message\":\"�޴���\"}]");
			result=sb.toString();
			return "success";
		}
		result = "[{\"message\":\"�û�δ��¼\"}]";
		return "success";
	}
	
	public String findInvcodesInfo() {
		Users user = (Users) ServletActionContext.getRequest().getSession()
				.getAttribute("User");
		if (user!=null) {
			if (salersService.findInvcodesInfo(invcodes)) 
				result="[{\"message\":\"�ʸ�ſ���\",\"ble\":\"0\"}]";
			else
				result="[{\"message\":\"�ʸ�Ų�����\",\"ble\":\"1\"}]";
			return "success";
		}
		result = "[{\"message\":\"�û�δ��¼\"}]";
		return "success";
	}
	
	public SalersService getSalersService() {
		return salersService;
	}

	public void setSalersService(SalersService salersService) {
		this.salersService = salersService;
	}

	public InvcodesService getInvcodesService() {
		return invcodesService;
	}

	public void setInvcodesService(InvcodesService invcodesService) {
		this.invcodesService = invcodesService;
	}

	public String getInvcodes() {
		return invcodes;
	}

	public void setInvcodes(String invcodes) {
		this.invcodes = invcodes;
	}

	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
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
