package com.example;

import org.glassfish.jersey.server.ResourceConfig;

import provider.BearerTokenFilter;
import provider.GsonMessageBodyHandler;

public class CustomApplication extends ResourceConfig {
	public CustomApplication() {
		packages("bridge.service");

		register(GsonMessageBodyHandler.class);
		register(BearerTokenFilter.class);
	}
}
