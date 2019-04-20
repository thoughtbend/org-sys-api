package com.thoughtbend.orgsysapi.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;

import com.thoughtbend.orgsysapi.data.entity.OrganizationNode;
import com.thoughtbend.orgsysapi.data.repository.OrganizationRepository;
import com.thoughtbend.orgsysapi.web.resource.Organization;

@SpringBootTest
public class OrganizationControllerTest {

	@Mock
	private LinkBuilder mockLinkBuilder;
	
	@Mock
	private EntityLinks mockEntityLinks;
	
	@Mock
	private OrganizationRepository mockOrgRepo;

	@InjectMocks
	private OrganizationController target = new OrganizationController();

	@Test
	public void test_getOrganizations_success() {

		List<OrganizationNode> orgNodeFixtures = new ArrayList<>();
		OrganizationNode orgNode1 = new OrganizationNode();
		orgNode1.setId(UUID.randomUUID().toString());
		orgNodeFixtures.add(orgNode1);
		
		OrganizationNode orgNode2 = new OrganizationNode();
		orgNode2.setId(UUID.randomUUID().toString());
		orgNodeFixtures.add(orgNode2);
		
		when(this.mockOrgRepo.findAll()).thenReturn(orgNodeFixtures);
		when(this.mockEntityLinks.linkFor(Organization.class)).thenReturn(this.mockLinkBuilder);
		when(this.mockLinkBuilder.slash(any(UUID.class))).thenReturn(this.mockLinkBuilder);
		when(this.mockLinkBuilder.withSelfRel()).thenReturn(new Link("/testlink", "self"));
		
		HttpEntity<Resources<Resource<Organization>>> result = this.target.getOrganizations();

		assertNotNull(result, "result should not be null");
		assertEquals(2, result.getBody().getContent().size());
	}
}
