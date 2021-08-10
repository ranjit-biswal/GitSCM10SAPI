package resources;

import java.io.File;

public class Filepaths {
	/*******************************************************
	 * >>>>>>>>>>>> GLOBAL FILE PATHS <<<<<<<<<<<*
	 *******************************************************/
	public static String globalProp = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
			+ File.separator + "resource" + File.separator + "resources" + File.separator + "global.properties";
	
	public static String envProp = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
			+ File.separator + "resource" + File.separator + "resources" + File.separator + "env.properties";

	/********************************************************
	 * >>>>>>>>>> EXTENT RELATED FILE PATHS <<<<<<<<<<<<<<<<<*
	 *******************************************************/
	public static String extentFilepath = System.getProperty("user.dir") + File.separator + "Report" + File.separator
			+ "ExtReport" + File.separator + "WebRegressionTestExecutionReport.html";
	public static String screenshotFilepath = System.getProperty("user.dir") + File.separator + "Report" + File.separator
			+ "Screenshot";

	/*********************************************************
	 * >>>>>>>>>>>>>>> JSON RELATED FILE PATHS <<<<<<<<<<<<<<<*
	 *********************************************************/
	// Scp test data filepath
	public static String testDataJsonFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "data" + File.separator + "testscp" + File.separator;
	// Scp test data new file path
	public static String testDataScpJsonFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "resources" + File.separator + "testDataJson"  + File.separator;
	// Scp text properties file path
	public static String textPropScpJsonFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "resources" + File.separator + "textscp"
			+ File.separator;
	// Csp text properties file path
	public static String textPropCspJsonFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "resources" + File.separator + "textcsp"
			+ File.separator;
	// Csp test properties file path
	public static String testDataCspJsonFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "data" + File.separator + "testcsp" + File.separator;
	// Json API related file paths.
	public static String testDataSCPJsonFilepath = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "testDataJson" + File.separator;
	public static String insertOutagePayloadFilepath = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "data" + File.separator + "api"
			+ File.separator + "outage" + File.separator;
	public static String connectMeProgramJson = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "testDataJson" + File.separator;
	public static String addOutageJsonFilepath = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "testDataJson"+ File.separator;

	/************************************************************
	 * >>>>>>>>>>>>>>>>>>>> OTHER FILE PATHS <<<<<<<<<<<<<<<<<<<<*
	 ************************************************************/
	// Download folder path
	public static String downloadFilepath = System.getProperty("user.dir") + File.separator + "Downloads" + File.separator;
	// Upload folder path
	public static String uploadFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "java" + File.separator + "data" + File.separator + "fileupload" + File.separator;
	// Mail content text file path
	public static String mailContentTxtFilepath = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "java" + File.separator + "data" + File.separator
			+ "mailcontent" + File.separator + "out.txt";
	// Config output properties file path
	public static String outputConfigFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "java" + File.separator + "resources" + File.separator
			+ "registeredUser.properties";
	public static String apiConfigFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "java" + File.separator + "resources" + File.separator
			+ "apiConfigFilePath.properties";
	public static String apiPaymentsFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "java" + File.separator + "resources" + File.separator + "Payments.xlsx";
	public static String notificationFilepath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "java" + File.separator + "resources" + File.separator + "Notification.xlsx";
}
