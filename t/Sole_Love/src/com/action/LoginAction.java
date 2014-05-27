package com.action;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.model.Blacklists;
import com.model.Salers;
import com.model.Users;
import com.service.BlacklistsService;
import com.service.SalersService;
import com.service.UsersService;
import com.util.MD5Util;
import com.util.RandomNumUtil;

@Controller
@Scope("prototype")
public class LoginAction {

	@Resource
	UsersService usersService;
	@Resource
	SalersService salersService;
	@Resource
	BlacklistsService blacklistsService;

	// ��������
	private String result;

	String mobile;// �ֻ���
	String email;// ����
	String password;// ����

	/**
	 * ��֤�ֻ����������Ƿ����
	 * 
	 * @param mobile
	 * @param email
	 * @return
	 */
	public String check_email_or_mobile_existed() {
		// System.out.println(mobile+"    "+email);
		boolean bool = usersService
				.check_email_or_mobile_existed(mobile, email);
		if (bool)
			result = "{\"message\":\"�޴���\"}";
		else
			result = "{\"message\":\"�ֻ����������Ѵ���\"}";
		return "success";
	}

	/**
	 * ��¼
	 * 
	 * @return
	 */

	public String login() {
		Users users = null;
		MD5Util md5 = new MD5Util();
		String ip = ServletActionContext.getRequest().getRemoteAddr();
		Blacklists blacklists = blacklistsService.findBlacklistsInfo(ip);
		String md = md5.string2MD5(password);
		// System.out.println(blacklists.getCount()+"    "+mobile+"   "+password);
		if (blacklists.getCount() >= 3) {
			// System.out.println("blacklists.getCount() == 3");
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// �������ڸ�ʽ
			String f = df.format(new Date());
			String l = df.format(blacklists.getUpdatetime().getTime() + 600000);
			usersService.locked(mobile, email);// �����˺�
			if (Double.parseDouble(f) > Double.parseDouble(l)) {
				usersService.unlock(mobile, email);// �����˺�
				if (mobile != null && password != null) {
					users = usersService.login(ip, mobile, null, md);// �û���Ϣ
				} else if (email != null && password != null) {
					users = usersService.login(ip, null, email, md);
				}
				if (users != null) {
					//System.out.println("Login---------111------>users != null");
					Salers salers = salersService.findSalersInfo(users.getId());// �ײ�ʹ����Ϣ
					ServletActionContext.getRequest().getSession()
							.setAttribute("User", users);
					ServletActionContext.getRequest().getSession()
							.setAttribute("Saler", salers);
					usersService.loginDate(mobile, email);// ��½����
					blacklistsService.clearErrCount(ip);// ��½�����������
					JSONArray ja = new JSONArray();
					ja.add(users);
					ja.add(salers);
					result = ja.toString();
					
					return "success";
				} else if (users == null) {
					blacklistsService.logErrCount(ip);
					result = "{\"message\":\"�û����������벻��ȷ\"}";
					return "success";
				}
			}
			result = "{\"message\":\"�˺�������,10�����ڲ��ɵ�¼\"}";
			return "success";
		} else if (blacklists.getCount() >= 10) {
			result = "{\"message\":\"IP��ַ�ѱ�Block\"}";
			return "success";
		} else {
			if (mobile != null && password != null) {
				users = usersService.login(ip, mobile, null, md);// �û���Ϣ
			} else if (email != null && password != null) {
				users = usersService.login(ip, null, email, md);
			}
			if (users != null) {
				System.out.println("Login--------222------->users != null");
				Salers salers = salersService.findSalersInfo(users.getId());// �ײ�ʹ����Ϣ
				ServletActionContext.getRequest().getSession()
						.setAttribute("User", users);
				ServletActionContext.getRequest().getSession()
						.setAttribute("Saler", salers);
				usersService.loginDate(mobile, email);// ��½����
				blacklistsService.clearErrCount(ip);// ��½�����������
				JSONArray ja = new JSONArray();
				ja.add(users);
				ja.add(salers);
				result = ja.toString();
				return "success";
			} else if (users == null) {
				blacklistsService.logErrCount(ip);
				result = "{\"message\":\"�û����������벻��ȷ\"}";
				return "success";
			}
		}
		return "success";
	}

	/**
	 * �˳���¼
	 * 
	 * @return
	 */
	public String logOut() {
		Users user = (Users) ServletActionContext.getRequest().getSession()
				.getAttribute("User");
		if (user != null) {
			ServletActionContext.getRequest().getSession()
					.removeAttribute("User");// ��session�е��û���Ϣ���
			return "success";
		}
		return "success";
	}

	/**
	 * ��֤��
	 * 
	 * @return
	 */
	private ByteArrayInputStream inputStream;

	public String identifieCode() {
		RandomNumUtil rdnu = RandomNumUtil.Instance();
		this.setInputStream(rdnu.getImage());// ȡ�ô�������ַ�����ͼƬ
		ServletActionContext.getRequest().getSession()
				.setAttribute("identifie", rdnu.getString());// ȡ������ַ�������Session
		return "success";
	}

	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SalersService getSalersService() {
		return salersService;
	}

	public void setSalersService(SalersService salersService) {
		this.salersService = salersService;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public BlacklistsService getBlacklistsService() {
		return blacklistsService;
	}

	public void setBlacklistsService(BlacklistsService blacklistsService) {
		this.blacklistsService = blacklistsService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
