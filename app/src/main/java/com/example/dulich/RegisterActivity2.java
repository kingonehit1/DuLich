package com.example.dulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity2 extends AppCompatActivity {
    EditText mEmailEdt,mPassWordEdt;
    Button mRegister;
    TextView notHaveAcctTV;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tao tai khoan");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //Anh xa
        mEmailEdt = findViewById(R.id.emailET);
        mPassWordEdt = findViewById(R.id.passwordET);
        notHaveAcctTV = findViewById(R.id.nothave_accountTV);
        mRegister = findViewById(R.id.btn2_register);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Dang ky User...");
        //Su kien clicjk

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nhap email
                String email = mEmailEdt.getText().toString().trim();
                String password = mPassWordEdt.getText().toString().trim();
                //rang buoc
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //set loi
                    mEmailEdt.setError("Sai định dạng gmail");
                    mEmailEdt.setFocusable(true);
                }
                else if(password.length()<6){
                    //set pass wrorld
                    mPassWordEdt.setError("Mật khẩu tối đa 6 chữ số");
                    mPassWordEdt.setFocusable(true);
                }
                else {
                    registerUser(email,password);
                }

            }
        });
         notHaveAcctTV.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(RegisterActivity2.this,LoginActivity.class));
             }
         });
    }

    private void registerUser(String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Get user email add uid from auth
                            String email = user.getEmail();
                            String uid = user.getUid();

                            //using hashmap
                            HashMap<Object, String> hashMap = new HashMap<>();
                            //put info
                            hashMap.put("Email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("name","");//will add late (e.g edit profile)
                            hashMap.put("phone","");
                            hashMap.put("image","");
                            //forebase database istance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //path to store user date name"Users"
                            DatabaseReference reference = database.getReference("Users");
                            //put data within hashmap in database
                            reference.child(uid).setValue(hashMap);


                            Toast.makeText(RegisterActivity2.this,"Resgister...\n"+user.getEmail(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity2.this, DashboardActivity.class));
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity2.this,"Tên tài khoảng đã tồn tại",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Show loi
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity2.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}