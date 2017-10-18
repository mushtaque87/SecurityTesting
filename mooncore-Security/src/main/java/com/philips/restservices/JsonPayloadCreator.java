package com.philips.restservices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;




public class JsonPayloadCreator {


	public static DateTime createTimeStamp(String time, String offset) {
		int actual_offset = Double.valueOf(offset).intValue() * -1;

		DateTime dateTime_m = DateTime.parse(time).withZone(DateTimeZone.forID("UTC"));
		DateTime new_time = dateTime_m.plusMinutes(actual_offset);

		return new_time;
	}

	public static DateTime createTimeStamp(String time) {
		DateTime newTime = DateTime.parse(time);
		return newTime;
	}

	public static String createMdsObservationJsonPayload(String Observation_Type, String value, String duration, String time, String offset) {
		DateTime time1 = createTimeStamp(time, offset);

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:SS'Z'");
		DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		String dateStr;
		if(Observation_Type.contains("inactiveMinutes")|| Observation_Type.contains("activeMinutes")) {
			dateStr = fmt1.withZone(DateTimeZone.UTC).print(time1);
		} else dateStr = fmt.withZone(DateTimeZone.UTC).print(time1);

		JSONObject type = new JSONObject();
		JSONObject observation = new JSONObject();
		JSONObject date = new JSONObject();
		JSONObject data = new JSONObject();
		if (value.equals("bad") || value.equals("good")) 
			data.put("v", value);
		else  {
			data.put("v", Double.valueOf(value).intValue());
			data.put("d", Double.valueOf(duration).intValue());
		}

		date.put(dateStr, data);
		observation.put(Observation_Type, date);
		type.put("manual", observation);
		System.out.println(type.toString());
		return(type.toString());      

	}

	public static String createPublishJsonPayload(String User_Id, String time, String Offset) {
		DateTime time1 = createTimeStamp(time, Offset);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String localDateTime = fmt.withZone(DateTimeZone.UTC).print(time1);
		String dateTime = fmt2.withZone(DateTimeZone.UTC).print(time1);
		String date = fmt1.withZone(DateTimeZone.UTC).print(time1);

		JSONObject obj = new JSONObject();
		obj.put("userId", User_Id);
		obj.put("localDateTime", localDateTime);
		obj.put("dateTime", dateTime);
		obj.put("date", date);
		System.out.println(obj.toString());
		return(obj.toString());    



	}

	public static String createTargetReachedJsonPayload(String User_Id, String time, String Observation, String Offset) {
		DateTime time1 = createTimeStamp(time, Offset);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String localDateTime = fmt.withZone(DateTimeZone.UTC).print(time1);
		String dateTime = fmt2.withZone(DateTimeZone.UTC).print(time1);
		String date = fmt1.withZone(DateTimeZone.UTC).print(time1);

		JSONObject obj = new JSONObject();
		obj.put("userId", User_Id);
		obj.put("localDateTime", localDateTime);
		obj.put("dateTime", dateTime);
		obj.put("date", date);
		obj.put("observation", Observation);
		System.out.println(obj.toString());
		return(obj.toString());    



	}

	public static String createTargetReachedJsonPayload(String User_Id, String time, String Observation, String Offset, String milestone) {
		DateTime time1 = createTimeStamp(time, Offset);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String localDateTime = fmt.withZone(DateTimeZone.UTC).print(time1);
		String dateTime = fmt2.withZone(DateTimeZone.UTC).print(time1);
		String date = fmt1.withZone(DateTimeZone.UTC).print(time1);

		JSONObject obj = new JSONObject();
		obj.put("userId", User_Id);
		obj.put("localDateTime", localDateTime);
		obj.put("dateTime", dateTime);
		obj.put("date", date);
		obj.put("observation", Observation);
		obj.put("milestone", milestone);
		System.out.println(obj.toString());
		return(obj.toString());    



	}

	public static String createMilestoneReachedJsonPayload(String User_Id, String time, String Milestone, String Offset) {
		DateTime time1 = createTimeStamp(time, Offset);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String localDateTime = fmt.withZone(DateTimeZone.UTC).print(time1);
		String dateTime = fmt2.withZone(DateTimeZone.UTC).print(time1);
		String date = fmt1.withZone(DateTimeZone.UTC).print(time1);

		JSONObject obj = new JSONObject();
		obj.put("userId", User_Id);
		obj.put("localDateTime", localDateTime);
		obj.put("dateTime", dateTime);
		obj.put("date", date);
		obj.put("milestone", Milestone);
		//        System.out.println(obj.toString());
		return(obj.toString());    



	}

    public static String createTargetJsonPayload(String Observation_Type, String value,  String startDate) {
    	JSONObject target = new JSONObject();
		JSONObject obj = new JSONObject();
		obj.put("value", Double.valueOf(value).intValue());
		obj.put("startDate", startDate);
		target.put(Observation_Type, obj);
		System.out.println(target.toString());
		return(target.toString());    	
	}

	public static String createWeightGoalJsonPayload(String Observation_Type, String value, String date, String startDate) {
		JSONObject target = new JSONObject();
		JSONObject obj = new JSONObject();
		obj.put("value", Double.valueOf(value).intValue());
		obj.put("startDate", startDate);
		obj.put("date", date);
		target.put(Observation_Type, obj);
		return(target.toString());    	
	}

	public static String createLoginJsonPayload(String username, String password) {
		JSONObject login = new JSONObject();
		login.put("username", username);
		login.put("password", password);
		System.out.println(login.toString());
		return(login.toString());    	
	}

	public static String createMetricPayload(String value, String time) {
		// TODO Auto-generated method stub
		JSONObject payload = new JSONObject();
		payload.put("value", Double.valueOf(value));
		payload.put("measuredAt", time);
		System.out.println(payload.toString());
		return(payload.toString());
	}

	public static String createJsonFromArrayList(ArrayList<String> arrlist) {
		JSONArray services = new JSONArray();
		for(int i =0 ; i< arrlist.size(); i++) {
			services.put(arrlist.get(i));
		}
		return(services.toString());
	}


	public static String createguidelinceComplaiancePayload( String fruitsAndVeggies, String fat, String grain, String salt,String sugar,String protein,String glassesOfWater) {


		JSONObject obj = new JSONObject();
		obj.put("fruitsAndVeggies", Boolean.valueOf(fruitsAndVeggies));
		obj.put("fat",  Boolean.valueOf(fat));
		obj.put("grain",  Boolean.valueOf(grain));
		obj.put("salt",  Boolean.valueOf(salt));
		obj.put("sugar",  Boolean.valueOf(sugar));
		obj.put("protein",  Boolean.valueOf(protein));
		obj.put("glassesOfWater",  Boolean.valueOf(glassesOfWater));

		System.out.println(obj.toString());
		return(obj.toString());    



	}
	public static String createComplianceTargetJsonPayload(String complianceType, String value,String startDate,String autoAdjust) {
		JSONObject compliancetarget = new JSONObject();
		JSONObject obj = new JSONObject();
		obj.put("value", Double.valueOf(value).intValue());
		obj.put("startDate", startDate);
		obj.put("autoAdjust",Boolean.valueOf(autoAdjust));
		compliancetarget.put(complianceType, obj);
		return(compliancetarget.toString());    	
	}

	public static String createMdsBloodPressureJsonPayload(String Observation_Type,String time,String Systolic,String diastolic,String offset) {
		DateTime time1 = createTimeStamp(time, offset);

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:SS'Z'");

		String dateStr;
		dateStr = fmt.withZone(DateTimeZone.UTC).print(time1);

		JSONObject type = new JSONObject();
		JSONObject observation = new JSONObject();
		JSONObject v=new JSONObject();
		v.put("systolic", Double.valueOf(Systolic).intValue());
		v.put("diastolic",  Double.valueOf(diastolic).intValue());
		JSONObject vMap=new JSONObject();
		vMap.put("v",v);
		JSONObject date = new JSONObject();
		date.put(dateStr, vMap);
		observation.put(Observation_Type, date);
		type.put("manual", observation);
		System.out.println(type.toString());
		return(type.toString());      

	}

	public static String GetTargetPayload(String Observation, String startDate, String endDate) {
		JSONObject dateRange = new JSONObject();
		JSONObject payload = new JSONObject();
		dateRange.put("startDate", startDate);
		dateRange.put("endDate", endDate);
		payload.put(Observation, dateRange);
		return(payload.toString());
	}

	public static String createHHASJsonPayload(HashMap<String, String> question_ids, String id, String AQ4_answer) {

		JSONObject AQ1 = new JSONObject();
		JSONObject AQ2 = new JSONObject();
		JSONObject AQ3 = new JSONObject();
		JSONObject AQ4 = new JSONObject();
		JSONObject AQ5 = new JSONObject();
		JSONObject AQ6 = new JSONObject();
		JSONObject AQ7= new JSONObject();
		JSONObject AQ8= new JSONObject();
		JSONObject AQ9= new JSONObject();
		JSONObject AQ10= new JSONObject();
		JSONObject AQ11= new JSONObject();

		JSONArray assessmentResults = new JSONArray();
		JSONObject payload = new JSONObject();

		JSONObject att_AQ3 = new JSONObject();
		JSONObject att_AQ4 = new JSONObject();
		JSONObject att_AQ5 = new JSONObject();
		JSONObject att_AQ7 = new JSONObject();

		payload.put("id", id);
		att_AQ3.put("value", 10);

		att_AQ4.put("diastolic", 80);
		att_AQ4.put("unit", "mmHg");
		att_AQ4.put("systolic", 120);

		att_AQ5.put("value", 5.6);
		att_AQ5.put("unit", "mg/dL");

		att_AQ7.put("value", 2);
		att_AQ7.put("unit", "mmol/L");

		AQ1.put("questionId", question_ids.get("questionId1"));
		AQ1.put("answerId", AQ4_answer);

		AQ2.put("questionId", question_ids.get("questionId2"));
		AQ2.put("answerId", "fairlyOften");

		AQ3.put("questionId", question_ids.get("questionId3"));
		AQ3.put("answerId", "number");
		AQ3.put("attributes", att_AQ3);

		AQ4.put("questionId", question_ids.get("questionId4"));
		AQ4.put("answerId", "bloodPressure");
		AQ4.put("attributes", att_AQ4);


		AQ5.put("questionId", question_ids.get("questionId5"));
		AQ5.put("answerId", "cholesterol");
		AQ5.put("attributes", att_AQ5);

		AQ6.put("questionId", question_ids.get("questionId6"));
		AQ6.put("answerId", "unsure");


		AQ7.put("questionId", question_ids.get("questionId7"));
		AQ7.put("answerId", "hdl");
		AQ7.put("attributes", att_AQ7);

		AQ8.put("questionId", question_ids.get("questionId8"));
		AQ8.put("answerId", "yes");


		AQ9.put("questionId", question_ids.get("questionId9"));
		AQ9.put("answerId", "no");		


		AQ10.put("questionId", question_ids.get("questionId10"));
		AQ10.put("answerId", "no");

		AQ11.put("questionId", question_ids.get("questionId11"));
		AQ11.put("answerId", "unsure");


		assessmentResults.put(AQ1);
		assessmentResults.put(AQ2);
		assessmentResults.put(AQ3);
		assessmentResults.put(AQ4);
		assessmentResults.put(AQ5);
		assessmentResults.put(AQ6);
		assessmentResults.put(AQ7);
		assessmentResults.put(AQ8);
		assessmentResults.put(AQ9);
		assessmentResults.put(AQ10);
		assessmentResults.put(AQ11);


		payload.put("questionnaireResponse", assessmentResults);
		return(payload.toString());
	}

	public static String createMcpsPostPayload(String time, String card_id , String deliveryMechanism) {
		
//		DateTime time1 = createTimeStamp(time, "0");
//		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//		String dateTime = fmt.withZone(DateTimeZone.UTC).print(time1);	
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String createdDateTime = sdf.format(cal.getTime());
        System.out.println(createdDateTime);
		
		JSONObject payload = new JSONObject();
		payload.put("templateName", card_id);
		payload.put("userZoneCreatedDateTime", createdDateTime);
		payload.put("createdTimestamp", time);
		payload.put("deliveryMechanism", deliveryMechanism);
		return(payload.toString());
	}

//	{
//		  "INSPIRATION": { "startDate": “%s”, "endDate": “%s” },
//		  "EDUCATION": { "startDate":  "%s”, "endDate": "%s" },
//		  "FEEDBACK": { "startDate": "%s", "endDate": "%s"  },
//		  "FEEDBACK.WEEKLY": { "startDate":  "%s", "endDate": "%s" },
//		  "INSIGHT": { "startDate":  "%s", "endDate": "%s" },
//		  "INSIGHT.WEEKLY": { "startDate": "%s",   "endDate": "%s" },
//		  "VCDIALOG": { "startDate": "%s",   "endDate": "%s" },
//		  "INTRO": { "startDate": "%s",   "endDate": "%s" },
//		  "OUTRO": { "startDate": "%s",   "endDate": "%s" }
//		} 
//		  
		  
	public static String createMcpsPutPayload(String mess, String resp) {
		JSONObject payload = new JSONObject();
		JSONObject response = new JSONObject();
		JSONArray userdia = new JSONArray();

		response.put("messageId", mess);
		response.put("visualId", resp);
		userdia.put(response);
		payload.put("isCompleted", true);
		payload.put("userDialogs", userdia);
		return(payload.toString());

	}

	public static String createNewServiceJsonPayload(String userId, String time, String service, String Offset) {
		DateTime time1 = createTimeStamp(time, Offset);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String localDateTime = fmt.withZone(DateTimeZone.UTC).print(time1);
		String dateTime = fmt2.withZone(DateTimeZone.UTC).print(time1);
		String date = fmt1.withZone(DateTimeZone.UTC).print(time1);

		JSONObject obj = new JSONObject();
		obj.put("userId", userId);
		obj.put("localDateTime", localDateTime);
		obj.put("dateTime", dateTime);
		obj.put("date", date);
		obj.put("service", service);
		System.out.println(obj.toString());
		return(obj.toString());   
	}

	public static String createGuidelinePayload(String userId, String time, String Offset) {
		DateTime time1 = createTimeStamp(time, Offset);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String localDateTime = fmt.withZone(DateTimeZone.UTC).print(time1);
		String dateTime = fmt2.withZone(DateTimeZone.UTC).print(time1);
		String date = fmt1.withZone(DateTimeZone.UTC).print(time1);

		JSONArray list = new JSONArray();
		list.put("fruitsAndVeggies");
		list.put("fat");
		list.put("grain");
		list.put("salt");
		list.put("sugar");
		list.put("protein");

		JSONObject obj = new JSONObject();
		obj.put("userId", userId);
		obj.put("localDateTime", localDateTime);
		obj.put("dateTime", dateTime);
		obj.put("date", date);
		obj.put("compliantList", list);
		System.out.println(obj.toString());
		return(obj.toString());  
	}

	public static String createJsonPayloadFromMap(HashMap<String, String> map){
		JSONObject accountMap = new JSONObject();
		map.forEach((key, value) -> accountMap.put(key, value)); 
		return accountMap.toString();
	}

	public static String createCalorieIntakePayload(String observationType, String value, String category, String time, String offset){
		DateTime newTime = createTimeStamp(time, offset);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:SS'Z'");
		String localDateTime = fmt.withZone(DateTimeZone.UTC).print(newTime);

		JSONObject data = new JSONObject();
		data.put("value", Integer.parseInt(value));
		data.put("category", category);

		JSONObject v = new JSONObject();
		v.put("v", data);

		JSONObject dayTime = new JSONObject();
		dayTime.put(localDateTime, v);

		JSONObject observation = new JSONObject();
		observation.put(observationType, dayTime);

		JSONObject payload = new JSONObject();
		payload.put("manual", observation);
		return (payload.toString());
	}

	public static String createSleepRatingPayload(String observationType, String value, String time){
		DateTime newTime = createTimeStamp(time, Integer.toString(0));
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:SS'Z'");
		String localDateTime = fmt.withZone(DateTimeZone.UTC).print(newTime);

		JSONObject data = new JSONObject();
		data.put("v", value);

		JSONObject dayTime = new JSONObject();
		dayTime.put(localDateTime, data);

		JSONObject observation = new JSONObject();
		observation.put(observationType, dayTime);

		JSONObject payload = new JSONObject();
		payload.put("manual", observation);
		return (payload.toString());
	}

	public static String createRequirementMetPayload(String userId, String time, String requirementMetCategory){
		DateTime time1 = createTimeStamp(time, Integer.toString(0));
		DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		String date = fmt1.withZone(DateTimeZone.UTC).print(time1);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("requirement", requirementMetCategory).put("userId", userId).put("date", date);
		return jsonObj.toString();
	}
	
	public static String createRedeemVoucherPayload(String userId){
		JSONObject obj = new JSONObject();
		obj.put("businessPartnerId", 1).put("userId", userId);
		return obj.toString();
	}

	public static String createPayloadForNewServiceEvent(String userId, String time, String service){
		DateTime time1 = createTimeStamp(time);	
		String localDateTime = time1.toString("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		String date = time1.toString("yyyy-MM-dd");
		String dateTime = time1.toString("yyyy-MM-dd'T'HH:mm:SS'Z'");
		
		JSONObject obj = new JSONObject();
		obj.put("userId", userId);
		obj.put("localDateTime", localDateTime);
		obj.put("dateTime", dateTime);
		obj.put("date", date);
		obj.put("service", service);
		System.out.println(obj.toString());
		return(obj.toString());   
	}

	public static String createPayloadForPlanAssignment(String userId, String planStartDate, String profile, String plan){
		DateTime newTime = createTimeStamp(planStartDate);
		String localDateTime = newTime.toString("yyyy-MM-dd'T'HH:mm:SS'Z'");

		JSONObject obj = new JSONObject();
		obj.put("userId", userId).put("dateTime", localDateTime).put("hhProfile", profile).put("state", "Onboarded").put("hhPlan", plan);
		return obj.toString();
	}

	public static String createPayloadForSubmoduleAssignment(String userId, String time, String profile){
		DateTime time1 = createTimeStamp(time);
		
		String localDateTime = time1.toString("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		String date = time1.toString("yyyy-MM-dd");
		String dateTime = time1.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		JSONObject obj = new JSONObject();
		obj.put("eventType", "NEW_DAY").put("userId", userId).put("dateTime", dateTime).put("localDateTime", localDateTime)
		.put("date", date).put("userProfile", Integer.valueOf(profile)).put("subModuleDay", 1);
		return obj.toString();
	}
	
	public static String createHHASStoreQuestionnairePayload(String payloadInString){ 
		JSONObject json = new JSONObject(payloadInString); return json.toString(); 
		}

	public static String createMcpsGetContentPayload(String time) {
		
		String date = createTimeStamp(time).toString("yyyy-MM-dd");
		JSONObject body = new JSONObject();
		JSONObject payload = new JSONObject();
		body.put("startDate", date);
		body.put("endDate", date);
		payload.put("VCDIALOG", body);		
		return payload.toString();
	}

	public static String createMCPSJsonPayloadFromMap(HashMap<String, String> msg_resp_map) {
		JSONArray userDia = new JSONArray();
		JSONObject payload = new JSONObject();
		for (String key : msg_resp_map.keySet()) {
			JSONObject body = new JSONObject();
			body.put("messageId", key);
			body.put("visualId", msg_resp_map.get(key));
			userDia.put(body);
				    
				}
		payload.put("userDialogs", userDia)																																																		;
		payload.put("isCompleted", true);
		return payload.toString();
	}

	public static String createPublishSleepRatingPayload(String userId, String time, String Offset) {
		DateTime time1 = createTimeStamp(time, Offset);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String localDateTime = fmt.withZone(DateTimeZone.UTC).print(time1);
		String dateTime = fmt2.withZone(DateTimeZone.UTC).print(time1);
		String date = fmt1.withZone(DateTimeZone.UTC).print(time1);

		JSONObject obj = new JSONObject();
		obj.put("userId", userId);
		obj.put("localDateTime", localDateTime);
		obj.put("dateTime", dateTime);
		obj.put("date", date);
		obj.put("rating", "bad");
		System.out.println(obj.toString());
		return(obj.toString());    

	}
	
	public static String createMcps_retreiveCardByTypeAndDate_PostPayload(String startdate, String endDate) {
		
		JSONObject timepayload = new JSONObject();
		timepayload.put("startDate", startdate);
		timepayload.put("endDate", endDate);
		
		JSONObject payload = new JSONObject();
		payload.put("INSPIRATION", timepayload);
		payload.put("EDUCATION", timepayload);
		payload.put("FEEDBACK", timepayload);
		payload.put("FEEDBACK.WEEKLY",timepayload);
		payload.put("INSIGHT",timepayload);
		payload.put("INSIGHT.WEEKLY",timepayload);
		payload.put("VCDIALOG",timepayload);
		payload.put("INTRO",timepayload);
		payload.put("OUTRO",timepayload);
		return(payload.toString());
	}
	
	public static String createMdsObservationForSleepScore(String payload, String time){
		DateTime time1 = createTimeStamp(time, "0");

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:SS'Z'");
		String dateStr = fmt.withZone(DateTimeZone.UTC).print(time1);
		
		JSONObject resultPayload = new JSONObject(payload);
		JSONObject date = new JSONObject();
		JSONObject json = new JSONObject();
		JSONObject moonshine = new JSONObject();
		JSONObject res = resultPayload.getJSONObject("moonshine").getJSONObject("sleep");
		JSONObject jo = res.getJSONObject(resultPayload.getNames(res)[0]);
		date.put(dateStr, jo);
		
		moonshine.put("sleep", date);
		json.put("moonshine", moonshine);
	
		return json.toString();
	}

	
	
	public static String createActivityTypePayload(String Observation_Type, String value, String duration, String time, String offset) {
		DateTime time1 = createTimeStamp(time, offset);

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:SS'Z'");
		String dateStr = fmt.withZone(DateTimeZone.UTC).print(time1);

		JSONObject type = new JSONObject();
		JSONObject observation = new JSONObject();
		JSONObject date = new JSONObject();
		JSONObject data = new JSONObject();

		data.put("v", value);
		data.put("d", duration);
		date.put(dateStr, data);
		observation.put(Observation_Type, date);
		type.put("manual", observation);
		System.out.println(type.toString());
		return(type.toString());      
	}
	
	public static String createMdsWeightObservationPayload(String Observation_Type, String value, String time, String offset) {
		DateTime time1 = createTimeStamp(time, offset);

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:SS'Z'");
		String dateStr = fmt.withZone(DateTimeZone.UTC).print(time1);

		JSONObject type = new JSONObject();
		JSONObject observation = new JSONObject();
		JSONObject date = new JSONObject();
		JSONObject data = new JSONObject();

		data.put("v", Double.parseDouble(value));
		date.put(dateStr, data);
		observation.put(Observation_Type, date);
		type.put("manual", observation);
		System.out.println(type.toString());
		return(type.toString());      
	}

}
