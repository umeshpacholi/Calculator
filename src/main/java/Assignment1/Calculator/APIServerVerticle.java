package Assignment1.Calculator;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

public class APIServerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(APIServerVerticle.class);
	
	@Override
	public void start() {
	    Router router = Router.router(vertx);
		router.get("/add").handler(this::add);
		HttpServer server =vertx.createHttpServer();
		server.requestHandler(router::accept).listen(8080);

		logger.info("Logger_info : APIServerVerticle started  : " );
		System.out.println("APIServerVerticle started  : " );
	}
	
	private void add(RoutingContext routingContext) {
		System.out.println("APIServerVerticle add method called");
		HttpServerResponse response = routingContext.response();
		JsonObject requested_data;
		DeliveryOptions options = new DeliveryOptions();
		options.addHeader("my_attribute1", "my_attribute1");
		options.addHeader("my_attribute2", "my_attribute2");
		requested_data = routingContext.getBodyAsJson();
		publishEvent("add", requested_data, options, response);
		
	}
	
	private void publishEvent(String event, JsonObject requested_data, DeliveryOptions options, HttpServerResponse response) {
		
		vertx.eventBus().request(event, requested_data, options, replyHandler -> {
			if (replyHandler.succeeded())
			{
				String reply = replyHandler.result().body().toString();
				System.out.println("CalculatorVerticle 'add' endpoint success : " + reply);
				response.setStatusCode(200).putHeader(HttpHeaders.CONTENT_TYPE.toString(), "application/json").end(reply);
				
			} else { System.out.println("CalculatorVerticle 'add' endpoint error cause : " + replyHandler.cause().getMessage());
				   }
		});
	}
}
