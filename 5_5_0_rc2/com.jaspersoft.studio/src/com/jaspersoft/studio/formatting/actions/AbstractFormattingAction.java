/*******************************************************************************
 * Copyright (C) 2010 - 2013 Jaspersoft Corporation. All rights reserved. http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.formatting.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import net.sf.jasperreports.engine.design.JRDesignElement;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.editor.action.IGlobalAction;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.model.MGraphicElement;
import com.jaspersoft.studio.model.band.MBand;

public abstract class AbstractFormattingAction extends SelectionAction implements IGlobalAction {

	public AbstractFormattingAction(IWorkbenchPart part) {
		super(part);
	}

	/**
	 * Return a new list of elements ordered by position X and Y
	 * 
	 * @param elements
	 * @return
	 */
	public static List<APropertyNode> sortXY(List<APropertyNode> elements) {
		return sortXY(elements, false);
	}

	protected List<APropertyNode> getOperationSet() {
		@SuppressWarnings("unchecked")
		List<?> editparts = new ArrayList<Object>(getSelectedObjects());
		if (editparts.isEmpty())
			return new ArrayList<APropertyNode>();
		List<APropertyNode> result = new ArrayList<APropertyNode>();
		for (Object element : editparts) {
			if (element instanceof EditPart) {
				EditPart part = (EditPart) element;
				if (part.getModel() instanceof MGraphicElement && !(part.getModel() instanceof MBand))
					result.add((MGraphicElement) ((EditPart) element).getModel());
			}
		}
		return result;
	}

	/**
	 * Return a new list of elements ordered by position X and Y
	 * 
	 * @param elements
	 * @return
	 */
	public static List<APropertyNode> sortXY(List<APropertyNode> elements, final boolean reverse) {
		APropertyNode[] elements_array = new APropertyNode[elements.size()];
		elements_array = elements.toArray(elements_array);

		Arrays.sort(elements_array, new Comparator<APropertyNode>() {

			public int compare(APropertyNode e1, APropertyNode e2) {

				JRDesignElement jre1 = (JRDesignElement) e1.getValue();
				JRDesignElement jre2 = (JRDesignElement) e2.getValue();

				int x1 = (reverse) ? jre1.getX() + jre1.getWidth() : jre1.getX();
				int x2 = (reverse) ? jre2.getX() + jre2.getWidth() : jre2.getX();
				int y1 = (reverse) ? jre1.getY() + jre1.getHeight() : jre1.getY();
				int y2 = (reverse) ? jre2.getY() + jre2.getHeight() : jre2.getY();

				int mul = (reverse) ? -1 : 1;

				if (x1 > x2)
					return 1 * mul;
				else if (x1 < x2)
					return -1 * mul;
				else if (y1 > y2)
					return 1 * mul;
				else if (y1 < y2)
					return -1 * mul;
				return 0;
			}
		});

		return Arrays.asList(elements_array);
	}

	/**
	 * Return a new list of elements ordered by position Y and X
	 * 
	 * @param elements
	 * @return
	 */
	public static List<APropertyNode> sortYX(List<APropertyNode> elements, final boolean reverse) {
		APropertyNode[] elements_array = new APropertyNode[elements.size()];
		elements_array = elements.toArray(elements_array);

		Arrays.sort(elements_array, new Comparator<APropertyNode>() {

			public int compare(APropertyNode e1, APropertyNode e2) {

				JRDesignElement jre1 = (JRDesignElement) e1.getValue();
				JRDesignElement jre2 = (JRDesignElement) e2.getValue();

				int x1 = (reverse) ? jre1.getX() + jre1.getWidth() : jre1.getX();
				int x2 = (reverse) ? jre2.getX() + jre2.getWidth() : jre2.getX();
				int y1 = (reverse) ? jre1.getY() + jre1.getHeight() : jre1.getY();
				int y2 = (reverse) ? jre2.getY() + jre2.getHeight() : jre2.getY();

				int mul = (reverse) ? -1 : 1;

				if (y1 > y2)
					return 1 * mul;
				else if (y1 < y2)
					return -1 * mul;
				else if (x1 > x2)
					return 1 * mul;
				else if (x1 < x2)
					return -1 * mul;

				return 0;
			}
		});

		return Arrays.asList(elements_array);
	}

	/**
	 * Return a new list of elements ordered by position Y and X
	 * 
	 * @param elements
	 * @return
	 */
	public static List<APropertyNode> sortYX(List<APropertyNode> elements) {
		return sortYX(elements, false);
	}

	@Override
	public void run() {
		execute(createAlignmentCommand());
	}

	protected abstract Command createAlignmentCommand();

	/**
	 * Convenient funtion to create the undo operation...
	 * 
	 * @param element
	 * @return
	 */
	public static Rectangle getElementBounds(JRDesignElement element) {
		return new Rectangle(element.getX(), element.getY(), element.getWidth(), element.getHeight());
	}

}
