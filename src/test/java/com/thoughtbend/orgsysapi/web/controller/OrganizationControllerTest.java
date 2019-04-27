package com.thoughtbend.orgsysapi.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
	
	private static final class TestData {
		final static String ORG_1_NAME = "org-1-name";
		final static String ORG_2_NAME = "org-2-name";
	}

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
		orgNode1.setName(TestData.ORG_1_NAME);
		orgNodeFixtures.add(orgNode1);
		
		OrganizationNode orgNode2 = new OrganizationNode();
		orgNode2.setId(UUID.randomUUID().toString());
		orgNode2.setName(TestData.ORG_2_NAME);
		orgNodeFixtures.add(orgNode2);
		
		when(this.mockOrgRepo.findAll()).thenReturn(orgNodeFixtures);
		when(this.mockEntityLinks.linkFor(Organization.class)).thenReturn(this.mockLinkBuilder);
		when(this.mockLinkBuilder.slash(any(UUID.class))).thenReturn(this.mockLinkBuilder);
		when(this.mockLinkBuilder.withSelfRel()).thenReturn(new Link("/testlink", "self"));
		
		HttpEntity<Resources<Resource<Organization>>> result = this.target.getOrganizations();

		assertNotNull(result, "result should not be null");
		
		Collection<Resource<Organization>> organizationResourceResultList = result.getBody().getContent();
		
		assertEquals(2, organizationResourceResultList.size());
		
		Iterator<Resource<Organization>> organizationResourceResultIterator = organizationResourceResultList.iterator();
		Resource<Organization> orgResourceResult1 = organizationResourceResultIterator.next();
		assertEquals(orgNode1.getId(), orgResourceResult1.getContent().getId().toString());
		assertEquals(TestData.ORG_1_NAME, orgResourceResult1.getContent().getName());
		
		Resource<Organization> orgResourceResult2 = organizationResourceResultIterator.next();
		assertEquals(orgNode2.getId(), orgResourceResult2.getContent().getId().toString());
		assertEquals(TestData.ORG_2_NAME, orgResourceResult2.getContent().getName());
	}
}
