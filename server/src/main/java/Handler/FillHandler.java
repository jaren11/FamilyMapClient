package Handler;

import DataAccess.DataAccessException;
import Service.ClearService;
import Service.FillService;
import Result.ClearResult;
import Result.FillResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    private boolean success;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        success = false;
        Gson g = new Gson();

        try{
            if(exchange.getRequestMethod().toLowerCase().equals("post")){
                FillService fillService = new FillService();
                Headers requestHeaders = exchange.getRequestHeaders();

                String requestURI = exchange.getRequestURI().toString();
                String response = "Error, invalid request";
                FillResult fillResult = null;

                requestURI = requestURI.substring(6);
                if(requestURI.contains("/")){
                    int index = requestURI.indexOf("/");
                    fillResult = fillService.fill(requestURI.substring(0, index), Integer.parseInt(requestURI.substring(index+1)));
                    response = g.toJson(fillResult);
                }else{
                    int numGen = 4;
                    fillResult = fillService.fill(requestURI, numGen);
                    response = g.toJson(fillResult);
                }

                success = fillResult.isSuccess();

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
        }catch(IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
