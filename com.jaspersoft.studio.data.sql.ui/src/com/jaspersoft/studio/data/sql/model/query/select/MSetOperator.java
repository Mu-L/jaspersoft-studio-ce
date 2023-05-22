/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.sql.model.query.select;

import net.sf.jasperreports.engine.JRConstants;

import com.jaspersoft.studio.data.sql.model.query.AMKeyword;
import com.jaspersoft.studio.model.ANode;

public class MSetOperator extends AMKeyword {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public MSetOperator(ANode parent) {
		super(parent, AMKeyword.SET_OPERATOR_UNION, null);
	}

	@Override
	public String toSQLString() {
		return getValue() + " ";
	}

}
