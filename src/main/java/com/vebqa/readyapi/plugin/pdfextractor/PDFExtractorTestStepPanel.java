package com.vebqa.readyapi.plugin.pdfextractor;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.ui.support.ModelItemDesktopPanel;

public class PDFExtractorTestStepPanel extends ModelItemDesktopPanel<PDFExtractorTestStep> {

    /** serialVersionUID description. */
    private static final long serialVersionUID = 398715768048748118L;

    public PDFExtractorTestStepPanel(PDFExtractorTestStep modelItem) {
        super(modelItem);
        buildUI();
    }

    private void buildUI() {
    	SoapUI.log.debug("buildUI in PDFExtractorTestStepPanel");

    }
}
