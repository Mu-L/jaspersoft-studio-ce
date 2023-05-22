/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.sql.widgets.scalar;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.data.designer.AQueryDesigner;
import com.jaspersoft.studio.data.sql.model.query.operand.ScalarOperand;

public class StringWidget extends AScalarWidget {
	private Text txt;

	public StringWidget(Composite parent, ScalarOperand<String> operand, AQueryDesigner designer) {
		super(parent, SWT.NONE, operand, designer);

	}

	@Override
	protected void createWidget(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 2;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		setLayout(layout);

		txt = new Text(this, SWT.BORDER);
		txt.setText(getValue().toSQLString());
		txt.setToolTipText(getValue().toSQLString());
		txt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		DataBindingContext bindingContext = new DataBindingContext();
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(txt),
				PojoProperties.value("value").observe(getValue())); //$NON-NLS-1$
	}

}
