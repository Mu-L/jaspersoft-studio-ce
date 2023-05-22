/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.jdbc;

import java.util.List;

/**
 * Clients that want to provide a list of JDBC driver definitions should
 * implement this interface.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public interface JDBCDriverDefinitionsContainer {

	/**
	 * @return the list of JDBC driver definitions
	 */
	List<JDBCDriverDefinition> getJDBCDriverDefinitions();
	
}
