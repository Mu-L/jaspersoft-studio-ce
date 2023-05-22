/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.chart.wizard.fragments.data.series;

import java.util.List;

import net.sf.jasperreports.engine.design.JRDesignExpression;

public interface ISeriesFactory<T> {
	public Object createSerie();

	public T createSerie(JRDesignExpression expr, T prevValue);

	public String getColumnText(Object element, int columnIndex);

	public Object getValue(T element, String property);

	public void modify(T element, String property, Object value);

	public List<T> getList();

	public void setList(List<T> vlist);
}
