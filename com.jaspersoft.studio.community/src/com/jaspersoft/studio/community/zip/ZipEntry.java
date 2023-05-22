/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.community.zip;

/**
 * Bean describing an entry that will be put in the final ZIP attached to the
 * new issue created.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class ZipEntry {

	private String name;
	private String location;
	private ZipEntryType type;

	public ZipEntry() {
	}
	
	public ZipEntry(String name, String location, ZipEntryType type) {
		super();
		this.name = name;
		this.location = location;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ZipEntryType getType() {
		return type;
	}

	public void setType(ZipEntryType type) {
		this.type = type;
	}

}
