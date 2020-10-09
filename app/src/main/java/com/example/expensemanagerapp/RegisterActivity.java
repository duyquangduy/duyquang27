package com.example.expensemanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtemailres, edtpassreg;
    Button btnReg;
    TextView txtsignin;

    private ProgressDialog dialog;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);

        initView();
    }

    private void initView() {
        edtemailres = findViewById(R.id.edtemail_reg);
        edtpassreg = findViewById(R.id.edtpassword_reg);
        btnReg = findViewById(R.id.btn_reg);
        txtsignin = findViewById(R.id.txtsignin_reg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemailres.getText().toString().trim();
                String pass = edtpassreg.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    edtemailres.setError("Email required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    edtpassreg.setError("Password required");
                    return;
                }
                dialog.setMessage("Processing...");
                dialog.show();
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            dialog.dismiss();
                            Log.d("AAA",task.toString());
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}
