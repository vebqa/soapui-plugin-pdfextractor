package com.vebqa.readyapi.plugin.pdfextractor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.text.Document;

import com.eviware.soapui.support.DocumentListenerAdapter;
import com.eviware.soapui.support.components.JUndoableTextField;
import com.eviware.soapui.ui.support.ModelItemDesktopPanel;
import com.jgoodies.forms.builder.ButtonStackBuilder;

public class PDFExtractorTestStepDesktopPanel extends ModelItemDesktopPanel<PDFExtractorTestStep> {

	private static final long serialVersionUID = 287253772763520634L;
	
	private static final String LABEL_DOCUMENT_TAG = "Document Tag";
	private static final String LABEL_TARGET_FILE_NAME = "Target File name";
	
	
	private JUndoableTextField documentTagField;
	private JUndoableTextField targetFileNameField;

	public PDFExtractorTestStepDesktopPanel(PDFExtractorTestStep modelItem) {
		super(modelItem);
		buildUI();
	}

	private void buildUI() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		// documentTag
		builder.addFixed(new JLabel(LABEL_DOCUMENT_TAG));
		builder.addRelatedGap();
		documentTagField = new JUndoableTextField(getModelItem().getDocumentTag());
		documentTagField.getDocument().addDocumentListener(new DocumentListenerAdapter() {

			@Override
			public void update(Document document) {
				getModelItem().setDocumentTag(documentTagField.getText());
			}
		});

		// targetFile
		documentTagField.setPreferredSize(new Dimension(300, 20));
		builder.addFixed(documentTagField);

		builder.addFixed(new JLabel(LABEL_TARGET_FILE_NAME));
		builder.addRelatedGap();
		targetFileNameField = new JUndoableTextField(getModelItem().getTargetFileName());
		targetFileNameField.getDocument().addDocumentListener(new DocumentListenerAdapter() {

			@Override
			public void update(Document document) {
				getModelItem().setTargetFileName(targetFileNameField.getText());
			}
		});

		targetFileNameField.setPreferredSize(new Dimension(300, 20));
		builder.addFixed(targetFileNameField);

		
		builder.getPanel().setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		builder.getPanel().setPreferredSize(new Dimension(450, 180));
		add(builder.getPanel(), BorderLayout.NORTH);
	}
}
