package com.example.do_an_2;



import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class edit_profile extends AppCompatActivity {


    public static final int MY_REQUEST_CODE=10;
    ProgressDialog progressDialog;
    ImageView imageView_update;
    EditText edt_update_name;
    EditText edt_update_moblie;
    EditText edt_update_mail;
    EditText edt_update_address;
    Button btn_update_profile;
    Button btn_update_email_info;
    Uri muri;
    private ActivityResultLauncher<Intent> mActivityResultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode()==RESULT_OK)
                    {
                        Intent intent= result.getData();
                        if(intent==null)
                        {
                            return;
                        }
                        Uri uri=intent.getData();
                        muri=uri;
                        try {
                            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            setBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_ifo);

        imageView_update=findViewById(R.id.update_image_user);
        edt_update_name=findViewById(R.id.update_name_info);
        edt_update_mail=findViewById(R.id.update_email_info);
        btn_update_profile=findViewById(R.id.btn_update_info);
        btn_update_email_info=findViewById(R.id.btn_Update_email);
        progressDialog= new ProgressDialog(this);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl_update = user.getPhotoUrl();
        edt_update_name.setText(name);
        edt_update_mail.setText(email);
        Glide.with(this).load(photoUrl_update).error(R.drawable.ic_no_image).centerCrop().into(imageView_update);
        //chon anh
        imageView_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
            }
        });


        btn_update_email_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnclickUpdateEmail();
            }
        });
    }

    private void onClickRequestPermission() {
        if(this==null)
        {
            return;
        }

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
        {
            openGallary();
            return;
        }
        if(this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        {
            openGallary();
        }else{

            String[] permisstions={Manifest.permission.READ_EXTERNAL_STORAGE};
            this.requestPermissions(permisstions,MY_REQUEST_CODE);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_REQUEST_CODE)
        {
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                openGallary();
            }else{
                Toast.makeText(this, "vui long cho phep truy cap", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openGallary() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select picture"));
    }



    public void setBitmap(Bitmap bitmapImg)
    {
        imageView_update.setImageBitmap(bitmapImg);
    }


    private void OnclickUpdateEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        progressDialog.show();

        String strEMail= edt_update_mail.getText().toString().trim();
        user.updateEmail(strEMail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(edit_profile.this, "Update email success", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(edit_profile.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else{
                        Toast.makeText(edit_profile.this, "no success", Toast.LENGTH_SHORT).show();}
                    }
                });
    }

    private void onClickUpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        progressDialog.show();
        String strFullname= edt_update_name.getText().toString().trim();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullname)
                .setPhotoUri(muri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(edit_profile.this, "Update profile success", Toast.LENGTH_LONG).show();
                            Intent intent;
                            intent = new Intent(edit_profile.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        }
                    }
                });

    }
}