package apiCore.Request.ConnectMe;
import resources.Filepaths;

import utils.RestUtils;

public class ConnectMePayLoad {
	static String sSetConnectMeProgram = Filepaths.connectMeProgramJson + "ProgramPayload.json";

	/**
	 * This Method is used to Create Connect Me program payload
	 * 
	 * @author Souradeep.Ghosh
	 * @return
	 */
	public static String createConnectMeProgramPayload() {
		String body = null;
		try {
			body = RestUtils.generateStringFromResource(sSetConnectMeProgram);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}
}
