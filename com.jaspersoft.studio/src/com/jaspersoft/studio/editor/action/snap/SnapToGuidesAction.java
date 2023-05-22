/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.action.snap;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.preferences.RulersGridPreferencePage;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class SnapToGuidesAction extends ACheckResourcePrefAction {
	public static final String ID = "PROPERTY_SNAP_TO_GUIDES_ENABLED"; //$NON-NLS-1$

	public SnapToGuidesAction(JasperReportsConfiguration jrConfig) {
		super(Messages.common_snap_to_guides, jrConfig);
		setText(Messages.common_snap_to_guides);
		setToolTipText(Messages.SnapToGuidesAction_show_grid_tool_tip);
		setId(ID);
	}

	@Override
	protected String getProperty() {
		return RulersGridPreferencePage.P_PAGE_RULERGRID_SNAPTOGUIDES;
	}
}
