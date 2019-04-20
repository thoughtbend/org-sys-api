package com.thoughtbend.orgsysapi.web.resource;

import java.util.UUID;

public class Organization {

	private UUID id;
	private String name;
	private boolean artifactsAttached;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isArtifactsAttached() {
		return artifactsAttached;
	}

	public void setArtifactsAttached(boolean artifactsAttached) {
		this.artifactsAttached = artifactsAttached;
	}
}
