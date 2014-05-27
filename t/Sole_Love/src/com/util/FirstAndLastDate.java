package com.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class FirstAndLastDate {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public String firstDate() {

		// ��ȡ��ǰ�µ�һ�죺
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// ����Ϊ1��,��ǰ���ڼ�Ϊ���µ�һ��
		String first = format.format(c.getTime());
		// System.out.println("===============first:" + first);
		return first;

	}

	public String lastDate() {
		// ��ȡ��ǰ�����һ��
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		// System.out.println("===============last:" + last);
		return last;
	}

	public String sysDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		// System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
		return df.format(new Date());
	}

	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		// System.out.println(df.format(new
		// Timestamp(System.currentTimeMillis())));
		// System.out.println(df.format(new Date()));
		// String f=df.format(new
		// Timestamp(System.currentTimeMillis())).replace(":", "").replace("-",
		// "");
		// String l=df.format(new Date(new
		// Date().getTime()+600000)).replace(":", "").replace("-", "");
		// if (Double.parseDouble(l)>Double.parseDouble(f)) {
		// System.out.println("aaaaaaa");
		// }
		
	}
}
