package com.vebqa.readyapi.plugin.pdfextractor;

import com.eviware.soapui.impl.EmptyPanelBuilder;
import com.eviware.soapui.model.PanelBuilder;
import com.eviware.soapui.model.util.PanelBuilderFactory;
import com.eviware.soapui.ui.desktop.DesktopPanel;

public class PDFExtractorTestStepPanelBuilderFactory implements PanelBuilderFactory<PDFExtractorTestStep> {
	@Override
	public PanelBuilder<PDFExtractorTestStep> createPanelBuilder() {
		return new PDFExtractorTestStepPanelBuilder();
	}

	@Override
	public Class<PDFExtractorTestStep> getTargetModelItem() {
		return PDFExtractorTestStep.class;
	}

	public static class EMailTestStepPanelBuilder extends EmptyPanelBuilder<PDFExtractorTestStep> {
		@Override
		public DesktopPanel buildDesktopPanel(PDFExtractorTestStep modelItem) {
			return new PDFExtractorTestStepDesktopPanel(modelItem);
		}

		@Override
		public boolean hasDesktopPanel() {
			return true;
		}
	}
}
