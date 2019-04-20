package com.thoughtbend.orgsysapi.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;

import static org.mockito.Mockito.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;

import com.thoughtbend.orgsysapi.web.resource.Organization;

public class OrganizationControllerTest {

	@Mock
	private LinkBuilder mockLinkBuilder;
	
	@Mock
	private EntityLinks mockEntityLinks;

	private OrganizationController target = null;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		this.target = new OrganizationController(this.mockEntityLinks);
	}

	@Test
	public void test_getOrganizations_success() {

		when(this.mockEntityLinks.linkFor(Organization.class)).thenReturn(this.mockLinkBuilder);
		when(this.mockLinkBuilder.slash(any(UUID.class))).thenReturn(this.mockLinkBuilder);
		when(this.mockLinkBuilder.withSelfRel()).thenReturn(new Link("/testlink", "self"));
		
		HttpEntity<Resources<Resource<Organization>>> result = this.target.getOrganizations();

		assertNotNull(result, "result should not be null");
		assertEquals(2, result.getBody().getContent().size());
	}
}
