package com.tmm.enterprise.microblog.helper;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ControllerHelperTest {

	@Test
	public void testSuccessResponse() {
		ModelAndView mav = ControllerHelper.successResponse("test success message");
		Map<String, Object> model = mav.getModel();
		assertEquals("{model={data={\"success\":true,\"info\":\"test success message\",\"errorInfo\":\"\",\"dataLength\":0,\"data\":[]}}}", model.toString());
	}

	@Test
	public void testSuccessResponseJSON() {
		JsonObject job = ControllerHelper.successResponseJSON("test success message");
		assertEquals("{\"success\":true,\"info\":\"test success message\",\"errorInfo\":\"\",\"dataLength\":0,\"data\":[]}", job.toString());
	}

	@Test
	public void testErrorResponse() {
		ModelAndView mav = ControllerHelper.errorResponse("test error message");
		Map<String, Object> model = mav.getModel();
		assertEquals("{model={data={\"success\":false,\"errorInfo\":\"test error message\",\"totalCount\":0,\"data\":[]}}}", model.toString());
	}

	@Test
	public void testErrorResponseJSON() {
		JsonObject job = ControllerHelper.errorResponseJSON("test error message");
		assertEquals("{\"success\":false,\"errorInfo\":\"test error message\",\"totalCount\":0,\"data\":[]}", job.toString());
	}

	@Test
	public void testMakeMAVJsonObject() {
		JsonObject json = new JsonObject();
		json.addProperty("testValue", "test string being added");
		ModelAndView mav = ControllerHelper.makeMAV(json);
		Map<String, Object> model = mav.getModel();
		assertEquals("{model={data={\"success\":true,\"info\":\"\",\"errorInfo\":\"\",\"totalCount\":1,\"data\":[{\"testValue\":\"test string being added\"}]}}}", model.toString());
	}

	@Test
	public void testMakeMAVJsonObjectString() {
		JsonObject json = new JsonObject();
		json.addProperty("testValue", "test string being added");
		ModelAndView mav = ControllerHelper.makeMAV(json, "info string");
		Map<String, Object> model = mav.getModel();
		assertEquals("{model={data={\"success\":true,\"info\":\"info string\",\"errorInfo\":\"\",\"totalCount\":1,\"data\":[{\"testValue\":\"test string being added\"}]}}}", model.toString());
	}

	@Test
	public void testMakeMAVJsonArray() {
		JsonObject json = new JsonObject();
		json.addProperty("testValue", "test string being added");
		JsonArray array = new JsonArray();
		array.add(json);
		ModelAndView mav = ControllerHelper.makeMAV(array);
		Map<String, Object> model = mav.getModel();
		assertEquals("{model={data={\"success\":true,\"info\":\"\",\"errorInfo\":\"\",\"totalCount\":1,\"data\":[{\"testValue\":\"test string being added\"}]}}}", model.toString());
	}

	@Test
	public void testMakeMAVJsonArrayString() {
		JsonObject json = new JsonObject();
		json.addProperty("testValue", "test string being added");
		JsonArray array = new JsonArray();
		array.add(json);
		ModelAndView mav = ControllerHelper.makeMAV(array, "info string");
		Map<String, Object> model = mav.getModel();
		assertEquals("{model={data={\"success\":true,\"info\":\"info string\",\"errorInfo\":\"\",\"totalCount\":1,\"data\":[{\"testValue\":\"test string being added\"}]}}}", model.toString());
	}


	@Test
	public void testMakeResponseStringJsonArray() {
		JsonObject json = new JsonObject();
		json.addProperty("testValue", "test string being added");
		JsonArray array = new JsonArray();
		array.add(json);
		JsonObject job = ControllerHelper.makeResponse("info string", array);
		assertEquals("{\"success\":true,\"info\":\"info string\",\"errorInfo\":\"\",\"totalCount\":1,\"data\":[{\"testValue\":\"test string being added\"}]}", job.toString());
	}

	@Test
	public void testMakeResponseStringJsonObject() {
		JsonObject json = new JsonObject();
		json.addProperty("testValue", "test string being added");
		JsonObject job = ControllerHelper.makeResponse("info string", json);
		assertEquals("{\"success\":true,\"info\":\"info string\",\"errorInfo\":\"\",\"totalCount\":1,\"data\":{\"testValue\":\"test string being added\"}}", job.toString());
	}

	@Test
	public void testBuildErrorMAV() {
		ModelAndView mav = ControllerHelper.buildErrorMAV("error message");
		Map<String, Object> model = mav.getModel();
		assertEquals("{errorMessage=error message}", model.toString());
	
	}

}
