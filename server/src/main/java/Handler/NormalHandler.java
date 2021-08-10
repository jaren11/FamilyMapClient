package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class NormalHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            String filePathStr;
            String requestURI = exchange.getRequestURI().toString();

            if (requestURI.equals("/")) {
                filePathStr = "D:/School/2021 Summer/CS 240/FamilyMapServerStudent-master/web/index.html";
            }
            else {
                filePathStr = "D:/School/2021 Summer/CS 240/FamilyMapServerStudent-master/web" + requestURI;
            }

            File theFile = new File(filePathStr);

            if(theFile.exists()){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }else{
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                filePathStr = "D:/School/2021 Summer/CS 240/FamilyMapServerStudent-master/web/HTML/404.html";
            }

            OutputStream responseBody = exchange.getResponseBody();
            Path filePath = FileSystems.getDefault().getPath(filePathStr);

            Files.copy(filePath, responseBody);
            responseBody.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
