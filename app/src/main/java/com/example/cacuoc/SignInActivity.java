package com.example.cacuoc;

import android.content.Intent;
import android.media.MediaPlayer;
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

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvNotAccountYet;
    private Button btnSignIn;
    MediaPlayer mediaPlayer;

    private Map<String, String> userAccounts;

    private final  String REQUIRE = "Require";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etConfirmPassword);
        tvNotAccountYet = findViewById(R.id.tvNotAccountYet);
        btnSignIn = findViewById(R.id.btnSignIn);

        tvNotAccountYet.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        userAccounts = new HashMap<>();
        userAccounts.put("HaoHT", "123");
        userAccounts.put("DuyNM", "123");
        userAccounts.put("KhanhNCD", "123");
        userAccounts.put("DuyLD", "123");
        userAccounts.put("CongCV", "123");
        userAccounts.put("LamTT", "123");
    }

    private boolean checkInput(){
        if (TextUtils.isEmpty(etUsername.getText().toString())){
            etUsername.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())){
            etPassword.setError(REQUIRE);
            return false;
        }
        return true;
    }

    private void signIn(){
        if (!checkInput()){
            return;
        }

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (userAccounts.containsKey(username) && userAccounts.get(username).equals(password)) {
            Intent intent = new Intent(this, BetActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUpForm(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int viewId;
        if(v.getId() == R.id.btnSignIn)
            viewId = 1;
        else
            viewId = 2;
        switch (viewId) {
            case 1:
                signIn();
                break;
            case 2:
                signUpForm();
                break;
        }
    }
}