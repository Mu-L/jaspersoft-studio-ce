/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.wizard.resource.page;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.server.messages.Messages;

public class ResourceDescriptorPage extends WizardPage {

	public ResourceDescriptorPage() {
		super(Messages.ResourceDescriptorPage_id);
		setTitle(Messages.ResourceDescriptorPage_title);
		setDescription(Messages.ResourceDescriptorPage_description);
	}

	public void createControl(Composite parent) {
		setControl(new Button(parent, SWT.ARROW_DOWN));
	}
}
