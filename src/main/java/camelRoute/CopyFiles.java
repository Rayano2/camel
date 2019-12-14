package camelRoute;

import com.sun.javafx.util.Logging;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.logging.Logger;

public class CopyFiles {

     private static final Logger logging = Logger.getLogger(CopyFiles.class.getName());

    public static void main(String[] args) throws Exception {
    





        CamelContext context = new DefaultCamelContext();

        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:data/input?noop=true")
                            .to("log:?level=INFO&showBody=true")
                            .process(new Processor() {
                                public void process(Exchange exchange) throws Exception {
                                    Object body = exchange.getMessage().getBody();
                                    Object header = exchange.getMessage().getHeaders();

                                    logging.info(String.valueOf(body));
                                    logging.info(String.valueOf(" header is :"  + header));
                                }
                            })
                            .to("file:data/output");
                }
            });
            context.start();
            logging.info(String.valueOf("router list :" + context.getRoutes()));
            Thread.sleep(5000);
            context.stop();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
