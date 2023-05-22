/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.utils;

public class RDUtil {

	public static String getID(String url) {
		return url.substring(url.lastIndexOf('/') + 1);
	}

	public static String getParentFolder(String url) {
		return url.substring(0, url.lastIndexOf('/'));
	}
}
