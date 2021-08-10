package com.example.familymapapplication.Activities.Fragments.Models;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.EventResult;
import Result.LoginResult;
import Result.PersonResult;
import Result.RegisterResult;

import com.google.gson.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerProxy {
    public ServerProxy(){
    }

    public LoginResult login (String host, String port, LoginRequest loginRequest){
        Gson g = new Gson();

        try{
            URL url = new URL("http://" + host + ":" + port + "/user/login");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.connect();

            try(OutputStream requestBody = connection.getOutputStream()) {
                // Write request body to OutputStream ...
                String requestInfo = g.toJson(loginRequest);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(requestBody);
                outputStreamWriter.write(requestInfo);
                outputStreamWriter.flush();

            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream responseBody = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
                char[] theCharArray = new char[1024];
                int girth;
                while ((girth = inputStreamReader.read(theCharArray)) > 0) {
                    sb.append(theCharArray, 0, girth);
                }

                String response = sb.toString();

                LoginResult loginResult = g.fromJson(response, LoginResult.class);
                return loginResult;
            }
            else {
                return new LoginResult(connection.getResponseMessage());
            }

        }catch(IOException e){
            e.printStackTrace();
            return new LoginResult("Error: login error");
        }
    }


    public RegisterResult register (String host, String port, RegisterRequest registerRequest){
        Gson g = new Gson();

        try{
            URL url = new URL("http://" + host + ":" + port + "/user/register");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.connect();

            try(OutputStream requestBody = connection.getOutputStream()) {
                // Write request body to OutputStream ...
                String requestInfo = g.toJson(registerRequest);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(requestBody);
                outputStreamWriter.write(requestInfo);
                outputStreamWriter.flush();

            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream responseBody = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
                char[] theCharArray = new char[1024];
                int girth;
                while ((girth = inputStreamReader.read(theCharArray)) > 0) {
                    sb.append(theCharArray, 0, girth);
                }

                String response = sb.toString();

                RegisterResult registerResult = g.fromJson(response, RegisterResult.class);
                return registerResult;
            }
            else {
                return new RegisterResult(connection.getResponseMessage());
            }

        }catch(IOException e){
            e.printStackTrace();
            return new RegisterResult("Error: login error");
        }
    }

    //get people and events functions
    public PersonResult getPersons (String host, String port, String authtoken){
        Gson g = new Gson();

        try{
            URL url = new URL("http://" + host + ":" + port + "/person");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", authtoken);
            connection.addRequestProperty("Accept", "application/json");

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream responseBody = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
                char[] theCharArray = new char[1024];
                int girth;
                while ((girth = inputStreamReader.read(theCharArray)) > 0) {
                    sb.append(theCharArray, 0, girth);
                }

                String response = sb.toString();

                PersonResult personResult = g.fromJson(response, PersonResult.class);
                DataCache.getInstance().putInPeople(personResult);
                return personResult;
            }
            else {
                return new PersonResult(connection.getResponseMessage());
            }

        }catch(IOException e){
            e.printStackTrace();
            return new PersonResult("Error: cannot retrieve people");
        }
    }

    public EventResult getEvents (String host, String port, String authtoken){
        Gson g = new Gson();

        try{
            URL url = new URL("http://" + host + ":" + port + "/event");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", authtoken);
            connection.addRequestProperty("Accept", "application/json");

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream responseBody = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
                char[] theCharArray = new char[1024];
                int girth;
                while ((girth = inputStreamReader.read(theCharArray)) > 0) {
                    sb.append(theCharArray, 0, girth);
                }

                String response = sb.toString();

                EventResult eventResult = g.fromJson(response, EventResult.class);
                DataCache.getInstance().putInEvents(eventResult);
                return eventResult;
            }
            else {
                return new EventResult(connection.getResponseMessage());
            }

        }catch(IOException e){
            e.printStackTrace();
            return new EventResult("Error: cannot retrieve people");
        }
    }
}
