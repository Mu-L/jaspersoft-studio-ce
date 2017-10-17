/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.preview.view.report.system;

import java.io.File;

import net.sf.jasperreports.eclipse.viewer.ReportViewer;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRExportProgressMonitor;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.editor.preview.actions.export.AExportAction;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

/**
 * A simple wrapper for a custom exporter
 * 
 * @author Orlandin Marco
 *
 */
public abstract class ACustomViewer extends ASystemViewer {

	/**
	 * Create the exporter. In JSS the creation is done trough reflection so
	 * no exporter seems created directly
	 * 
	 * @param parent parent area if the user want to create custom controls by overriding the createControls method
	 * @param jContext the context of the current report
	 */
	public ACustomViewer(Composite parent, JasperReportsConfiguration jContext) {
		super(parent, jContext);
	}
	
	/**
	 * The exporter is not provided directly by the viewer but it is encapsulated in another object.
	 * This object is the AExportAction and this action is called when the user select the exporter item in
	 * the output menu. A basic implementation of this action is already provided by this method and 
	 * in most cases it will fit the exporter without need changes. However for advanced needs this can be 
	 * Overridden to provide a different action
	 * 
	 * @return a not null AExportAction
	 */
	@Override
	protected AExportAction createExporter(ReportViewer rptv) {
		return new AExportAction(rptv, jContext, null) {
			@Override
			protected JRAbstractExporter<?, ?, ?, ?> getExporter(JasperReportsConfiguration jContext,JRExportProgressMonitor monitor, File file) {
				return createExporter(jContext, monitor, file);
			}
		};
	}

	/**
	 * Return the JRAbstractExporter that will convert the current report into an external resource
	 * 
	 * @param jContext the context of the exported report
	 * @param monitor the jr monitor
	 * @param file the target file where the exporting operation
	 * @return a not null JRAbstractExporter
	 */
	protected abstract JRAbstractExporter<?, ?, ?, ?> createExporter(JasperReportsConfiguration jContext, JRExportProgressMonitor monitor, File file);

	/**
	 * Return the extension of the file that will be generated by this exporter. 
	 * 
	 * @return a string in the format .extension where the extension is the extension of
	 * the file that will be generated as target file of the export
	 */
	protected abstract String getExtension(JasperPrint jrprint);
}
