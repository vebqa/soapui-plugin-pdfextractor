/**
 * PDFExtractor Plugin fuer SoapUI
 */
package com.vebqa.readyapi.plugin.pdfextractor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.config.TestStepConfig;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepWithProperties;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansion;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContainer;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionUtils;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestStepResult.TestStepStatus;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.xml.XmlObjectConfigurationBuilder;
import com.eviware.soapui.support.xml.XmlObjectConfigurationReader;

/**
 * Custom TestStep that extracts pdf data from tag value and save this as a file to disk.
 * 
 * @author Doerges
 *
 */
public class PDFExtractorTestStep extends WsdlTestStepWithProperties implements PropertyExpansionContainer {

	private String documentTag;

	private String targetFileName;

	protected PDFExtractorTestStep(WsdlTestCase testCase, TestStepConfig config, boolean forLoadTest) {
		super(testCase, config, true, forLoadTest);

		if (!forLoadTest) {
			setIcon(UISupport.createImageIcon("com/vebqa/readyapi/plugin/pdfextractor/pdf.png"));
		}

		if (config.getClass() != null) {
			readConfig(config);
		} else {
			SoapUI.log.debug("No need to read config.");
		}
	}

	@Override
	public TestStepResult run(TestCaseRunner runner, TestCaseRunContext context) {
		WsdlTestStepResult result = new WsdlTestStepResult(this);
		result.startTimer();

		String teststep = getTestCase().getTestStepAt(context.getCurrentStepIndex() - 1).getLabel();
		String dataResponse = getTestCase().getTestStepAt(context.getCurrentStepIndex() - 1)
				.getPropertyValue("Response");
		
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		} catch (ParserConfigurationException e1) {
			SoapUI.log.error("Cannot set parser configuration: " + e1.getMessage());
		}
		factory.setNamespaceAware(false);
		DocumentBuilder builder = null;
		Document document = null;
		String data = null;
		int foundTags = 0;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(dataResponse)));
			NodeList dataTag = document.getElementsByTagName(documentTag.trim());
			foundTags = dataTag.getLength();
			
			if (foundTags == 1) {
				Node dataNode = dataTag.item(0);
				if (dataNode != null) {
					data = dataNode.getTextContent();
				}
			}
		} catch (ParserConfigurationException e) {
			SoapUI.log.error("error during xml processing" + e.getMessage());
		} catch (SAXException e) {
			SoapUI.log.error("error during xml processing" + e.getMessage());
		} catch (IOException e) {
			SoapUI.log.error("error during xml processing" + e.getMessage());
		}

		if (data != null && foundTags == 1) {
			SoapUI.log.debug("data-result: " + data.substring(0, 30) + "[tbc; length: " + data.length() + "]");
			SoapUI.log.debug("Found Tags: " + foundTags);

			byte[] tDataBytes = Base64.getDecoder().decode(data);

			String outputFileName;

			// Wenn komplett definiert dann das nehmen
			if (targetFileName.endsWith(".pdf")) {
				outputFileName = targetFileName;
			} else {
				// ansonsten zusammenbauen
				// Zeichen ersetzen welche wir im Dateinamen nicht haben wollen:
				// ":"," "
				String tsname = teststep.replaceAll(" ", "_");
				tsname = tsname.replaceAll(":", "_");
				outputFileName = tsname + ".pdf";
			}
			
			try (OutputStream tOut = Files.newOutputStream(new File(outputFileName).toPath())) {
				tOut.write(tDataBytes);
				result.addMessage("pdf-data: written to file (" + outputFileName + ")");
				result.setStatus(TestStepStatus.OK);
			} catch (IOException e) {
				SoapUI.log.error("Cannot write data: " + e.getMessage());
				result.addMessage("Cannot write data: " + e.getMessage());
				result.setStatus(TestStepStatus.FAILED);
			}
		} else {
			result.addMessage("No PDFDokument Tag found. No data written.");
			result.setStatus(TestStepStatus.FAILED);
		}
		result.stopTimer();
		return result;
	}

	public String getDocumentTag() {
		return documentTag;
	}

	public void setDocumentTag(String aTag) {
		String tempOld = documentTag;
		documentTag = aTag;
		updateConfig();
		notifyPropertyChanged("documentTag", tempOld, aTag);
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public void setTargetFileName(String aFileName) {
		String tempOld = targetFileName;
		targetFileName = aFileName;
		updateConfig();
		notifyPropertyChanged("tagetFileName", tempOld, aFileName);
	}

	public WsdlTestStep buildTestStep(WsdlTestCase aTestCase, TestStepConfig aConfig, Boolean forLoadTest) {

		return new PDFExtractorTestStep(aTestCase, aConfig, forLoadTest);
	}

	/**
	 * Properties
	 */
	public PropertyExpansion[] getPropertyExpansions() {
		List<PropertyExpansion> result = new ArrayList<PropertyExpansion>();
		result.addAll(PropertyExpansionUtils.extractPropertyExpansions(this, this, "documentTag"));
		result.addAll(PropertyExpansionUtils.extractPropertyExpansions(this, this, "targetFileName"));
		return result.toArray(new PropertyExpansion[result.size()]);
	}

	private void readConfig(TestStepConfig config) {
		XmlObjectConfigurationReader reader = new XmlObjectConfigurationReader(config.getConfig());
		documentTag = reader.readString("documentTag", "");
		SoapUI.log.debug("read-config: documentTag=" + documentTag);

		targetFileName = reader.readString("targetFileName", "");
		SoapUI.log.debug("read-config: targetFileName=" + targetFileName);
	}

	/**
	 * Update Config
	 */
	private void updateConfig() {
		XmlObjectConfigurationBuilder builder = new XmlObjectConfigurationBuilder();
		SoapUI.log.debug("update-config: documentTag=" + documentTag);
		builder.add("documentTag", documentTag);
		SoapUI.log.debug("update-config: targetFileName=" + targetFileName);
		builder.add("targetFileName", targetFileName);
		getConfig().setConfig(builder.finish());
	}
}
