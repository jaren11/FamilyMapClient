package Handler;

import DataAccess.DataAccessException;
import Service.EventIDService;
import Service.EventService;
import Result.EventIDResult;
import Result.EventResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {
    private boolean success;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        success = false;

        try{
            if(exchange.getRequestMethod().toLowerCase().equals("get")){
                Headers requestHeaders = exchange.getRequestHeaders();
                if(requestHeaders.containsKey("Authorization")){
                    String token = requestHeaders.getFirst("Authorization");
                    String requestURI = exchange.getRequestURI().toString();
                    String response;
                    EventService eventService = new EventService();
                    EventIDService eventIDService = new EventIDService();
                    Gson gson = new Gson();

                    if (requestURI.equals("/event")){
                        EventResult allResult = eventService.event(token);
                        if (!(allResult.getMessage() == null)){
                            response = "{ \"message\": \"" + allResult.getMessage() + "\"}";
                        }
                        else {
                            response = gson.toJson(allResult);
                        }
                        success = allResult.isSuccess();
                    }
                    else if (requestURI.substring(0,7).equals("/event/")){
                        EventIDResult singleResult = eventIDService.eventIDResult(requestURI.substring(7), token);
                        if (!(singleResult.getMessage() == null)){
                            response = "{ \"message\": \"" + singleResult.getMessage() + "\"}";
                        }
                        else {
                            response = gson.toJson(singleResult);
                        }
                        success = singleResult.isSuccess();
                    }
                    else {
                        success = false;
                        response = "Error: Invalid request";
                    }

                    if(success){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }

                    OutputStream responseBody = exchange.getResponseBody();
                    OutputStreamWriter streamWriter = new OutputStreamWriter(responseBody);
                    streamWriter.write(response);
                    streamWriter.flush();

                    responseBody.close();
                }
            }

        } catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }
}
