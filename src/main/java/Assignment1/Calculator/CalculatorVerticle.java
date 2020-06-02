package Assignment1.Calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

public class CalculatorVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(CalculatorVerticle.class);
	
	@Override
	public void start() throws Exception {
		System.out.println("Inside CalculatorVerticle : start method called ");
		logger.info("Inside CalculatorVerticle : start method called ");

		vertx.eventBus().consumer("add", message -> {	add(message);	message.reply("Messaged returned from add method of calculator");} ) ;
	}
	
	private void add(Message<Object> message) {
		System.out.println("Inside CalculatorVerticle message passed for add method : "+message.toString());
	}
}
