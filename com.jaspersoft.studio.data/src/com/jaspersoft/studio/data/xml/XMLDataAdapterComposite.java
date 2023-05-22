/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.xml;

import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.data.AFileDataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DateNumberFormatWidget;
import com.jaspersoft.studio.data.messages.Messages;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.xml.XmlDataAdapter;
import net.sf.jasperreports.data.xml.XmlDataAdapterImpl;
import net.sf.jasperreports.engine.JasperReportsContext;

public class XMLDataAdapterComposite extends AFileDataAdapterComposite {

	private Button btnRadioButtonUseXpath = null;
	private Button btnRadioButtonCreateDataAdapter = null;
	private Text textSelectExpression;
	private boolean useConnection = false;
	private Button supportsNamespaces;
	private DateNumberFormatWidget dnf;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public XMLDataAdapterComposite(Composite parent, int style,
			JasperReportsContext jrContext) {

		super(parent, style, jrContext);
		setLayout(new GridLayout(1, false));

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		GridLayout gl_composite = new GridLayout(3, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);

		createFileNameWidgets(composite);

		supportsNamespaces = new Button(this, SWT.CHECK);
		supportsNamespaces
				.setText(Messages.XMLDataAdapterComposite_NamespacesSupport);
		supportsNamespaces.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false));

		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		composite_1.setLayout(new FillLayout(SWT.VERTICAL));

		btnRadioButtonUseXpath = new Button(composite_1, SWT.RADIO);
		btnRadioButtonUseXpath.setText(Messages.XMLDataAdapterComposite_2);

		btnRadioButtonCreateDataAdapter = new Button(composite_1, SWT.RADIO);
		btnRadioButtonCreateDataAdapter
				.setText(Messages.XMLDataAdapterComposite_3);
		btnRadioButtonCreateDataAdapter.setSelection(true);

		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		GridLayout gl_composite_2 = new GridLayout(2, false);
		gl_composite_2.marginWidth = 0;
		gl_composite_2.marginHeight = 0;
		composite_2.setLayout(gl_composite_2);

		Label lblNewLabel_1 = new Label(composite_2, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_1.setText(Messages.XMLDataAdapterComposite_4);

		textSelectExpression = new Text(composite_2, SWT.BORDER);
		textSelectExpression.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

		@SuppressWarnings("unused")
		Label separator = new Label(composite_1, SWT.SEPARATOR | SWT.HORIZONTAL);

		dnf = new DateNumberFormatWidget(this);

		// UI elements listener
		btnRadioButtonUseXpath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textSelectExpression.setEnabled(false);
				useConnection = false;
				pchangesuport.firePropertyChange("createdataadapter", true, //$NON-NLS-1$
						false);
			}
		});

		btnRadioButtonCreateDataAdapter
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						textSelectExpression.setEnabled(true);
						useConnection = true;
						pchangesuport.firePropertyChange("createdataadapter", //$NON-NLS-1$
								false, true);
					}
				});
	}

	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		XmlDataAdapter xmlDataAdapter = (XmlDataAdapter) dataAdapter;

		doBindFileNameWidget(xmlDataAdapter);
		bindingContext.bindValue(
				WidgetProperties.widgetSelection().observe(supportsNamespaces),
				PojoProperties.value("namespaceAware").observe(dataAdapter)); //$NON-NLS-1$
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(textSelectExpression),
				PojoProperties.value("selectExpression").observe(dataAdapter)); //$NON-NLS-1$

		dnf.bindWidgets(xmlDataAdapter, bindingContext,
				xmlDataAdapter.getLocale(), xmlDataAdapter.getTimeZone());

		bindingContext.bindValue(
				WidgetProperties.widgetSelection().observe(btnRadioButtonCreateDataAdapter),
				PojoProperties.value("useConnection").observe(dataAdapter)); //$NON-NLS-1$

		if (!xmlDataAdapter.isUseConnection()) {
			useConnection = true;
			btnRadioButtonCreateDataAdapter.setSelection(true);
			textSelectExpression.setEnabled(true);
			btnRadioButtonUseXpath.setSelection(false);
		} else {
			useConnection = false;
			btnRadioButtonUseXpath.setSelection(true);
			btnRadioButtonCreateDataAdapter.setSelection(false);
			textSelectExpression.setEnabled(false);
		}

	}

	public DataAdapterDescriptor getDataAdapter() {
		if (dataAdapterDesc == null)
			dataAdapterDesc = new XMLDataAdapterDescriptor();

		XmlDataAdapterImpl dataAdapter = (XmlDataAdapterImpl) dataAdapterDesc
				.getDataAdapter();

		dataAdapter.setUseConnection(!useConnection);
		dataAdapter.setSelectExpression(textSelectExpression.getText());
		dataAdapter.setNamespaceAware(supportsNamespaces.getSelection());
		dataAdapter.setDatePattern(dnf.getTextDatePattern());
		dataAdapter.setNumberPattern(dnf.getTextNumberPattern());
		dataAdapter.setLocale(dnf.getLocale());
		dataAdapter.setTimeZone(dnf.getTimeZone());

		return dataAdapterDesc;
	}

	@Override
	public String getHelpContextId() {
		return PREFIX.concat("adapter_xml"); //$NON-NLS-1$
	}

	@Override
	protected String[] getFileExtensions() {
		return new String[] { "*.xml", "*.*" };
	}
}
