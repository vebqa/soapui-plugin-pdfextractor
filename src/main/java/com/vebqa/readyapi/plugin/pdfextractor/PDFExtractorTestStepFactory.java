package com.vebqa.readyapi.plugin.pdfextractor;

import com.eviware.soapui.config.TestStepConfig;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.registry.WsdlTestStepFactory;

/**
 * Old Style TestStepFactory
 * 
 * @author Doerges
 *
 */
public class PDFExtractorTestStepFactory extends WsdlTestStepFactory {

	private static final String PDF_STEP_ID = "pdf";

	public PDFExtractorTestStepFactory() {
		super(PDF_STEP_ID, "PDF Extract", "Extacts a PDF document from xml tag", "com/vebqa/readyapi/plugin/pdfextractor/pdf.png");
	}

	@Override
	public WsdlTestStep buildTestStep(WsdlTestCase testCase, TestStepConfig config, boolean forLoadTest) {
		return new PDFExtractorTestStep(testCase, config, forLoadTest);
	}

	@Override
	public TestStepConfig createNewTestStep(WsdlTestCase testCase, String name) {
		TestStepConfig testStepConfig = TestStepConfig.Factory.newInstance();
		testStepConfig.setType(PDF_STEP_ID);
		testStepConfig.setName(name);
		return testStepConfig;
	}

	@Override
	public boolean canCreate() {
		return true;
	}
}
