/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.ejbql;

import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.messages.Messages;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.ejbql.EjbqlDataAdapter;
import net.sf.jasperreports.engine.JasperReportsContext;

public class EjbqlDataAdapterComposite extends ADataAdapterComposite {

	private Text punitName;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public EjbqlDataAdapterComposite(Composite parent, int style, JasperReportsContext jrContext) {
		super(parent, style, jrContext);
		setLayout(new GridLayout(1, false));

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText(Messages.EjbqlDataAdapterComposite_0);

		punitName = new Text(this, SWT.BORDER);
		punitName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(punitName),
				PojoProperties.value("persistanceUnitName").observe(dataAdapter)); //$NON-NLS-1$
	}

	public DataAdapterDescriptor getDataAdapter() {
		if (dataAdapterDesc == null)
			dataAdapterDesc = new EjbqlDataAdapterDescriptor();

		EjbqlDataAdapter dataAdapter = (EjbqlDataAdapter) dataAdapterDesc
				.getDataAdapter();

		dataAdapter.setPersistanceUnitName(punitName.getText());

		return dataAdapterDesc;
	}

	
	@Override
	public String getHelpContextId() {
		return PREFIX.concat("adapter_EJBQL");
	}
}
