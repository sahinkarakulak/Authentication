package com.mrcaracal.auth1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterScreen extends AppCompatActivity {

    TextView txt_go_to_login_screen;
    EditText edt_create_mail, edt_create_pass1, edt_create_pass2;
    Button btn_save;

    private FirebaseAuth firebaseAuth;

    boolean doubleBackToExitPressedOnce = false;

    private void init() {
        txt_go_to_login_screen = findViewById(R.id.txt_go_to_login_screen);
        edt_create_mail = findViewById(R.id.edt_create_mail);
        edt_create_pass1 = findViewById(R.id.edt_create_pass1);
        edt_create_pass2 = findViewById(R.id.edt_create_pass2);
        btn_save = findViewById(R.id.btn_save);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        txt_go_to_login_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_login_screen();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void go_to_login_screen() {
        Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
        startActivity(intent);
    }

    private void save() {

        String email = edt_create_mail.getText().toString();
        String password1 = edt_create_pass1.getText().toString();
        String password2 = edt_create_pass2.getText().toString();
        
        if (email.equals("") || password1.equals("") || password2.equals("")){
            Toast.makeText(this, "Fill in the required fields.", Toast.LENGTH_SHORT).show();
        }else {
            if (password1.equals(password2)){
                firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegisterScreen.this, "User Created!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterScreen.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterScreen.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}