/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data;

import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.messages.Messages;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.engine.JasperReportsContext;

public class NameComposite extends ADataAdapterComposite {
	private DataAdapterDescriptor dataAdapterDesc = null;
	private Text textName;

	public NameComposite(Composite parent, int style, JasperReportsContext jrContext) {
		super(parent, style, jrContext);
		setLayout(new GridLayout(2, false));

		Label lblName = new Label(this, SWT.NONE);
		lblName.setText(Messages.NameComposite_0);

		textName = new Text(this, SWT.BORDER);
		textName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);
	}

	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(textName),
				PojoProperties.value("name").observe(dataAdapter)); //$NON-NLS-1$
	}

	@Override
	public DataAdapterDescriptor getDataAdapter() {
		return dataAdapterDesc;
	}

}
