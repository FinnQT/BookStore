package com.example.do_an_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    ProgressDialog progressDialog;

    private EditText edt_email;
    private EditText edt_password;
    private Button Login;
    private TextView fogot_pass;
    private TextView want_sigh_up;
    private Button btn_loginwithAmin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        edt_email=findViewById(R.id.editTextEmail);
        edt_password=findViewById(R.id.editTextPassword);
        Login=findViewById(R.id.cirLoginButton);
        fogot_pass=findViewById(R.id.tv_fotgot);
        want_sigh_up=findViewById(R.id.want_signup);
        btn_loginwithAmin=findViewById(R.id.btn_loginWithAmin);
        progressDialog= new ProgressDialog(this);

        want_sigh_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(login.this,register.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnclickLogin();
            }
        });
        fogot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlickFogotPassword();
            }
        });
        btn_loginwithAmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginWithAdmin();
            }
        });

    }

    private void LoginWithAdmin() {
        Intent intent= new Intent(login.this,Admin_Manager.class);
        startActivity(intent);
        finishAffinity();
    }

    private void OnlickFogotPassword() {
        Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.forgot_send_email);
        EditText emailFogot= dialog.findViewById(R.id.email_forgot);
        Button buttonsend= dialog.findViewById(R.id.btn_send_email);
        dialog.show();
        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = emailFogot.getText().toString().trim();
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(login.this, "Email sent", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });
            }
        });
    }

    private void OnclickLogin() {

        String email=edt_email.getText().toString().trim();
        String password=edt_password.getText().toString().trim();
        if(email.equals("")||password.equals("")){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else{
        progressDialog.show();
        FirebaseAuth auth= FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                           Intent intent= new Intent(login.this,MainActivity.class);
                           startActivity(intent);
                           finishAffinity();
                        } else {
                            Toast.makeText(login.this, "Sai Mật Khẩu Hoặc Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });}
    }

}