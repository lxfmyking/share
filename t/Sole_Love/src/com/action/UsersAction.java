package com.action;


import java.sql.Timestamp;

import javax.annotation.Resource;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.model.Salers;
import com.model.Users;
import com.service.SalersService;
import com.service.UsersService;
import com.util.MD5Util;
import com.util.VaildatorUtil;

@Controller
@Scope("prototype")
public class UsersAction {

	@Resource
	UsersService usersService;
	@Resource
	SalersService salersService;

	// ��������
	private String result;
	private String error;

	/**
	 * ��ѯ�û���Ϣ
	 * 
	 * @return
	 */
	public String findUsersInfo() {
		Users user = (Users) ServletActionContext.getRequest().getSession()
				.getAttribute("User");
		if (user != null) {
			Users u = usersService.findUsersInfo(user.getId());
			Salers saler = salersService.findSalersInfo(user.getId());
			ServletActionContext.getRequest().getSession()
					.removeAttribute("User");
			ServletActionContext.getRequest().getSession()
					.setAttribute("User", u);
			ServletActionContext.getRequest().getSession()
					.setAttribute("Saler", saler);
			JSONArray jo = new JSONArray();
			jo.add(u);
			jo.add(saler);
			result=jo.toString();
			return "success";
		}
		result = "[{\"message\":\"�û�δ��¼\"}]";
		return "success";
	}

	/**
	 * ע�����û�
	 * 
	 * @return
	 */
	String nickname;
	String mobile;// �ֻ���
	String email;// ����
	String password;
	String rand;

	public String addUserInfo() {
		//System.out.println(nickname+"    "+mobile+"    "+email+"    "+password+"    "+rand);
		Users users = new Users();
		if (mobile != null) {
			users.setMobile(mobile);
		} 
		if (email != null) {
			users.setEmail(email);
		}
		users.setNickname(nickname);
		users.setPassword(password);
		users.setNickname(nickname);
		users.setDelflag(0);
		users.setLocked(0);
		users.setCreationtime(new Timestamp(System.currentTimeMillis()));
		//System.out.println(VaildatorUtil.message(users).size());
		if (VaildatorUtil.message(users).size()!=0) {
			//System.out.println(VaildatorUtil.message(users));//����ֶ���Ϣ
			JSONObject jo=JSONObject.fromObject(VaildatorUtil.message(users));
			result=jo.toString();
			return "success";
		}
			
		
		// System.out.println(users.getNickname());
		MD5Util md5 = new MD5Util();

		String arandom = (String) (ServletActionContext.getRequest()
				.getSession().getAttribute("identifie"));
		// System.out.println(arandom + "          " + rand);
		// ��session�б�����֤���ַ�����ͻ��������֤���ַ����Ա���
		if (arandom.equals(rand.toUpperCase())) {
			// System.out.println("-------------------------->true");
			users.setPassword(md5.string2MD5(password));
			String ip = ServletActionContext.getRequest().getRemoteAddr();
			Users user = usersService.addUserInfo(users, ip);
			ServletActionContext.getRequest().getSession()
					.setAttribute("User", user);// �û���Ϣ��session
			// ��Ҫ���ض������json����
			JSONObject jo = JSONObject.fromObject(user);
			result = jo.toString();
			return "success";
		}
		return "success";
	}

	public String checkIdentifie() {
		String arandom = (String) (ServletActionContext.getRequest()
				.getSession().getAttribute("identifie"));
		// System.out.println(arandom + "          " + rand);
		// ��session�б�����֤���ַ�����ͻ��������֤���ַ����Ա���
		if (arandom.equals(rand.toUpperCase())) {
			result = "{\"message\":\"�޴���\"}";
			return "success";
		} else {
			result = "{\"message\":\"��֤�����\"}";
			return "success";
		}

	}

	
	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public SalersService getSalersService() {
		return salersService;
	}

	public void setSalersService(SalersService salersService) {
		this.salersService = salersService;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	

}
