/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.publish.imp.da;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;

public class ImpMongoDbDataAdapter extends AImpJdbcDataAdapter {
	public static final String DNAME = "tibcosoftware.jdbc.mongodb.MongoDBDriver";

	public ImpMongoDbDataAdapter() {
		super(DNAME, new String[][] { { "SchemaDefinition", ResourceDescriptor.TYPE_CONTENT_RESOURCE } });
	}

	@Override
	protected String getJdbcPrefix() {
		return "jdbc:tibcosoftware:mongodb:";
	}

}
