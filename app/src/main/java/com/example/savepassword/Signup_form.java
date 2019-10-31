package com.example.savepassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.PasswordAuthentication;

public class Signup_form extends AppCompatActivity {
    private EditText Name;
    private EditText Email;
    private EditText Password;
    private EditText Re_Password;
    private EditText Mobile_Number;
    private EditText Country;
    private Button Signup;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        db = new DatabaseHelper(this);

        Name = findViewById(R.id.EtName);
        Email = findViewById(R.id.Etemail);
        Password = findViewById(R.id.Etpassword);
        Re_Password = findViewById(R.id.EtRe_password);
        Mobile_Number = findViewById(R.id.Etphone);
        Country = findViewById(R.id.EtCountry);
        Signup = findViewById(R.id.BtnSignup);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String re_password = Re_Password.getText().toString();
                String mobile_number = Mobile_Number.getText().toString();
                int mobile_number1 = Integer.parseInt(mobile_number);
                String country = Country.getText().toString();
                validateEmail(email);
                validatePassword(password);

                if (name.equals("") || re_password.equals("") || country.equals("") || mobile_number.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                if (password.equals(re_password)) {
                    Boolean chkemail = db.checkEmail(email);
                    if (chkemail == true) {
                        Boolean insert = db.insert(email, password, name, mobile_number1, country);
                        if (insert == true) {
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            openHomePage();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void openHomePage() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }

    private Boolean validateEmail(String email) {
        email = Email.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("Please Enter your Email Id");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please Enter a valid Email Id");
            return false;
        } else {
            Email.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(String password) {
        password = Password.getText().toString().trim();

        if (password.isEmpty()) {
            Password.setError("Please Enter your Password");
            return false;
        } else {
            Password.setError(null);
            return true;
        }

    }
}
