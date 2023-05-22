/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.secret.keystore;

import com.jaspersoft.studio.JaspersoftStudioPlugin;

import net.sf.jasperreports.eclipse.secret.EclipseSecretsProvider;
import net.sf.jasperreports.util.SecretsProvider;

/**
 * {@link SecretsProvider} for the JasperReports Server sensitive information.
 * 
 * @author veaceslav chicu (chicuslavic@users.sourceforge.net)
 *
 */
public class JRKeyStoreSecretsProvider extends EclipseSecretsProvider {
	
	public static final String SECRET_NODE_ID = "com.jaspersoft.studio.keystore";
	
	@Override
	public boolean hasSecret(String key) {
		// Ensure back-compatibility
		return true;
	}
	
	@Override
	public String getSecretNodeId() {
		return SECRET_NODE_ID;
	}
	
	@Override
	public String getSecret(String key) {
		return JaspersoftStudioPlugin.shouldUseSecureStorage() ? super.getSecret(key) : key;
	}
}
