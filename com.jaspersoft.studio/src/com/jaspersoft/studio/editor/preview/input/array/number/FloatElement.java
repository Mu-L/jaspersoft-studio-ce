/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.input.array.number;

import java.util.Locale;

import org.apache.commons.validator.routines.FloatValidator;

public class FloatElement extends ANumberElement {

	@Override
	public Class<?> getSupportedType() {
		return Float.class;
	}

	protected boolean isValid(String number) {
		return FloatValidator.getInstance().isValid(number, Locale.US);
	}

	@Override
	protected Object convertString(String str) {
		return new Float(str);
	}
}
