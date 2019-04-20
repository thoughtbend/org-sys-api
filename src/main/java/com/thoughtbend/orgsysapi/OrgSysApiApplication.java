package com.thoughtbend.orgsysapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrgSysApiApplication {

	
	private static final Logger LOG = LoggerFactory.getLogger(OrgSysApiApplication.class);

	
	public static void main(String[] args) {
		LOG.info("Starting OrgSysApiApplication");
		SpringApplication.run(OrgSysApiApplication.class, args);
	}

}
