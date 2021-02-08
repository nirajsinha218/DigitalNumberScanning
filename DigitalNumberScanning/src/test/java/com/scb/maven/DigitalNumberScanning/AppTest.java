package com.scb.maven.DigitalNumberScanning;

import java.util.List;
import java.util.stream.BaseStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseScannerServiceIntegrationTest {

	@Override
	protected List<String> performScanning(String inputFilePath) {
		// TODO Auto-generated method stub
		List<String> resultList = App.fileParser(inputFilePath);
		return resultList;
	}

}
