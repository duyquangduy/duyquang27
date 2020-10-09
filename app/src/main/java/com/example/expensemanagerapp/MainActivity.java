package com.example.expensemanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText edtemail, edtpass;
    private Button btnsignup;
    private TextView txtforgot_password, txtsignup_reg;

    private ProgressDialog dialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        dialog = new ProgressDialog(this);

        initView();
    }

    private void initView() {
        edtemail = findViewById(R.id.edtemail_login);
        edtpass = findViewById(R.id.edtpassword_login);
        btnsignup = findViewById(R.id.btn_login);
        txtforgot_password = findViewById(R.id.txtforget_password);
        txtsignup_reg = findViewById(R.id.txtsignup_reg);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemail.getText().toString().trim();
                String pass = edtpass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    edtemail.setError("Email required");
                    return;
                }if(TextUtils.isEmpty(pass)){
                    edtpass.setError("Password required");
                    return;
                }
                dialog.setMessage("Progressing");
                dialog.show();
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }else {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //registration activity
        txtsignup_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        //reset pass activity
        txtforgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ResetActivity.class));
            }
        });
    }
}
