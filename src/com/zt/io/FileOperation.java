package com.zt.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

/**
 * �ļ���������
 * @author zt
 */
public class FileOperation {
	
	/**
	 * ͨ���ļ���Ի����·�� ��ȡ�ļ�����
	 * @param fileName �ļ�����
	 * @return �ļ�����
	 */
	public StringBuilder read(String fileName) {
		File file = new File(fileName);
		java.io.Reader in=null;
		StringBuilder sbContent=new StringBuilder();;
		try {
			in = new FileReader(file);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(in);
		
		try {
			String line="";
			while ((line=bufferedReader.readLine())!=null) {
				
				sbContent.append(line).append("\n");
			}
			
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sbContent;
	}
	
	/**
	 * ͨ�������ı�����ȡ����
	 * @param inputStream Ҫ����ת����������
	 * @return ��ȡ������
	 */
	public StringBuilder read(InputStream inputStream) {
		
		java.io.InputStreamReader reader=new InputStreamReader(inputStream);
		StringBuilder sbContent=new StringBuilder();
		try {
			int c=0;
			while ((c= reader.read())!=-1) {
				sbContent.append((char)c);
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sbContent;
	}
	
	
	/**
	 * д���ı��ļ�ͨ���ƶ����ļ����ƺ�����
	 * @param content Ҫд����ļ�����
	 * @param fileName Ҫд���ļ���·��
	 * @return �����Ƿ�ɹ�
	 */
	public boolean write(String content, String fileName) {
		Writer out = null;
		try {
			out = new FileWriter(fileName);
			
			BufferedWriter writer  = new BufferedWriter(out);
			
			writer.write(content);
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * ͨ��File�����ļ�·�� ���ļ�д�뵽ָ�������ļ�·����
	 * @param file Ҫд����ļ�
	 * @param filePath ���ļ�·��
	 * @return д���Ƿ�ɹ�
	 */
	public boolean writeFile(File file,String filePath){
		File newFile = new File(filePath);
		try {
			if (newFile.createNewFile()) {
				FileOutputStream fos = new FileOutputStream(file);
				
				InputStream is= new FileInputStream(file);
				
				byte[]bts = new byte[1024];
				while(is.read(bts)!=-1){
					fos.write(bts);
				}
				is.close();
				fos.close();
				return true;
			}else {
				return false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
