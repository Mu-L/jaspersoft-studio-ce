/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.bean;

import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.messages.Messages;
import com.jaspersoft.studio.swt.widgets.ClassType;
import com.jaspersoft.studio.swt.widgets.ClasspathComponent;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.bean.BeanDataAdapter;
import net.sf.jasperreports.engine.JasperReportsContext;

public class BeanDataAdapterComposite extends ADataAdapterComposite {
	private ClassType factoryText;
	private Text methodText;
	private Button useFDcheck;
	private ClasspathComponent cpath;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public BeanDataAdapterComposite(Composite parent, int style, JasperReportsContext jrContext) {
		super(parent, style, jrContext);
		setLayout(new GridLayout(2, false));

		Label lblFactory = new Label(this, SWT.NONE);
		lblFactory.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1));
		lblFactory.setText(Messages.BeanDataAdapterComposite_0);

		factoryText = new ClassType(this, Messages.BeanDataAdapterComposite_1);
		factoryText.setClassType(Messages.BeanDataAdapterComposite_2);

		Label lblMethodName = new Label(this, SWT.NONE);
		lblMethodName.setText(Messages.BeanDataAdapterComposite_3);
		new Label(this, SWT.NONE);

		methodText = new Text(this, SWT.BORDER);
		methodText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		methodText.setText(Messages.BeanDataAdapterComposite_4);
		new Label(this, SWT.NONE);

		useFDcheck = new Button(this, SWT.CHECK);
		useFDcheck.setText(Messages.BeanDataAdapterComposite_5);
		useFDcheck.setSelection(true);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		useFDcheck.setLayoutData(gd);

		cpath = new ClasspathComponent(this) {
			@Override
			protected void handleClasspathChanged() {
				pchangesuport.firePropertyChange("dirty", false, true);
			}			
		};
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		cpath.getControl().setLayoutData(gd);
	}

	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		BeanDataAdapter bda = (BeanDataAdapter) dataAdapter;

		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(factoryText.getControl()),
				PojoProperties.value("factoryClass").observe(dataAdapter)); //$NON-NLS-1$
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(methodText),
				PojoProperties.value("methodName").observe(dataAdapter)); //$NON-NLS-1$
		bindingContext.bindValue(
				WidgetProperties.buttonSelection().observe(useFDcheck),
				PojoProperties.value("useFieldDescription").observe(dataAdapter)); //$NON-NLS-1$
		
		cpath.setClasspaths(bda.getClasspath());
	}

	public DataAdapterDescriptor getDataAdapter() {
		if (dataAdapterDesc == null)
			dataAdapterDesc = new BeanDataAdapterDescriptor();

		BeanDataAdapter bda = (BeanDataAdapter) dataAdapterDesc
				.getDataAdapter();
		bda.setFactoryClass(factoryText.getClassType());
		bda.setMethodName(methodText.getText().trim());

		bda.setUseFieldDescription(useFDcheck.getSelection());
		bda.setClasspath(cpath.getClasspaths());

		return dataAdapterDesc;
	}
	
	@Override
	public String getHelpContextId() {
		return PREFIX.concat("adapter_javabeans");
	}

}
