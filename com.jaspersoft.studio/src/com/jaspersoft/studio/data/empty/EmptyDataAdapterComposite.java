/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.empty;

import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.messages.Messages;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.engine.JasperReportsContext;

public class EmptyDataAdapterComposite extends ADataAdapterComposite {

	private Spinner spinnerRecords;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public EmptyDataAdapterComposite(Composite parent, int style, JasperReportsContext jrContext) {
		super(parent, style, jrContext);
		setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText(Messages.EmptyDataAdapterComposite_0);

		spinnerRecords = new Spinner(this, SWT.BORDER);
		spinnerRecords.setValues(0, 0, Integer.MAX_VALUE, 0, 1, 10);
		spinnerRecords.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		bindingContext.bindValue(
				WidgetProperties.widgetSelection().observe(spinnerRecords),
				PojoProperties.value("recordCount").observe(dataAdapter)); //$NON-NLS-1$
	}

	public DataAdapterDescriptor getDataAdapter() {
		if (dataAdapterDesc == null)
			dataAdapterDesc = new EmptyDataAdapterDescriptor();

		((EmptyDataAdapterDescriptor) dataAdapterDesc).getDataAdapter().setRecordCount(spinnerRecords.getSelection());
		return dataAdapterDesc;
	}

	
	@Override
	public String getHelpContextId() {
		return PREFIX.concat("adapter_empty"); //$NON-NLS-1$
	}
}
