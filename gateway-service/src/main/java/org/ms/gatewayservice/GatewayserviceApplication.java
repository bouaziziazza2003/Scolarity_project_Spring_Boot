package org.ms.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
public class GatewayserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserviceApplication.class, args);
	}

	@Bean
	RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()

				.route(r -> r.path("/SCOLARITE-SERVICE/**")
						.filters(f -> f.rewritePath("/SCOLARITE-SERVICE/(?<segment>.*)", "/${segment}"))
						.uri("lb://SCOLARITE-SERVICE"))

				.route(r -> r.path("/PRODUIT-SERVICE/**")
						.filters(f -> f.rewritePath("/PRODUIT-SERVICE/(?<segment>.*)", "/${segment}"))
						.uri("lb://PRODUIT-SERVICE"))

				.route(r -> r
					    .path("/FACTURE-SERVICE/**")
					    .filters(f -> f.rewritePath("/FACTURE-SERVICE/(?<segment>.*)", "/${segment}"))
					    .uri("lb://FACTURE-SERVICE"))

				.route(r -> r
					    .path("/ETUDIANT-SERVICE/**")
					    .filters(f -> f.rewritePath("/ETUDIANT-SERVICE/(?<segment>.*)", "/${segment}"))
					    .uri("lb://ETUDIANT-SERVICE"))

				.route(r -> r
					    .path("/ENSEIGNANT-SERVICE/**")
					    .filters(f -> f.rewritePath("/ENSEIGNANT-SERVICE/(?<segment>.*)", "/${segment}"))
					    .uri("lb://ENSEIGNANT-SERVICE"))

				.route(r -> r
					    .path("/STAGE-SERVICE/**")
					    .filters(f -> f.rewritePath("/STAGE-SERVICE/(?<segment>.*)", "/${segment}"))
					    .uri("lb://STAGE-SERVICE"))

				.build();
	}
}