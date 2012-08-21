package com.tmm.enterprise.microblog.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.common.collect.Maps;
import com.tmm.enterprise.microblog.domain.Status;
import com.tmm.enterprise.microblog.service.StatusService;

public class StatusController extends MultiActionController implements InitializingBean {

	@Autowired
	private StatusService statusService;

	@Override
	public void afterPropertiesSet() throws Exception {}

	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
	}

	
	/**
	 * Controller method invoked when a user attempts to create a new status
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String newStatus = request.getParameter("status");
		String userName = request.getRemoteUser();
		statusService.createStatus(newStatus, userName);
		Map<String, String> model = Maps.newHashMap();
		model.put("status", newStatus);
		return new ModelAndView("ajax_status", model);
	}

	/**
	 * Method to repeat another status
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView repeatStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String statusId = request.getParameter("status");
		statusId = statusId.replace("/", "");
		long id = Long.parseLong(statusId);
		Status oldStatus = statusService.loadStatus(id);
		String newStatus = oldStatus.getStatus();
		String userName = request.getRemoteUser();
		statusService.createStatus(newStatus, userName);

		Map<String, String> model = Maps.newHashMap();
		model.put("status", newStatus);
		return new ModelAndView("ajax_status", model);
	}
}