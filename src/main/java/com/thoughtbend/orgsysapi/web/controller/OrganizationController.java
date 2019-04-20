package com.thoughtbend.orgsysapi.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thoughtbend.orgsysapi.web.resource.Organization;

@Controller
@RequestMapping("/api/v1/org")
@ExposesResourceFor(Organization.class)
public class OrganizationController {

	private final EntityLinks entityLinks;
	
	public OrganizationController(final EntityLinks entityLinks) {
		this.entityLinks = entityLinks;
	}
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpEntity<Resources<Resource<Organization>>> getOrganizations() {
		
		final Organization org = new Organization();
		org.setId(UUID.randomUUID());
		org.setName("My Org 1");
		
		final Organization org2 = new Organization();
		org2.setId(UUID.randomUUID());
		org2.setName("My Org 2");
		
		
		final List<Organization> orgList = new ArrayList<Organization>();
		orgList.add(org);
		orgList.add(org2);
		
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
}
