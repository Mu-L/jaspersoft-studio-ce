/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.community.issues.enums;

import com.jaspersoft.studio.community.messages.Messages;

/**
 * Enumeration representing the issue category.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public enum Category {
	BugReport("bug",Messages.Category_BugReport), //$NON-NLS-1$
	Task("task",Messages.Category_Task), //$NON-NLS-1$
	FeatureRequest("feature",Messages.Category_FeatureRequest), //$NON-NLS-1$
	Enhancement("enhancement", Messages.Category_EnhancementRequest), //$NON-NLS-1$
	General("general",Messages.Category_General), //$NON-NLS-1$
	Patch("patch",Messages.Category_Patch); //$NON-NLS-1$
	
	public static final String FIELD_NAME = "field_bug_category"; //$NON-NLS-1$
	private String value;
	private String text;
	
	private Category(String value,String text){
		this.value = value;
		this.text = text;
	}
	
	public String getText(){
		return this.text;
	}
	
	public String getValue() {
		return this.value;
	}
	
}
