package com.zt.web.taglib;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * ��֤���ǩ
 * @author zt
 * @since 2010-3-6
 */
public class ValidateCodeTag extends TagSupport  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1060324141549383034L;
	
	
	private HttpServletRequest request=null;
	
	private HttpServletResponse response=null;

	public ValidateCodeTag() {
		
		super();
	}

	@Override
	public int doStartTag() throws JspException {
		request = (HttpServletRequest) pageContext.getRequest();
		response= (HttpServletResponse) pageContext.getResponse();
		try {
			CreateCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	private void CreateCode() throws IOException {
		
		//����ҳ�治����
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", -1);
		
		
		// ���ڴ��д���ͼ��
		int width=60, height=20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		// ��ȡͼ��������
		Graphics g = image.getGraphics();
		
		//���������
		Random random = new Random();
		
		// �趨����ɫ
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, width, height);
		
		//�趨����
		g.setFont(new Font("Times New Roman",Font.PLAIN,18));
		
		//���߿�
		g.setColor(Color.black);
		g.drawRect(0,0,width-1,height-1);
		
		
		// �������155�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
		g.setColor(getRandColor(160,200));
		for (int i=0;i<155;i++)
		{
		 int x = random.nextInt(width);
		 int y = random.nextInt(height);
		        int xl = random.nextInt(12);
		        int yl = random.nextInt(12);
		 g.drawLine(x,y,x+xl,y+yl);
		}
		
		// ȡ�����������֤��(4λ����)
		String sRand="";
		String sRands="0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
		
		for (int i=0;i<4;i++){
			int index=random.nextInt(62);
		    String rand=sRands.charAt(index)+"";
		    sRand+=rand;
		    // ����֤����ʾ��ͼ����
		    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
		    g.drawString(rand,13*i+6,16);
		}
		
		// ����֤�����SESSION
		request.setAttribute("hrand",sRand);
		// ͼ����Ч
		g.dispose();
		
		// ���ͼ��ҳ��
		ServletOutputStream sos=response.getOutputStream();
		JPEGImageEncoder encoder =JPEGCodec.createJPEGEncoder(sos);
		encoder.encode(image);
		sos.close();
	}
	
	//�Ӹ�����Χ��������ɫ
	private Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
        
	}
	
}
