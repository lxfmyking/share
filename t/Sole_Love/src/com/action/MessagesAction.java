package com.action;

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.model.Messages;
import com.model.Users;
import com.service.MessagesService;

@Controller
@Scope("prototype")
public class MessagesAction {

	@Resource
	MessagesService messagesService;

	// ��������
	private String result;
	private String error;

	/**
	 * ��ѯվ����
	 * 
	 * @return
	 */
	public String findMsgInfo() {
		
		Users user = (Users) ServletActionContext.getRequest().getSession().getAttribute(
				"User");
		if (user != null) {
			List<Messages> list = messagesService.findMsgInfo(user.getId());
			JSONArray ja = JSONArray.fromObject(list);
			result=ja.toString();
			return "success";
		}
		result = "[{\"message\":\"�û�δ��¼\"}]";
		return "success";
	}

	/**
	 * ��վ����
	 * 
	 * @return
	 */
	@Id
	@GeneratedValue
	int msgId;// վ����ID

	public String readMesInfo() {
		Users user = (Users) ServletActionContext.getRequest().getSession().getAttribute(
				"User");
		if (user != null) {
			List<Messages> list = messagesService.readMesInfo(user.getId(),
					msgId);
			JSONArray ja = JSONArray.fromObject(list);
			StringBuffer sb=new StringBuffer();
			sb.append(ja.toString());
			sb.append("{\"message\":\"�޴���\"}");
			result=sb.toString();
			return "success";
		}
		result = "{\"message\":\"�û�δ��¼\"}";
		return "success";
	}

	/**
	 * �½�վ����
	 * 
	 * @return
	 */
	public String addMsgInfo() {
		Users user = (Users) ServletActionContext.getRequest().getSession().getAttribute(
				"User");
		if (user != null) {
			Messages messages = new Messages();
			messages.setUid(user.getId());
			messagesService.addMsgInfo(messages);
			return "success";
		}
		return "error";
	}

	/**
	 * ɾ��վ����
	 * 
	 * @return
	 */
	int[] id;

	public String delMsgInfo() {
		Users user = (Users) ServletActionContext.getRequest().getSession().getAttribute(
				"User");
		if (user != null) {
			List<Messages> list = messagesService.delMsgInfo(id, user.getId());
			JSONArray ja = JSONArray.fromObject(list);
			result = ja.toString();
			error = "{\"message\":\"�޴���\"}";
			return "success";
		}
		error = "{\"message\":\"�û�δ��¼\"}";
		return "success";
	}

	public MessagesService getMessagesService() {
		return messagesService;
	}

	public void setMessagesService(MessagesService messagesService) {
		this.messagesService = messagesService;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
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
