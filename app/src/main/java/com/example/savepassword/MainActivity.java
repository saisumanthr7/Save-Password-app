package com.example.savepassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
//    private static final Pattern PASSWORD_PATTERN =
//            Pattern.compile("^" +
//                    "(?=.*[0-9])" +
//                    "(?=.*[a-z])" +
//                    "(?=\\S+$)" +
//                    ".{6,}" +
//                    "$");


    DatabaseHelper db;
    private EditText Email;
    private EditText Password;
    private Button Login;
    private Button Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);

        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);
        Login = findViewById(R.id.btnlogin);
        Signup = findViewById(R.id.btnsignup);

        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                validateEmail(email);
                validatePassword(password);
                Boolean chklogindetails = db.checkemailpassword(email, password);

                if(chklogindetails == true){
                    Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                    openHomePage();
                }else{
                    Toast.makeText(getApplicationContext(),"Wrong Email or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupForm();
            }
        });
    }

    public void openHomePage() {
        Intent i = new Intent(this, Homepage.class);
        startActivity(i);
    }

    public void openSignupForm() {
        Intent i = new Intent(this, Signup_form.class);
        startActivity(i);
    }


    private Boolean validateEmail(String email) {
        email = Email.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("Please Enter your Email Id");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please Enter a valid Email Id");
            return false;
        } else {
            Email.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(String password){
        password = Password.getText().toString().trim();

        if(password.isEmpty()){
            Password.setError("Please Enter your Password");
            return false;
        }else {
            Password.setError(null);
            return true;
        }
    }

}
