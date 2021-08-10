package Handler;

import DataAccess.DataAccessException;
import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import Result.LoginResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
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
                LoginRequest loginRequest = gson.fromJson(body, LoginRequest.class);
                JsonObject theJson = gson.fromJson(body, JsonObject.class);

                LoginService service = new LoginService();
                LoginResult logResult = service.login(loginRequest);

                String response = gson.toJson(logResult);

                success = logResult.isSuccess();

                if (success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                OutputStream responseOutput = exchange.getResponseBody();
                OutputStreamWriter streamWriter = new OutputStreamWriter(responseOutput);
                streamWriter.write(response);
                streamWriter.flush();
                responseOutput.close();
            }
        }catch (IOException | DataAccessException inputException) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            inputException.printStackTrace();
        }
    }
}
