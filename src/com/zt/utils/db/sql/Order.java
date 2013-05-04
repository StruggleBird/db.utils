package com.zt.utils.db.sql;

/**
 * SQL ORDER
 * @author zt
 * @since 2010-3-26
 */
public class Order {
	
	public static final String ASC="asc";
	
	public static final String DESC="desc";
	
	private StringBuilder sbOrder = new StringBuilder(); //where 条件语句
	
	private boolean isFirstOrder=true;
	
	public boolean isEmpty()
	{
		return "".equals(sbOrder.toString().trim());
	}
	
	public String getOrderClause()
	{
		return this.sbOrder.toString();
	}
	
	public Order add(String orderStr){
		sbOrder.append(getprefix()).append(orderStr);
		return this;
	}
	
	public Order add(String orderStr,String mode){
		sbOrder.append(getprefix()).append(orderStr).append(" ").append(getSuffix(mode));
		return this;
	}
	
	public String getprefix(){
		
		if (isFirstOrder) {
			isFirstOrder=false;
			return "";
		}
		return ",";
	}
	
	public String getSuffix(String mode)
	{
		if (ASC.equalsIgnoreCase(mode)||DESC.equalsIgnoreCase(mode)) {
			return mode+" ";
		}
		throw new RuntimeException("排序方式: "+mode+" 错误");
	}
	
}
