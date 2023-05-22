/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.querydesigner.jsonql;

import com.jaspersoft.studio.data.querydesigner.json.JsonQueryDesigner;

import net.sf.jasperreports.data.json.JsonExpressionLanguageEnum;

/**
 * Query designer for JSONQL language.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class JsonQLQueryDesigner extends JsonQueryDesigner {

	@Override
	protected String getLanguage() {
		return JsonExpressionLanguageEnum.JSONQL.getName();
	}
	
}
