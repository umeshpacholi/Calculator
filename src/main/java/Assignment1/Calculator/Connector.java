package Assignment1.Calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Launcher;

public class Connector extends AbstractVerticle {	
	public	final static Logger logger = LoggerFactory.getLogger(Connector.class);
	
	public static void main(String[] args) {
		
		Launcher.executeCommand("run", Connector.class.getName());
	}
	
	@Override
	public void start(Future<Void> startFuture)throws Exception
	{

		   int procs = Runtime.getRuntime().availableProcessors();
		   vertx.deployVerticle(APIServerVerticle.class.getName(),  res -> {
														if(res.succeeded()) 
																			{
																			logger.info("Logger_info :  Deployed Verticle " + APIServerVerticle.class.getName());
																			System.out.println("Deployed Verticle " + APIServerVerticle.class.getName());
																			startFuture.complete();
																			}
														else
															{
															logger.info("Logger_info : Failed to deploy verticle " + res.cause());
															System.out.println("Failed to deploy verticle " + res.cause());
															startFuture.fail(res.cause());
															}
													  }
					  );
		   
		   vertx.deployVerticle( CalculatorVerticle.class.getName(), res -> {System.out.println("CalculatorVerticle deployed");} );


	}


}
