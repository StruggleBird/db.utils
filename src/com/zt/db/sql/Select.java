package com.zt.db.sql;

import com.zt.util.StringUtil;

public class Select {
	private String selectClause;
	private String fromClause;
	private String outerJoinsAfterFrom;
	private String whereClause;
	private String outerJoinsAfterWhere;
	private String orderByClause;
	private String groupByClause;

	public String toStatementString() {
		StringBuilder sbSelect = new StringBuilder();

		sbSelect.append("select ").append(this.selectClause).append(" from ")
				.append(this.fromClause);

		if (!StringUtil.IsNullOrEmpty(this.outerJoinsAfterFrom)) {
			sbSelect.append(this.outerJoinsAfterFrom);
		}

		if ((!StringUtil.IsNullOrEmpty(this.whereClause))
				|| (!StringUtil.IsNullOrEmpty(this.outerJoinsAfterWhere))) {
			sbSelect.append(" where ");

			if (!StringUtil.IsNullOrEmpty(this.outerJoinsAfterWhere)) {
				sbSelect.append(this.outerJoinsAfterWhere);
				if (!StringUtil.IsNullOrEmpty(this.whereClause))
					sbSelect.append(" and ");
			}

			if (!StringUtil.IsNullOrEmpty(this.whereClause))
				sbSelect.append(this.whereClause);

		}

		if (!StringUtil.IsNullOrEmpty(this.groupByClause)) {
			sbSelect.append(" group by ").append(this.groupByClause);
		}

		if (!StringUtil.IsNullOrEmpty(this.orderByClause)) {
			sbSelect.append(" order by ").append(this.orderByClause);
		}

		return sbSelect.toString();
	}

	public Select setFromClause(String fromClause) {
		this.fromClause = fromClause;
		return this;
	}

	public Select setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
		return this;
	}

	public Select setGroupByClause(String groupByClause) {
		this.groupByClause = groupByClause;
		return this;
	}

	public Select setOuterJoins(String outerJoinsAfterFrom,
			String outerJoinsAfterWhere) {
		this.outerJoinsAfterFrom = outerJoinsAfterFrom;

		String tmpOuterJoinsAfterWhere = outerJoinsAfterWhere.trim();
		if (tmpOuterJoinsAfterWhere.startsWith("and"))
			tmpOuterJoinsAfterWhere = tmpOuterJoinsAfterWhere.substring(4);

		this.outerJoinsAfterWhere = tmpOuterJoinsAfterWhere;

		return this;
	}

	public Select setSelectClause(String selectClause) {
		this.selectClause = selectClause;
		return this;
	}

	public Select setWhereClause(String whereClause) {
		this.whereClause = whereClause;
		return this;
	}


}