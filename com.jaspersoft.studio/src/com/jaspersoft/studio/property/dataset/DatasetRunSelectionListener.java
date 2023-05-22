/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.dataset;

/**
 * A listener which is notified when the sub dataset selection changes.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public interface DatasetRunSelectionListener {
	
	/**
	 * Notifies that the selection has changed.
	 */
	void selectionChanged();
}
