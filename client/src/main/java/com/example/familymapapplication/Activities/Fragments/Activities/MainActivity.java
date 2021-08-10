package com.example.familymapapplication.Activities.Fragments.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.familymapapplication.Activities.Fragments.Activities.Fragments.LoginRegisterFragment;
import com.example.familymapapplication.Activities.Fragments.Activities.Fragments.MapFragment;
import com.example.familymapapplication.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

//i needed to run the server in the background because I couldn't put all of it in the shared folder.

public class MainActivity extends AppCompatActivity implements LoginRegisterFragment.LoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.main_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment loginFragment = fragmentManager.findFragmentById(R.id.fragment_login);

        if(loginFragment == null){
            loginFragment = createLoginFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_login, loginFragment)
                    .commit();
        }else{
            if(loginFragment instanceof LoginRegisterFragment){
                ((LoginRegisterFragment) loginFragment).setLoginListener(this);
            }
        }
    }

    private Fragment createLoginFragment(){
        LoginRegisterFragment fragment = new LoginRegisterFragment();
        fragment.setLoginListener(this);
        return fragment;
    }

    @Override
    public void onLogin() {
        //map stuff!
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment mapFragment = new MapFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_login, mapFragment)
                .addToBackStack(null)
                .commit();
    }
}