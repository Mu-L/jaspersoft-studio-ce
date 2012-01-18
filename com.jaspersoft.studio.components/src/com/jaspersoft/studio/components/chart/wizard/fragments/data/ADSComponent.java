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
package com.jaspersoft.studio.components.chart.wizard.fragments.data;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.charts.design.JRDesignTimePeriodDataset;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesDataset;
import net.sf.jasperreports.charts.design.JRDesignXyDataset;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementDataset;
import net.sf.jasperreports.engine.export.draw.DrawVisitor;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.jaspersoft.studio.components.chart.figure.ChartFigure;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.dialog.ChartDatasetDialog;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.widget.DatasetSeriesWidget;
import com.jaspersoft.studio.editor.gef.figures.FrameFigure;
import com.jaspersoft.studio.editor.gef.figures.JRComponentFigure;
import com.jaspersoft.studio.editor.java2d.J2DLightweightSystem;
import com.jaspersoft.studio.utils.UIUtils;

public abstract class ADSComponent {
	private Control control;
	protected Label imgLabel;
	private FrameFigure chartFigure;
	private Canvas canvasChart;
	private JRDesignElement jrElement;
	private JRDesignElementDataset eDataset;
	private DatasetSeriesWidget dsWidget;
	private LightweightSystem lws;
	private Button btDatasetType;

	public ADSComponent(Composite composite, DatasetSeriesWidget dsWidget) {
		createControl(composite);
		this.dsWidget = dsWidget;
	}

	public abstract String getName();

	public void setData(DrawVisitor drawVisitor, JRDesignElement jrChart,
			JRDesignElementDataset eDataset, SimpleFileResolver fResolver) {
		this.jrElement = jrChart;
		this.eDataset = eDataset;
		jrElement.setWidth(canvasChart.getSize().x);
		jrElement.setHeight(canvasChart.getSize().y);
		setChartFigure();
		chartFigure.setJRElement(jrElement, drawVisitor, fResolver);
		canvasChart.redraw();
		btDatasetType.setEnabled(false);
		if (jrElement instanceof JRDesignChart) {
			JRDesignChart jrDChart = (JRDesignChart) jrElement;
			if (jrDChart.getChartType() == JRChart.CHART_TYPE_XYBAR)
				btDatasetType.setEnabled(true);
		}
	}

	public Control getControl() {
		return control;
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		btDatasetType = new Button(composite, SWT.PUSH | SWT.FLAT);
		btDatasetType.setText(getName());
		btDatasetType.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Map<Class<? extends JRDesignElementDataset>, String> map = new HashMap<Class<? extends JRDesignElementDataset>, String>();
				if (jrElement instanceof JRDesignChart) {
					JRDesignChart jrDChart = (JRDesignChart) jrElement;
					if (jrDChart.getChartType() == JRChart.CHART_TYPE_XYBAR) {
						map.put(JRDesignTimePeriodDataset.class, dsWidget
								.getName(JRDesignTimePeriodDataset.class));
						map.put(JRDesignTimeSeriesDataset.class, dsWidget
								.getName(JRDesignTimeSeriesDataset.class));
						map.put(JRDesignXyDataset.class,
								dsWidget.getName(JRDesignXyDataset.class));

					}
					if (!map.isEmpty()) {
						Class<? extends JRDesignElementDataset> selclass = (Class<? extends JRDesignElementDataset>) jrDChart
								.getDataset().getClass();
						ChartDatasetDialog dialog = new ChartDatasetDialog(
								btDatasetType.getShell(), map, selclass);
						if (dialog.open() == Window.OK) {
							Class<? extends JRDesignElementDataset> newselclass = dialog
									.getSelection();
							if (!selclass.equals(newselclass))
								try {
									JRChartDataset jrded = (JRChartDataset) newselclass
											.getConstructor(
													JRChartDataset.class)
											.newInstance(jrDChart.getDataset());
									jrDChart.setDataset(jrded);
									dsWidget.setDataset(null, jrElement,
											eDataset);
								} catch (Exception e1) {
									UIUtils.showError(e1);
								}
						}
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		createChartTop(composite);
		new Label(composite, SWT.NONE).setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		createChartLeft(composite);

		createChartPreview(composite);

		createChartRight(composite);

		new Label(composite, SWT.NONE).setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		createChartBottom(composite);

		this.control = composite;
	}

	protected abstract Control createChartTop(Composite parent);

	protected abstract Control createChartLeft(Composite parent);

	protected abstract Control createChartRight(Composite parent);

	protected abstract Control createChartBottom(Composite parent);

	protected Control createChartPreview(Composite composite) {
		canvasChart = new Canvas(composite, SWT.NO_REDRAW_RESIZE
				| SWT.NO_BACKGROUND);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.widthHint = 250;
		canvasChart.setLayoutData(gd);

		lws = new J2DLightweightSystem();
		lws.setControl(canvasChart);
		canvasChart.addControlListener(new ControlListener() {

			public void controlResized(ControlEvent e) {
				if (jrElement != null) {
					jrElement.setWidth(canvasChart.getSize().x);
					jrElement.setHeight(canvasChart.getSize().y);
				}
			}

			public void controlMoved(ControlEvent e) {
			}
		});

		setChartFigure();

		return canvasChart;
	}

	private void setChartFigure() {
		if (jrElement instanceof JRChart)
			chartFigure = new ChartFigure();
		else
			// if (jrElement instanceof JRCommonElement)
			chartFigure = new JRComponentFigure();
		lws.setContents(chartFigure);
	}
}
