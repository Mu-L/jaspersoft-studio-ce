/*
 * JasperReports - Free Java Reporting Library. Copyright (C) 2001 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program is part of JasperReports.
 * 
 * JasperReports is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JasperReports is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with JasperReports. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.server.action.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;

import com.jaspersoft.ireport.jasperserver.ws.FileContent;
import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.server.WSClientHelper;
import com.jaspersoft.studio.server.model.MReportUnit;
import com.jaspersoft.studio.server.model.MResource;
import com.jaspersoft.studio.utils.SelectionHelper;
import com.jaspersoft.studio.utils.UIUtils;

public class RunReportUnitAction extends Action {
	private static final String ID = "RUNREPORTUNIT";
	private TreeViewer treeViewer;

	public RunReportUnitAction(TreeViewer treeViewer) {
		super();
		setId(ID);
		setText("Run Report Unit");
		setDescription("Run Report Unit");
		setToolTipText("Run the report unit");
		setImageDescriptor(JaspersoftStudioPlugin
				.getImageDescriptor("icons/resources/eclipse/start_task.gif")); //$NON-NLS-1$
		setDisabledImageDescriptor(JaspersoftStudioPlugin
				.getImageDescriptor("icons/resources/eclipse/start_task.gif")); //$NON-NLS-1$
		this.treeViewer = treeViewer;
	}

	@Override
	public void run() {
		final TreeSelection s = (TreeSelection) treeViewer.getSelection();
		TreePath[] p = s.getPaths();
		for (int i = 0; i < p.length; i++) {
			final Object obj = p[i].getLastSegment();
			if (obj instanceof MResource) {
				ProgressMonitorDialog pm = new ProgressMonitorDialog(Display
						.getDefault().getActiveShell());
				try {
					pm.run(true, true, new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								dorun(obj);
							} catch (Throwable e) {
								throw new InvocationTargetException(e);
							} finally {
								monitor.done();
							}
						}

					});
				} catch (InvocationTargetException e) {
					UIUtils.showError(e.getCause());
				} catch (InterruptedException e) {
					UIUtils.showError(e);
				}
				break;
			}
		}
	}

	protected void dorun(final Object obj) throws Exception,
			FileNotFoundException, IOException {
		MResource res = (MResource) obj;

		INode node = res.getReportUnit();
		if (node != null && node instanceof MReportUnit) {
			Map<String, Object> files = WSClientHelper
					.runReportUnit((MReportUnit) node);
			for (String key : files.keySet()) {
				FileContent fc = (FileContent) files.get(key);
				if (key.equals("jasperPrint")) {
					final File f = File.createTempFile("jrprint", ".jrprint");
					f.deleteOnExit();
					f.createNewFile();
					FileOutputStream htmlFile = new FileOutputStream(f);
					htmlFile.write(fc.getData());
					htmlFile.close();
					Display.getDefault().asyncExec(new Runnable() {

						public void run() {
							SelectionHelper.openEditor(f);
						}
					});

				}

				// else {
				// File f = new File("c:\\images");
				// if (!f.exists())
				// f.mkdirs();
				//
				// FileOutputStream imageFile = new FileOutputStream(
				// "c:\\images\\" + key);
				// imageFile.write(fc.getData());
				// imageFile.close();
				// }

			}
		}
	}
}
