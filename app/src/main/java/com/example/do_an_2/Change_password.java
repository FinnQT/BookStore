package com.example.do_an_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Change_password extends AppCompatActivity {
    
    EditText editText_changePassword;
    EditText editText_oldPassWord;
    EditText editText_newPassword;
    Button btn_changeNewPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        editText_changePassword=findViewById(R.id.edt_newpassword);

        btn_changeNewPassword=findViewById(R.id.btn_comfirm_change_password);
        progressDialog= new ProgressDialog(this);
        btn_changeNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangePassword();

            }
        });

    }

    private void onClickChangePassword() {

        String newpassword= editText_changePassword.getText().toString().trim();
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newpassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(Change_password.this, "Change Password Success", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Change_password.this, "showdilog", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // sử dụng hàm này nếu muốn xác thực lại người dùng thàng công

    private void reAuthenticate(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }
}