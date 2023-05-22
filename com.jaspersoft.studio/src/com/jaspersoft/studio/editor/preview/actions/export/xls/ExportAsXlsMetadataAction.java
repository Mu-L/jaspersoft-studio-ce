/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.actions.export.xls;

import com.jaspersoft.studio.editor.preview.actions.export.ExportMenuAction;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.viewer.IReportViewer;
import net.sf.jasperreports.engine.export.JRExportProgressMonitor;
import net.sf.jasperreports.engine.export.JRXlsMetadataExporter;
import net.sf.jasperreports.export.SimpleXlsMetadataReportConfiguration;

@Deprecated
public class ExportAsXlsMetadataAction extends AExportXlsAction {

	public ExportAsXlsMetadataAction(IReportViewer viewer, JasperReportsConfiguration jContext, ExportMenuAction parentMenu) {
		super(viewer, jContext, parentMenu);

		setText(Messages.ExportAsXlsMetadataAction_title);
		setToolTipText(Messages.ExportAsXlsMetadataAction_tooltip);

		setFileExtensions(new String[] { "*.xls" }); //$NON-NLS-1$
		setFilterNames(new String[] { Messages.ExportAsXlsAction_filternames });
		setDefaultFileExtension("xls"); //$NON-NLS-1$
	}

	@Override
	protected JRXlsMetadataExporter createExporter(JasperReportsConfiguration jContext, JRExportProgressMonitor monitor) {
		JRXlsMetadataExporter exp = new JRXlsMetadataExporter(jContext);
		SimpleXlsMetadataReportConfiguration rconf = new SimpleXlsMetadataReportConfiguration();
		setupReportConfiguration(rconf, monitor);
		exp.setConfiguration(rconf);

		return exp;
	}
}
