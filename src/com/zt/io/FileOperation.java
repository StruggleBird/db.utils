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
 * 文件流操作类
 * @author zt
 */
public class FileOperation {
	
	/**
	 * 通过文件相对或绝对路径 获取文件内容
	 * @param fileName 文件名称
	 * @return 文件内容
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
	 * 通过输入文本流获取内容
	 * @param inputStream 要进行转换的输入流
	 * @return 读取的内容
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
	 * 写入文本文件通过制定的文件名称和内容
	 * @param content 要写入的文件内容
	 * @param fileName 要写入文件的路径
	 * @return 操作是否成功
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
	 * 通过File和新文件路径 将文件写入到指定的新文件路径中
	 * @param file 要写入的文件
	 * @param filePath 新文件路径
	 * @return 写入是否成功
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
