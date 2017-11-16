package com.vebqa.readyapi.plugin.pdfextractor;

import com.eviware.soapui.impl.EmptyPanelBuilder;
import com.eviware.soapui.plugins.auto.PluginPanelBuilder;
import com.eviware.soapui.ui.desktop.DesktopPanel;

@PluginPanelBuilder(targetModelItem = PDFExtractorTestStep.class)
public class PDFExtractorTestStepPanelBuilder extends EmptyPanelBuilder<PDFExtractorTestStep> {

    @Override
    public DesktopPanel buildDesktopPanel(PDFExtractorTestStep testStep) {
        return new PDFExtractorTestStepDesktopPanel(testStep);
    }

    @Override
    public boolean hasDesktopPanel() {
        return true;
    }
}
