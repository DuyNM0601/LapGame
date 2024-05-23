package com.example.cacuoc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsername;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btnSignUp;
    TextView tvAlreadyAcc;

    private final String REQUIRE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvAlreadyAcc = (TextView) findViewById(R.id.tvAlreadyAcc);

        btnSignUp.setOnClickListener(this);
        tvAlreadyAcc.setOnClickListener(this);
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(etUsername.getText().toString())){
            etUsername.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())){
            etPassword.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etConfirmPassword.getText().toString())){
            etConfirmPassword.setError(REQUIRE);
        }
        if (!TextUtils.equals(etPassword.getText().toString(), etConfirmPassword.getText().toString())){
            Toast.makeText(this, "Passwork are not match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void signUp(){
        if (!checkInput()){
            return;
        }
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void signInForm(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int viewId;
        if(v.getId() == R.id.btnSignUp)
            viewId = 1;
        else
            viewId = 2;
        switch (viewId) {
            case 1:
                signUp();
                break;
            case 2:
                signInForm();
                break;
        }
    }
}