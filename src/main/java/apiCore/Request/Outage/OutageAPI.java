package apiCore.Request.Outage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import resources.Filepaths;
import utils.RestUtils;

import static io.restassured.RestAssured.given;

public class OutageAPI {
	String addOutageJsonFilepath = Filepaths.addOutageJsonFilepath;
	String outPayloadFile = addOutageJsonFilepath + "outagePayLoad.json";
	
	/**
	 * Write outage payload in JSON to create new outage.
	 * 
	 * @param outageCause
	 * @param startTime
	 * @param endTime
	 * @param outageMsg
	 * @param outReportInfo
	 * @param isResolved
	 */
	public void writeOutagePayloadJson(String outReportInfo, String outageCause, String outageMsg,
									   String startTime, String endTime, String isResolved,
									   String serviceType, int isPlanned) {
		//Creating a JSONObject object
		JSONObject jsonObject = new JSONObject();
		// Put type parameter
		jsonObject.put("Type", "0");
		// Put cause of outage.
		jsonObject.put("Cause", outageCause);
		// Put Start Time
		jsonObject.put("StartTime", startTime);
		// Put circuit
		jsonObject.put("Circuit", "");
		// Put end time
		jsonObject.put("EndTime", endTime);
		// Put outage longitude
		jsonObject.put("OutageLongitude", "33.744681");
		// Put outage latitude
		jsonObject.put("OutageLatitude", "-117.755101");
		// Put outage zipcode
		jsonObject.put("Zipcode", "92604");
		// Put outage message
		jsonObject.put("OutageMessage", outageMsg);
		// Put outage report info
		jsonObject.put("OutageReportInfo", outReportInfo);
		// Put outage xml data
		jsonObject.put("xmldata", "{'Outage':[{\"Area\":[{\"y\":-117.89541727466829,\"x\":33.72723526491251,\"zipcode\":\"92602\"},{\"y\":-117.69114023609431,\"x\":33.811142421664705,\"zipcode\":\"92602\"},{\"y\":-117.64616495533268,\"x\":33.706674052404566,\"zipcode\":\"92602\"},{\"y\":-117.84597879810585,\"x\":33.65696413248896,\"zipcode\":\"92602\"},{\"y\":-117.89541727466829,\"x\":33.72723526491251,\"zipcode\":\"92602\"}]},{\"points\":[{\"y\":-117.80443674488325,\"x\":33.70638844751949,\"zipcode\":\"92602\"}]}]}");
		// Put offset
		jsonObject.put("offset", "-330");
		// Put outage id as blank
		jsonObject.put("OutageId", "");
		// Put outage latitude
		jsonObject.put("IsResolved", isResolved);
		// Put outage city
		jsonObject.put("City", "1");
		// Put service type ID
		jsonObject.put("ServiceTypeID", serviceType);
		// Put is planned flag
		jsonObject.put("IsPalnned", isPlanned);
		try {
			FileWriter file = new FileWriter(outPayloadFile);
			file.write(jsonObject.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write outage payload in JSON to update the existing outage.
	 * 
	 * @param outageId
	 * @param outageCause
	 * @param startTime
	 * @param endTime
	 * @param outageMsg
	 * @param outageReportInfo
	 * @param isResolved
	 */
	public void writeOutagePayloadJson(String outageId, String outageReportInfo, String outageCause,
										String outageMsg, String startTime, String endTime, String isResolved,
									   String serviceType, int isPlanned) {
		//Creating a JSONObject object
		JSONObject jsonObject = new JSONObject();
		// Put type parameter
		jsonObject.put("Type", "0");
		// Put outage id if you have to update existing outage
		jsonObject.put("OutageId", outageId);
		// Put cause of outage.
		jsonObject.put("Cause", outageCause);
		// Put Start Time
		jsonObject.put("StartTime", startTime);
		// Put circuit
		jsonObject.put("Circuit", "");
		// Put end time
		jsonObject.put("EndTime", endTime);
		// Put outage longitude
		jsonObject.put("OutageLongitude", "33.744681");
		// Put outage latitude
		jsonObject.put("OutageLatitude", "-117.755101");
		// Put outage zipcode
		jsonObject.put("Zipcode", "92602");
		// Put outage message
		jsonObject.put("OutageMessage", outageMsg);
		// Put outage report info
		jsonObject.put("OutageReportInfo", outageReportInfo);
		// Put outage xml data
		jsonObject.put("xmldata", "{'Outage':[{\"Area\":[{\"y\":-117.89541727466829,\"x\":33.72723526491251,\"zipcode\":\"92602\"},{\"y\":-117.69114023609431,\"x\":33.811142421664705,\"zipcode\":\"92602\"},{\"y\":-117.64616495533268,\"x\":33.706674052404566,\"zipcode\":\"92602\"},{\"y\":-117.84597879810585,\"x\":33.65696413248896,\"zipcode\":\"92602\"},{\"y\":-117.89541727466829,\"x\":33.72723526491251,\"zipcode\":\"92602\"}]},{\"points\":[{\"y\":-117.80443674488325,\"x\":33.70638844751949,\"zipcode\":\"92602\"}]}]}");
		// Put offset
		jsonObject.put("offset", "-330");
		// Put outage latitude
		jsonObject.put("IsResolved", isResolved);
		// Put outage city
		jsonObject.put("City", "1");
		// Put service type ID
		jsonObject.put("ServiceTypeID", serviceType);
		// Put service type ID
		jsonObject.put("IsPalnned", isPlanned);
		try {
			FileWriter file = new FileWriter(outPayloadFile);
			file.write(jsonObject.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RequestSpecification generateAddOutageRequest(Map<String, String> reqHeader,
														 Object payLoad) {
		return given().spec(RestUtils.requestSpecWithHeaderAndBody(reqHeader, payLoad));
	}

	public RequestSpecification generateUpdateOutageRequest(Map<String, String> reqHeader,
															Object payLoad) {
		return given().spec(RestUtils.requestSpecWithHeaderAndBody(reqHeader, payLoad));
	}
}
