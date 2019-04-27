package com.thoughtbend.orgsysapi.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thoughtbend.orgsysapi.data.entity.OrganizationNode;
import com.thoughtbend.orgsysapi.data.repository.OrganizationRepository;
import com.thoughtbend.orgsysapi.web.resource.Organization;

@Controller
@RequestMapping("/api/v1/org")
@ExposesResourceFor(Organization.class)
public class OrganizationController {

	private static final Logger LOG = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	public OrganizationController() {
		//this.entityLinks = entityLinks;
	}
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpEntity<Resources<Resource<Organization>>> getOrganizations() {
		
		if (LOG.isTraceEnabled()) {
			LOG.trace("Entering GET - organization");
		}
		
		final List<Organization> orgList = new ArrayList<Organization>();
		//orgList.add(org);
		//orgList.add(org2);
		
		Iterable<OrganizationNode> orgNodes = this.organizationRepository.findAll();
		orgNodes.forEach(orgNode -> {
			final Organization org = new Organization();
			org.setId(UUID.fromString(orgNode.getId()));
			org.setName(orgNode.getName());
			orgList.add(org);
		});
		
		LinkBuilder linkBuilder = this.entityLinks.linkFor(Organization.class);
		List<Resource<Organization>> orgResourceList = new ArrayList<>();
		
		for (Organization currentOrg : orgList) {
			LinkBuilder nextLinkBuilder = linkBuilder.slash(currentOrg.getId());
			Link link = nextLinkBuilder.withSelfRel();
			Resource<Organization> currentOrgResource = new Resource<Organization>(currentOrg, link);
			orgResourceList.add(currentOrgResource);
		}
		
		Link masterSelfLink = linkBuilder.withSelfRel();
		Resources<Resource<Organization>> orgListResource = 
				new Resources<Resource<Organization>>(orgResourceList, masterSelfLink);
		
		return new ResponseEntity<Resources<Resource<Organization>>>(orgListResource, HttpStatus.OK);
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpEntity<Resource<Organization>> postOrganization(@RequestBody Organization newOrganization) {
	
		LOG.info("Received post request");
		OrganizationNode newOrgNode = new OrganizationNode();
		//newOrgNode.setId(UUID.randomUUID());
		newOrgNode.setName(newOrganization.getName());
		
		OrganizationNode persistedOrgNode = this.organizationRepository.save(newOrgNode);
		
		LOG.info(persistedOrgNode.toString());
		
		return new ResponseEntity<Resource<Organization>>(HttpStatus.CREATED);
	}
}
