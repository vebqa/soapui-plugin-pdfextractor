package com.vebqa.readyapi.plugin.pdfextractor;

import com.eviware.soapui.plugins.PluginAdapter;
import com.eviware.soapui.plugins.PluginConfiguration;
import com.eviware.soapui.support.UISupport;

/**
 * Plugin Configuration
 * @author Doerges
 *
 */
@PluginConfiguration(groupId = "com.veb.readyapi.plugin.pdfextractor", name = "PDFExtractor Plugin",
        version = "1.0.0", autoDetect = true, description = "Adds pdf extractor TestSteps to SoapUI NG", infoUrl = "")
public class PluginConfig extends PluginAdapter {

    public static final String DEFAULT_PDF_TAGNAME = "PDFDocument";
    public static final String LOGGER_NAME = "PDFExtractor Plugin";

    /**
     * Standard Constructor
     */
    public PluginConfig() {
        super();
        UISupport.addResourceClassLoader(getClass().getClassLoader());
    }
}
