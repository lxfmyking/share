package com.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.persistence.Entity;

@Entity
public class RandomNumUtil {
	private ByteArrayInputStream image;// ͼ��
	private String str;// ��֤��

	private RandomNumUtil() {
		init();// ��ʼ������
	}

	/*
	 * ȡ��RandomNumUtilʵ��
	 */
	public static RandomNumUtil Instance() {
		return new RandomNumUtil();
	}

	/*
	 * ȡ����֤��ͼƬ
	 */
	public ByteArrayInputStream getImage() {
		return this.image;
	}

	/*
	 * ȡ��ͼƬ����֤��
	 */
	public String getString() {
		return this.str;
	}
	private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',  
            'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',  
            'X', 'Y', 'Z','1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private void init() {
		// ���ڴ��д���ͼ��
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// ��ȡͼ��������
		Graphics g = image.getGraphics();
		// ���������
		Random random = new Random();
		// �趨����ɫ
		g.setColor(getRandColor(180, 230));
		g.fillRect(0, 0, width, height);
		// �趨����
		g.setFont(new Font("Courier", Font.ITALIC, 18));
		// �������155�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// ȡ�����������֤��(4λ����)
		String sRand = "";
		 // randomCode��¼�����������֤��  
        StringBuffer randomCode = new StringBuffer();  
        // �������codeCount���ַ�����֤�롣  
        for (int i = 0; i < 4; i++) {  
            sRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);  
            // �����������ɫֵ���������ÿ���ַ�����ɫֵ������ͬ��  
           
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(sRand, (i + 1) * 8, 15);  
            // ������������������һ��  
            randomCode.append(sRand);  
        }  
		/*for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// ����֤����ʾ��ͼ����
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// ���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
			g.drawString(rand, 13 * i + 6, 16);
		}*/
		// ��ֵ��֤��
		this.str = randomCode.toString();
		// ͼ����Ч
		g.dispose();
		ByteArrayInputStream input = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ImageOutputStream imageOut = ImageIO
					.createImageOutputStream(output);
			ImageIO.write(image, "JPEG", imageOut);
			imageOut.close();
			input = new ByteArrayInputStream(output.toByteArray());
		} catch (Exception e) {
			System.out.println("��֤��ͼƬ�������ִ���" + e.toString());
		}
		this.image = input;/* ��ֵͼ�� */
	}

	/*
	 * ������Χ��������ɫ
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
