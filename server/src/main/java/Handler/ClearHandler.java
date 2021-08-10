package Handler;

import Result.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    private boolean success;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        success = false;

        try{
            if(exchange.getRequestMethod().toLowerCase().equals("post")){
                ClearService clearService = new ClearService();
                ClearResult clearResult = clearService.clear();

                String response = "{ \"message\": \"" + clearResult.getMessage() + "\"}";

                success = clearResult.isSuccess();

                if(success){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                OutputStream responseStream = exchange.getResponseBody();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(responseStream);
                outputStreamWriter.write(response);
                outputStreamWriter.flush();
                responseStream.close();


            }
        }catch(IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
