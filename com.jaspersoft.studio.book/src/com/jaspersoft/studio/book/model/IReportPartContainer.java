/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.book.model;

import java.util.List;

import net.sf.jasperreports.engine.design.JRDesignSection;

@Deprecated
public interface IReportPartContainer {

	List<MReportPart> getReportParts();
	
	JRDesignSection getSection();
	
}
