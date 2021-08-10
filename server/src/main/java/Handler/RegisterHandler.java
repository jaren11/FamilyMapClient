package Handler;

import DataAccess.DataAccessException;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {
    private boolean success;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        success = false;

        try {
            Gson gson = new Gson();
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStream bodyInput = exchange.getRequestBody();
                java.util.Scanner scanner = new java.util.Scanner(bodyInput).useDelimiter("\\A");
                String body = scanner.hasNext() ? scanner.next() : "";
                RegisterRequest registerRequest = gson.fromJson(body, RegisterRequest.class);
                JsonObject json = gson.fromJson(body, JsonObject.class);

                RegisterService service = new RegisterService();
                RegisterResult registerResult = service.register(registerRequest);

                String response = gson.toJson(registerResult);

                success = registerResult.isSuccess();

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
        catch (IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
