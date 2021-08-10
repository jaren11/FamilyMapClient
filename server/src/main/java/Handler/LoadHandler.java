package Handler;

import DataAccess.DataAccessException;
import Service.LoadService;
import Request.LoadRequest;
import Result.LoadResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class LoadHandler implements HttpHandler {
    private boolean success;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        success = false;
        Gson gson = new Gson();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Headers requestHeaders = exchange.getRequestHeaders();

                LoadService loadService = new LoadService();

                InputStream input = exchange.getRequestBody();
                java.util.Scanner scanner = new java.util.Scanner(input).useDelimiter("\\A");
                String body = scanner.hasNext() ? scanner.next() : "";

                LoadRequest request = gson.fromJson(body, LoadRequest.class);
                LoadResult result = loadService.load(request);

                String response = gson.toJson(result);

                success = result.isSuccess();

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
        catch (IOException | DataAccessException | SQLException inputException) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            inputException.printStackTrace();
        }
    }
}
