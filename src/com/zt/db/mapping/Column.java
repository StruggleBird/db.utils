package com.zt.db.mapping;

/**
 *  �����е�ӳ����
 * @author zt
 * @since 2010-3-7
 */
public class Column {
	
	private Boolean isPrimary; //�Ƿ�Ϊ����
	
	private String name; //������
	
	private String fieldName; //������Ӧ���ֶ�����
	
	
	public String getFieldName() {
		return fieldName;
	}



	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}



	public Column( String name, String fieldName, Boolean isPrimary) {
		super();
		this.isPrimary = isPrimary;
		this.name = name;
		this.fieldName = fieldName;
	}



	public Column() {
		super();
	}
	
	

	public Column(String name) {
		super();
		this.name = name;
	}



	public Column( String name,Boolean isPrimary) {
		super();
		this.isPrimary = isPrimary;
		this.name = name;
	}
	
	

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
