package com.example.databasefirebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button googleBtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    int success_code = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        googleBtn = findViewById(R.id.buttonLogin);
        initGoogleAuthLogIn();
    }

    private void performLogin() {
        // Get the entered username and password
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        // Perform authentication (you can replace this with your own logic)
        if (isValidCredentials(username, password)) {
            // Successful login, you can navigate to the next activity or perform other actions
            // For example, you can start a new activity:
             Intent intent = new Intent(this, HomeActivity.class);
             startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidCredentials(String username, String password) {
        // Replace this with your own authentication logic
        // For example, you can check against a hardcoded username and password
        return username.equals("a") && password.equals("a");
    }
    private void initGoogleAuthLogIn(){
        googleBtn = findViewById(R.id.buttonLogin);
        // init google objects for sign in via google account
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    private void SignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, success_code);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // trying to make a request for google sign in
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == success_code){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                // call for navigate for menu activity
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Google SignIn failed", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void navigateToSecondActivity() {
        finish();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

}