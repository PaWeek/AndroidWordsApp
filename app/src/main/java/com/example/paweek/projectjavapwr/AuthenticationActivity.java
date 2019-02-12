package com.example.paweek.projectjavapwr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.paweek.projectjavapwr.Services.AuthenticationService;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class AuthenticationActivity extends AppCompatActivity {

    private AuthenticationService _service;
    private EditText emailTxt;
    private EditText passwordTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        _service = new AuthenticationService(this);

        final Button acceptBtn = (Button) findViewById(R.id.acceptBtn);
        final Switch isLoginSwitch = (Switch) findViewById(R.id.isLogin);
        emailTxt = (EditText) findViewById(R.id.emailTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);

        acceptBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                   if (isLoginSwitch.isChecked())
                       login();
                   else
                       register();
            }
        });
    }

    private void login()
    {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        if(_service.Login(email, password))
            openModules();
        else
        {
            showAlert("Wrong credentials");
            emailTxt.setText("");
            passwordTxt.setText("");
        }
    }

    private void register()
    {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        if(email.equals(""))
        {
            showAlert("Email cannot be empty");
            return;
        }

        if(password.equals(""))
        {
            showAlert("Password cannot be empty");
            return;
        }

        if(!validateEmail(email))
        {
            showAlert("Email is not valid");
            return;
        }

        if(!validatePassword(password))
        {
            showAlert("Password must min. 8 characters");
            return;
        }

        if(_service.Register(emailTxt.getText().toString(), passwordTxt.getText().toString()))
        {
            showAlert("Registered succeed");
            emailTxt.setText("");
            passwordTxt.setText("");
        }
        else showAlert("User with this email already exist");
    }

    private void openModules()
    {
        Intent intent = new Intent(this, ModulesActivity.class);
        startActivity(intent);
    }

    private void showAlert(String message)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.show();
    }

    private Boolean validateEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(email).matches();
    }

    private Boolean validatePassword(String password)
    {
        if (password.length() < 8)
            return false;

        return true;
    }
}
