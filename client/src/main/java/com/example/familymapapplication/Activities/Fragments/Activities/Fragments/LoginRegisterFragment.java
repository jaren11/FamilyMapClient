package com.example.familymapapplication.Activities.Fragments.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.familymapapplication.Activities.Fragments.Models.DataCache;
import com.example.familymapapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.familymapapplication.Activities.Fragments.Models.ServerProxy;

import Model.Event;
import Model.Person;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.EventResult;
import Result.LoginResult;
import Result.PersonResult;
import Result.RegisterResult;

public class LoginRegisterFragment extends Fragment {
    
    private EditText serverHost;
    private EditText serverPort;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private LoginListener listener;
    private TextWatcher wotcher;
    private DataCache theDataCache = DataCache.getInstance();

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    private Button bMale;
    private Button bFemale;

    private Button bSignIn;
    private Button bRegister;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        registerRequest = new RegisterRequest();
        loginRequest = new LoginRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        wotcher = new ChangeButtons();
        View view = inflater.inflate(R.layout.layout_register_login, container, false);

        serverHost = view.findViewById(R.id.serverHostInput);
        serverHost.addTextChangedListener(wotcher);
        serverPort = view.findViewById(R.id.portNumberInput);
        serverPort.addTextChangedListener(wotcher);
        username = view.findViewById(R.id.usernameInput);
        username.addTextChangedListener(wotcher);
        password = view.findViewById(R.id.passwordInput);
        password.addTextChangedListener(wotcher);
        firstName = view.findViewById(R.id.firstNameInput);
        firstName.addTextChangedListener(wotcher);
        lastName = view.findViewById(R.id.lastNameInput);
        lastName.addTextChangedListener(wotcher);
        email = view.findViewById(R.id.emailInput);
        email.addTextChangedListener(wotcher);

        bSignIn = view.findViewById(R.id.signInButton);
        bRegister = view.findViewById(R.id.registerButton);

        bFemale = view.findViewById(R.id.female);
        bFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRequest.setGender("f");
                testBoth();
            }
        });

        bMale = view.findViewById(R.id.male);
        bMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRequest.setGender("m");
                testBoth();
            }
        });

        testBoth();
        //clicking sign in button

        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest.setUsername(username.getText().toString());
                loginRequest.setPassword(password.getText().toString());

                LoginTask login = new LoginTask(loginHandler, serverHost.getText().toString(),
                        serverPort.getText().toString(), loginRequest);

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(login);
            }
        });

        testBoth();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRequest.setUsername(username.getText().toString());
                registerRequest.setPassword(password.getText().toString());
                registerRequest.setFirstName(firstName.getText().toString());
                registerRequest.setLastName(lastName.getText().toString());
                registerRequest.setEmail(email.getText().toString());

                RegisterTask register = new RegisterTask(registerHandler, serverHost.getText().toString(),
                        serverPort.getText().toString(), registerRequest);

                //register
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(register);
            }
        });

        return view;
    }

    Handler loginHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();

            if(bundle.getBoolean("loginSuccess") == false){
                Context context = getContext();
                CharSequence text = "Error: Login failed";
                int duration = Toast.LENGTH_SHORT;

                Toast.makeText(context, text, duration).show();
            }else{
                Context context = getContext();
                CharSequence text = "Login succeeded!!";
                int duration = Toast.LENGTH_SHORT;

                Toast.makeText(context, text, duration).show();

                GetDataTask dataTask = new GetDataTask(dataHandler, serverHost.getText().toString(),
                        serverPort.getText().toString(), bundle.getString("loginAuthToken"));

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(dataTask);
                listener.onLogin();
            }

        }
    };

    Handler registerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();

            if(bundle.getBoolean("registerSuccess") == false){
                Context context = getContext();
                CharSequence text = "Error: Register failed";
                int duration = Toast.LENGTH_SHORT;

                Toast.makeText(context, text, duration).show();
            }else{
                Context context = getContext();
                CharSequence text = "Register succeeded!!";
                int duration = Toast.LENGTH_SHORT;

                Toast.makeText(context, text, duration).show();

                GetDataTask dataTask = new GetDataTask(dataHandler, serverHost.getText().toString(),
                        serverPort.getText().toString(), bundle.getString("registerAuthToken"));

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(dataTask);
            }

        }
    };

    Handler dataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();

            if(bundle.getBoolean("personSuccess") == true && bundle.getBoolean("eventSuccess") == true){
                Context context = getContext();

                ArrayList<Person> arrayList = (ArrayList<Person>)bundle.getSerializable("personsArrayList");

                for(int i = 0; i < arrayList.size(); i++){

                    if(arrayList.get(i).getPersonID() == theDataCache.getPersonID()){
                        firstName.setText(arrayList.get(i).getFirstName());
                        lastName.setText(arrayList.get(i).getLastName());
                        break;
                    }

                }

                theDataCache.initializeAllTheData();

                CharSequence text = "Hello, " + firstName.getText().toString() + " " + lastName.getText().toString();
                int duration = Toast.LENGTH_SHORT;

                Toast.makeText(context, text, duration).show();

            }else {
                Context context = getContext();
                CharSequence text = "Error: Could not get data";
                int duration = Toast.LENGTH_SHORT;

                Toast.makeText(context, text, duration).show();
            }
        }
    };

    private static class LoginTask implements Runnable{
        private Handler theHandler;
        private LoginRequest theRequest;
        private String theHost;
        private String thePort;

        LoginTask(Handler handler, String host, String port, LoginRequest loginRequest){
            this.theHandler = handler;
            this.theRequest = loginRequest;
            this.theHost = host;
            this.thePort = port;
        }

        @Override
        public void run() {
            ServerProxy theProxy = new ServerProxy();
            LoginResult answer = theProxy.login(theHost, thePort, theRequest);

            sendMessage(answer);
        }

        public void sendMessage(LoginResult loginResult){
            boolean isSuccess = loginResult.isSuccess();
            Bundle theBundle = new Bundle();

            theBundle.putBoolean("loginSuccess", isSuccess);
            theBundle.putString("loginAuthToken", loginResult.getAuthToken());

            Message sendToHandler = Message.obtain();
            sendToHandler.setData(theBundle);

            theHandler.sendMessage(sendToHandler);
        }
    }

    private static class RegisterTask implements Runnable{
        private Handler theHandler;
        private RegisterRequest theRequest;
        private String theHost;
        private String thePort;

        RegisterTask(Handler handler, String host, String port, RegisterRequest registerRequest){
            this.theHandler = handler;
            this.theRequest = registerRequest;
            this.theHost = host;
            this.thePort = port;
        }

        @Override
        public void run() {
            ServerProxy theProxy = new ServerProxy();
            RegisterResult answer = theProxy.register(theHost, thePort, theRequest);
            sendMessage(answer);
        }

        public void sendMessage(RegisterResult registerResult){
            boolean isSuccess = registerResult.isSuccess();
            Bundle theBundle = new Bundle();
            theBundle.putBoolean("registerSuccess", isSuccess);
            theBundle.putString("registerAuthToken", registerResult.getAuthtoken());

            Message sendToHandler = Message.obtain();
            sendToHandler.setData(theBundle);

            theHandler.sendMessage(sendToHandler);
        }
    }

    //task to get people and events
    private class GetDataTask implements Runnable{
        private Handler theHandler;
        private String theHost;
        private String thePort;
        private String theAuthtoken;

        public GetDataTask(Handler handler, String host, String port, String authtoken){
            this.theHandler = handler;
            this.theHost = host;
            this.thePort = port;
            this.theAuthtoken = authtoken;
        }

        @Override
        public void run() {
            ServerProxy theProxy = new ServerProxy();
            PersonResult persons = theProxy.getPersons(theHost, thePort, theAuthtoken);
            EventResult events = theProxy.getEvents(theHost, thePort, theAuthtoken);
            DataCache theDataCache = DataCache.getInstance();
            theDataCache.setPersonID(persons.getData().get(0).getPersonID());
            sendMessage(persons, events);
        }

        public void sendMessage(PersonResult personResult, EventResult eventResult){
            ArrayList<Person> thePersons = personResult.getData();
            String personMessage = personResult.getMessage();
            boolean personSuccess = personResult.isSuccess();

            ArrayList<Event> theEvents = eventResult.getData();
            String eventMessage = eventResult.getMessage();
            boolean eventSuccess = eventResult.isSuccess();
            Bundle theBundle = new Bundle();

            theBundle.putSerializable("personsArrayList", thePersons);
            theBundle.putString("personMessage", personMessage);
            theBundle.putBoolean("personSuccess", personSuccess);

            theBundle.putSerializable("eventsArrayList", theEvents);
            theBundle.putString("eventMessage", eventMessage);
            theBundle.putBoolean("eventSuccess", eventSuccess);

            Message sendToHandler = Message.obtain();
            sendToHandler.setData(theBundle);

            theHandler.sendMessage(sendToHandler);
        }
    }

    private class ChangeButtons implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            testBoth();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public void testBoth(){
        if(testRegisterButton() == false){
            bRegister.setEnabled(false);
        }else{
            bRegister.setEnabled(true);
        }

        if(testSignInButton() == false){
            bSignIn.setEnabled(false);
        }else{
            bSignIn.setEnabled(true);
        }
    }

    private boolean testRegisterButton(){
        if (TextUtils.isEmpty(serverHost.getText()) || TextUtils.isEmpty(serverPort.getText()) ||
                TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText()) ||
                TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(firstName.getText()) ||
                TextUtils.isEmpty(lastName.getText()) || registerRequest.getGender() == null){
            return false;
        }
        else {
            return true;
        }
    }

    private boolean testSignInButton(){
        if (TextUtils.isEmpty(serverHost.getText()) ||
                TextUtils.isEmpty(serverPort.getText()) ||
                TextUtils.isEmpty(username.getText()) ||
                TextUtils.isEmpty(password.getText())){
            return false;
        }
        else {
            return true;
        }
    }





    public interface LoginListener {
        void onLogin();
    }

    public void setLoginListener(LoginListener loginListener) {
        this.listener = loginListener;
    }

}
