/*******************************************************************************
 * Copyright (C) 2010 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.data.sql.model.query.from;

import net.sf.jasperreports.engine.JRConstants;

import org.eclipse.jface.viewers.StyledString;

import com.jaspersoft.studio.data.sql.model.metadata.MSqlTable;
import com.jaspersoft.studio.data.sql.model.query.AMKeyword;
import com.jaspersoft.studio.data.sql.model.query.subquery.MQueryTable;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.preferences.fonts.utils.FontUtils;

public class MFromTableJoin extends MFromTable {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public MFromTableJoin(MFromTable parent, MSqlTable value) {
		this(parent, value, -1);
	}

	public MFromTableJoin(MFromTable parent, MSqlTable value, int index) {
		super(parent, value, index);

	}

	@Override
	public void setParent(ANode newparent, int newIndex) {
		if (newparent == null) {
			tableJoin.getFromTable().removeTableJoin(tableJoin);
			tableJoin = null;
		} else
			tableJoin = new TableJoin(this, (MFromTable) newparent);
		super.setParent(newparent, newIndex);
	}

	@Override
	public MSqlTable getValue() {
		return (MSqlTable) super.getValue();
	}

	private String join = AMKeyword.INNER_JOIN;

	public String getJoin() {
		return join;
	}

	public void setJoin(String join) {
		this.join = join;
	}

	@Override
	public String getToolTip() {
		return join + " " + super.getToolTip() + " ON ";
	}

	@Override
	public String getDisplayText() {
		String s = " " + join + " ";
		if (getValue() instanceof MQueryTable)
			return s + "(";
		return s + super.getDisplayText() + " ON ";
	}

	@Override
	public StyledString getStyledDisplayText() {
		StyledString dt = new StyledString(join + " ", FontUtils.KEYWORDS_STYLER);
		String tbltext = super.getDisplayText();
		if (getValue() instanceof MQueryTable)
			return dt.append("(");
		int ind = (join + " " + tbltext).indexOf(" AS ");
		dt.append(tbltext);
		if (ind >= 0)
			dt.setStyle(ind, " AS ".length(), FontUtils.KEYWORDS_STYLER);
		dt.append(" ON ", FontUtils.KEYWORDS_STYLER);
		return dt;
	}

	private TableJoin tableJoin;

	public TableJoin getTableJoin() {
		return tableJoin;
	}

	public void setTableJoin(TableJoin tableJoin) {
		this.tableJoin = tableJoin;
	}

	public String toSQLString() {
		return "\n\t" + getDisplayText();
	}

}
