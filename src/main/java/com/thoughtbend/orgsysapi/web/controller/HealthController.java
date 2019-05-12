package com.thoughtbend.orgsysapi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thoughtbend.orgsysapi.web.resource.HealthResponse;

@Controller
@RequestMapping(path="/api/org-sys-api-health")
public class HealthController {
	
	Logger LOG = LoggerFactory.getLogger(HealthController.class);
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HealthResponse getHealth() {
		LOG.debug("GET - /api/org-sys-api-health");
		return new HealthResponse(true);
	}

}