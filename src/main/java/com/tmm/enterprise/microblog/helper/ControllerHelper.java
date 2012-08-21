package com.tmm.enterprise.microblog.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ControllerHelper{

	
	@SuppressWarnings("unchecked")
	public static ModelAndView successResponse(String message){
		JsonObject json =successResponseJSON(message);
		HashMap model = new HashMap();
		model.put("data", json.toString());
		return new ModelAndView("json", "model", model);
	}

	public static JsonObject successResponseJSON(String message){
		JsonObject json = makeResponse(message, "");
		json.addProperty("dataLength", 0);
		json.add("data", new JsonArray());
		
		return json;
	}	
	
	@SuppressWarnings("unchecked")
	public static ModelAndView errorResponse(String error){
		JsonObject json = errorResponseJSON(error);	
		HashMap model = new HashMap();
		model.put("data", json.toString());
		return new ModelAndView("json", "model", model);
	}
	
	public static JsonObject errorResponseJSON(String error){
		JsonObject json = makeResponse(null, error);
		json.addProperty("totalCount", 0);
		json.add("data", new JsonArray());
		
		return json;
	}	
	
	

	public static ModelAndView makeMAV(JsonObject json){
		JsonArray tmpArray = new JsonArray();
		tmpArray.add(json);
		return makeMAV(tmpArray);
	}
	
	public static ModelAndView makeMAV(JsonObject json, String info){
		JsonArray tmpArray = new JsonArray();
		tmpArray.add(json);
		return makeMAV(tmpArray, info);
	}
	
	public static ModelAndView makeMAV(JsonArray json){
		return makeMAV(json, "");
	}
	
	@SuppressWarnings("unchecked")
	public static ModelAndView makeMAV(JsonArray json, String info){
		JsonObject o = makeResponse(info, json);
		HashMap model = new HashMap();
		model.put("data", o.toString());
		return new ModelAndView("json", "model", model);
	}

	
	public static JsonObject makeResponse(String info, JsonArray array){
		JsonObject json = makeResponse(info, "");
		json.addProperty("totalCount", array.size());
		json.add("data", array);
		return json;
	}

	public static JsonObject makeResponse(String info, JsonObject o){
		JsonObject json = makeResponse(info, "");
		json.addProperty("totalCount", 1);
		json.add("data", o);
		return json;
	}

	@SuppressWarnings("null")
	private static JsonObject makeResponse(String info, String errorInfo){
		JsonObject json = new JsonObject();

		if (info != null){
			json.addProperty("success", Boolean.TRUE);
			json.addProperty("info", info);
		}else {
			json.addProperty("success", Boolean.FALSE);
		}


		if (errorInfo != null || errorInfo.length() == 0){
			json.addProperty("errorInfo", errorInfo);
		}

		return json;
	}
	
	
	/**
	 * Method to build the page to display a generic error page
	 * 
	 * @param errorMessage
	 * @return
	 */
	public static ModelAndView buildErrorMAV(String errorMessage){
		Map<String, Object> model = Maps.newHashMap();
		model.put("errorMessage", errorMessage);			
		return new ModelAndView("genericError", model);
	}
}
