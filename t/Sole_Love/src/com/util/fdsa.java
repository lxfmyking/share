package com.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class fdsa {

	/**
	 * @param 
	 * @throws 
	 */
	public static void main(String[] args) {
		/*List<Integer> ch = new ArrayList<Integer>(); // ��һ��List�����500��С��
		for (int i = 1; i <= 500; i++) {
			ch.add(i);
		}
		int temp = 0;
		for (int j = 0; j < ch.size(); j++) {// ����List
			temp++;// ��������
			if (temp == 3) { // ���������3������С����List���Ƴ� ����1��ʼ����temp=0)
				ch.remove(j);
				j--;
				temp = 0;
			}
			if (j == ch.size() - 1)// ������������һ�������¿�ʼ���� j=-1 ��Ϊj++ �����Ǵ�j=0��ʼ���±���
				j = -1;
			if (ch.size() == 1)// ���List�Ĵ�СΪ1 ��ֻʣ�����һ��С�� ��ӡ���� ����
			{
				System.out.println(ch.get(0));
				break;
			}
		}*/
		 for (int i = 100; i <= 999; i++) {
	            int geWei, shiWei, baiWei;
	            baiWei = i / 100;
	            shiWei = (i - baiWei * 100) / 10;
	            geWei = i - baiWei * 100 - shiWei * 10;
	            if (i == Math.pow(geWei,3) + Math.pow(shiWei,3) + Math.pow(baiWei,3)) {
	                System.out.println(i);
	            }
	        }
	}

}
