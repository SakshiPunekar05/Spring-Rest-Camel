package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
class MyRouteBuilder extends RouteBuilder {
	
	@Override
	public void configure() {
		restConfiguration().component("servlet")
		.contextPath("/camel-rest-jpa")
		.apiContextPath("/api-doc")
		.bindingMode(RestBindingMode.json);
		
		

		// Expose end-point
		rest("/articles").description("Articles REST service")
			
		// GET /articles
			.get("/")
			.description("The list of all the articles")
			.route().routeId("get-articles")
			.bean(ArticleService.class, "findArticles").endRest()

		// GET /articles/{id}
			.get("/{id}")
			.description("Details of an article by id")
			.param().name("id").description("find Article by id").endParam()
			.route().routeId("get-article")
			.bean(ArticleService.class, "findArticle(${header.id})").endRest()
			
		// DELETE /articles/{id}
			.delete("/{id}")
			.description("Delete article")
			.param().name("id").description("Delete Article by id").endParam()
			.route().routeId("delete-article")
			.bean(ArticleService.class, "deleteArticle(${header.id})").endRest()

		//POST /newarticle
			.post("/newarticle").description("Create new articles")
			.param().name("id").description("post article").endParam()
		    .consumes(MediaType.APPLICATION_JSON_VALUE).route().routeId("post-article")
		    .to("direct:convert");

		// Send request data to queue
		from("direct:convert")
			.marshal().json()
				.bean(RestToQueue.class, "sendToQueue(${body})");

		// JSON Data Format
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Article.class);
		
		// consume messages from queue and insert into DB
		from("activemq:message").routeId("generate-article").unmarshal(jsonDataFormat)
		.bean(ArticleService.class,"generateArticle(${body})");
	}

}

