package com.thoughtbend.orgsysapi.data.repository;

import java.util.UUID;

import org.neo4j.ogm.id.IdStrategy;

public class UUIDIdStrategy implements IdStrategy {

	@Override
	public Object generateId(Object entity) {
		
		return UUID.randomUUID();
	}

}
