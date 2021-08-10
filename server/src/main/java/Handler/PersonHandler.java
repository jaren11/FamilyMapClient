package Handler;

import DataAccess.DataAccessException;
import Result.PersonIDResult;
import Result.PersonResult;
import Service.PersonIDService;
import Service.PersonService;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    private boolean success;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        success = false;
        Gson gson = new Gson();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers requestHeaders = exchange.getRequestHeaders();
                if (requestHeaders.containsKey("Authorization")) {
                    PersonService personService = new PersonService();
                    PersonIDService personIDService = new PersonIDService();

                    String authToken = requestHeaders.getFirst("Authorization");
                    String uRI = exchange.getRequestURI().toString();
                    String response = "Invalid request";

                    if (uRI.equals("/person")){
                        PersonResult allResult = personService.person(authToken);
                        response = gson.toJson(allResult);
                        success = allResult.isSuccess();
                    }
                    else if (uRI.substring(0,8).equals("/person/")){
                        PersonIDResult singleResult = personIDService.personID(uRI.substring(8), authToken);
                        response = gson.toJson(singleResult);
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

                    OutputStream theResponse = exchange.getResponseBody();

                    OutputStreamWriter streamWriter = new OutputStreamWriter(theResponse);
                    streamWriter.write(response);
                    streamWriter.flush();

                    theResponse.close();
                }
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
