/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.dataset.fields.table.column;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.property.dataset.fields.table.TColumn;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionDTO;
import com.jaspersoft.studio.property.descriptor.propexpr.cell.JRPropertyExpressionCellEditor;

import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignPropertyExpression;
import net.sf.jasperreports.engine.type.ExpressionTypeEnum;

public class JRPropertyColumnSupport extends PropertyColumnSupport {

	public JRPropertyColumnSupport(ColumnViewer viewer, TColumn c) {
		super(viewer, c);
	}

	protected CellEditor createCellEditor() {
		return new JRPropertyExpressionCellEditor((Composite) viewer.getControl());
	}

	@Override
	protected void setValue(final Object element, Object value) {
		if (element instanceof JRPropertiesHolder) {
			final JRPropertiesHolder field = (JRPropertiesHolder) element;
			if (element instanceof JRDesignField && value instanceof PropertyExpressionDTO) {
				JRDesignField f = (JRDesignField) element;
				PropertyExpressionDTO dto = (PropertyExpressionDTO) value;
				if (dto.isExpression()) {
					if (dto.getValue() == null || dto.getValue().isEmpty())
						f.removePropertyExpression(c.getPropertyName());
					else {
						JRDesignPropertyExpression pe = new JRDesignPropertyExpression();
						pe.setName(c.getPropertyName());
						pe.setValueExpression(dto.getValueAsExpression());
						f.removePropertyExpression(c.getPropertyName());
						f.addPropertyExpression(pe);
					}
				} else {
					f.removePropertyExpression(c.getPropertyName());
					if (dto.getValue() == null || dto.getValue().isEmpty())
						field.getPropertiesMap().removeProperty(c.getPropertyName());
					else
						field.getPropertiesMap().setProperty(c.getPropertyName(), dto.getValue());
				}
			} else {
				if (value == null || value.toString().isEmpty())
					field.getPropertiesMap().removeProperty(c.getPropertyName());
				else
					field.getPropertiesMap().setProperty(c.getPropertyName(), value.toString());
			}
			viewer.update(element, null);
		}
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof JRPropertiesHolder) {
			JRPropertiesHolder field = (JRPropertiesHolder) element;
			boolean isExpression = false;
			String value = field.getPropertiesMap().getProperty(c.getPropertyName());
			boolean isSimpleText = false;
			if (element instanceof JRDesignField) {
				JRDesignField f = (JRDesignField) element;
				if (f.getPropertyExpressionsList() != null) {
					for (JRPropertyExpression pe : f.getPropertyExpressionsList()) {
						if (pe.getName().equals(c.getPropertyName()) && pe.getValueExpression() != null) {
							isExpression = true;
							isSimpleText = ExpressionTypeEnum.SIMPLE_TEXT == pe.getValueExpression().getType();
							value = pe.getValueExpression().getText();
						}
					}
				}
			}
			return new PropertyExpressionDTO(isExpression, c.getPropertyName(), value, isSimpleText);
		}
		return new PropertyExpressionDTO(false, c.getPropertyName(), "", false);
	}

}
