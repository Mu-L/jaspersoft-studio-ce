/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.rcp.intro.action;

import java.util.Properties;

import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

public class OpenCheatSheetAction implements IIntroAction {

	public void run(IIntroSite site, Properties params) {
		String file = (String) params.get("csid");
		new org.eclipse.ui.cheatsheets.OpenCheatSheetAction(file).run();

	}

}
