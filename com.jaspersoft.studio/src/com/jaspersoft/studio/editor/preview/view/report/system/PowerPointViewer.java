/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.view.report.system;

import net.sf.jasperreports.eclipse.viewer.ReportViewer;
import net.sf.jasperreports.engine.JasperPrint;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.editor.preview.actions.export.AExportAction;
import com.jaspersoft.studio.editor.preview.actions.export.ExportAsPptxAction;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class PowerPointViewer extends ASystemViewer {

	public PowerPointViewer(Composite parent,  JasperReportsConfiguration jContext) {
		super(parent, jContext);
	}

	@Override
	protected AExportAction createExporter(ReportViewer rptv) {
		return new ExportAsPptxAction(rptv, jContext, null);
	}

	@Override
	protected String getExtension(JasperPrint jrprint) {
		return ".pptx";
	}

}
