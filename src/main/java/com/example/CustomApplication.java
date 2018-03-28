package com.example;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;

import provider.BearerTokenFilter;
import provider.GsonMessageBodyHandler;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/v1")
public class CustomApplication extends ResourceConfig {
	public CustomApplication() {
		packages("bridge.service");

		//classes do swagger...
		register(ApiListingResource.class);
		register(SwaggerSerializers.class);

		register(GsonMessageBodyHandler.class);
		register(BearerTokenFilter.class);
	}
}
