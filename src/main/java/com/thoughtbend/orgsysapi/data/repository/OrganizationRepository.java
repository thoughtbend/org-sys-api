package com.thoughtbend.orgsysapi.data.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.thoughtbend.orgsysapi.data.entity.OrganizationNode;

@Transactional
public interface OrganizationRepository extends CrudRepository<OrganizationNode, UUID>  {

}
