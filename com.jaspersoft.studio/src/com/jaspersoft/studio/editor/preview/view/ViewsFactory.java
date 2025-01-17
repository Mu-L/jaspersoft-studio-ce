/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.preview.view;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.editor.preview.actions.SwitchViewsAction;
import com.jaspersoft.studio.editor.preview.view.report.file.CSVMetadataViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.CSVViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.JSONMetadataViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.TXTViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.XMLImagesViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.XMLViewer;
import com.jaspersoft.studio.editor.preview.view.report.html.LayeredHTMLViewer;
import com.jaspersoft.studio.editor.preview.view.report.swt.SWTViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.AExporterFactory;
import com.jaspersoft.studio.editor.preview.view.report.system.DocxViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.OdsViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.OdtViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.PdfViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.PowerPointViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.RTFViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.XlsMetadataViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.XlsViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.XlsxViewer;
import com.jaspersoft.studio.messages.Messages;

public class ViewsFactory extends AViewsFactory {
	public static final String X_HTML = "xHTML"; //$NON-NLS-1$
	public static final String EXCEL_API = "ExcelAPI"; //$NON-NLS-1$
	public static final String XLS_METADATA = Messages.ViewsFactory_2;
	public static final String HTML = "HTML"; //$NON-NLS-1$
	public static final String HTML_NO_INTERACTIVITY = Messages.ViewsFactory_4; //$NON-NLS-1$
	public static final String VIEWER_JAVA = "Java"; //$NON-NLS-1$
	private static LinkedHashMap<String, Class<? extends APreview>> pcmap = new LinkedHashMap<String, Class<? extends APreview>>();
	static {
		pcmap.put(VIEWER_JAVA, SWTViewer.class);

		pcmap.put("SEPARATOR1", null); //$NON-NLS-1$

		pcmap.put(HTML, LayeredHTMLViewer.class); // $NON-NLS-1$
		pcmap.put("SEPARATOR1", null); //$NON-NLS-1$
		pcmap.put("SEPARATOR1", null); //$NON-NLS-1$
		pcmap.put("PDF", PdfViewer.class); //$NON-NLS-1$

		pcmap.put("SEPARATOR2", null); //$NON-NLS-1$

		pcmap.put("RTF", RTFViewer.class); //$NON-NLS-1$
		pcmap.put("DOCx", DocxViewer.class); //$NON-NLS-1$
		pcmap.put("ODT", OdtViewer.class); //$NON-NLS-1$
		pcmap.put("ODS", OdsViewer.class); //$NON-NLS-1$
		pcmap.put("PPTx", PowerPointViewer.class); //$NON-NLS-1$
		pcmap.put(Messages.ViewsFactory_5, TXTViewer.class);

		pcmap.put("SEPARATOR3", null); //$NON-NLS-1$

		pcmap.put("XLS", XlsViewer.class); //$NON-NLS-1$
		pcmap.put("XLSx", XlsxViewer.class); //$NON-NLS-1$
		pcmap.put("CSV", CSVViewer.class); //$NON-NLS-1$
		pcmap.put("XLS Metadata", XlsMetadataViewer.class); //$NON-NLS-1$
		pcmap.put(Messages.ViewsFactory_6, CSVMetadataViewer.class);
		pcmap.put(Messages.ViewsFactory_7, JSONMetadataViewer.class);

		pcmap.put("SEPARATOR4", null); //$NON-NLS-1$

		pcmap.put("XML", XMLViewer.class); //$NON-NLS-1$
		pcmap.put(Messages.ViewsFactory_8, XMLImagesViewer.class);

		// Load the contributed factories
		int separatorIndex = 5;
		List<AExporterFactory> factories = JaspersoftStudioPlugin.getExtensionManager().getExportersFactories();
		for (AExporterFactory factory : factories) {
			// Check that the name\key is unique
			if (!pcmap.containsKey(factory.getExporterName())) {
				// Check that the key\name and the class are not null
				if (factory.getExporterName() == null || factory.getViewerClass() == null) {
					String currentFactoryClass = pcmap.get(factory.getExporterName()).getName();
					String message = MessageFormat.format(Messages.ViewsFactory_errorExporterNull,
							new Object[] { currentFactoryClass });
					JaspersoftStudioPlugin.getInstance().logWarning(message);
					System.out.println(message);
				} else {
					if (factory.isSeparatorPlacedBefore()) {
						pcmap.put(SwitchViewsAction.SEPARATOR + separatorIndex, null);
						separatorIndex++;
					}
					pcmap.put(factory.getExporterName(), factory.getViewerClass());
				}
			} else {
				String currentFactoryClass = pcmap.get(factory.getExporterName()).getName();
				String message = MessageFormat.format(Messages.ViewsFactory_errorExporterDuplicated,
						new Object[] { factory.getExporterName(), currentFactoryClass });
				JaspersoftStudioPlugin.getInstance().logWarning(message);
				System.out.println(message);
			}
		}
	}

	/**
	 * Return the available keys for the preview area, may contains separator
	 */
	public Set<String> getKeys() {
		return pcmap.keySet();
	}

	@Override
	protected LinkedHashMap<String, Class<? extends APreview>> getMap() {
		return pcmap;
	}

}
