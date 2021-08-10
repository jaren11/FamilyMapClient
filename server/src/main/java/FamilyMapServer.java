import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import Handler.*;

public class FamilyMapServer {
    private HttpServer theServer;
    private final int CONNECTIONS = 12;

    public static void main(String[] args){
        String port = args[0];
        new FamilyMapServer().start(port);
    }

    private void start(String port) {

        try{
            theServer = HttpServer.create(new InetSocketAddress(Integer.parseInt(port)), CONNECTIONS);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        theServer.setExecutor(null);

        theServer.createContext("/user/register", new RegisterHandler());
        theServer.createContext("/user/login", new LoginHandler());
        theServer.createContext("/clear", new ClearHandler());
        theServer.createContext("/fill", new FillHandler());
        theServer.createContext("/load", new LoadHandler());
        theServer.createContext("/person", new PersonHandler());
        theServer.createContext("/event", new EventHandler());
        theServer.createContext("/", new NormalHandler());

        theServer.start();

        System.out.println("Server listening on port " + port);

    }
}
