package com.example.do_an_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class register extends AppCompatActivity {

    private EditText edt_email_register;
    private EditText edt_password_register;
    private EditText again_edt_password_register;
    private Button btn_Register;
    private TextView tv_have_account;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        edt_email_register=findViewById(R.id.email_register);
        edt_password_register=findViewById(R.id.pass_word_register);
        again_edt_password_register=findViewById(R.id.password_again);
        btn_Register=findViewById(R.id.btn_register);
        tv_have_account=findViewById(R.id.tvhave_account);
        progressDialog= new ProgressDialog(this);



        tv_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(register.this,login.class);
                startActivity(intent);
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickRegister();
            }
        });

    }

    private void onclickRegister() {

        String email=edt_email_register.getText().toString().trim();
        String password=edt_password_register.getText().toString().trim();
        String passwordAgain=again_edt_password_register.getText().toString().trim();
        if(email.equals("")||password.equals("")||passwordAgain.equals("")){
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else if(!passwordAgain.equals(password)){
            Toast.makeText(this, "Password Không trùng khớp", Toast.LENGTH_SHORT).show();
        }else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            progressDialog.show();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(register.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}