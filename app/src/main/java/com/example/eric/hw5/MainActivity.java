package com.example.eric.hw5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button buttonLiSi, buttonLiRg;
    private EditText editTextLiId, editTextLiPw;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        buttonLiSi = findViewById(R.id.buttonLiSi);
        buttonLiRg = findViewById(R.id.buttonLiRg);

        editTextLiId = findViewById(R.id.editTextLiId);
        editTextLiPw = findViewById(R.id.editTextLiPw);

        buttonLiSi.setOnClickListener(this);
        buttonLiRg.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater optionsMenuInflater = getMenuInflater();
        optionsMenuInflater.inflate(R.menu.dropdown_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuUpdateCondition:
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                return true;
            case R.id.menuUpdateBorrower:
                startActivity(new Intent(MainActivity.this, BorrowerActivity.class));
                return true;
            case R.id.menuDeleteBook:
                startActivity(new Intent(MainActivity.this, DeleteActivity.class));
                return true;
            case R.id.menuSignOut:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            default:

                return false;

        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLiSi) {
            loginUser(editTextLiId.getText().toString(), editTextLiPw.getText().toString());
        }
        if (v == buttonLiRg) {
            registerUser(editTextLiId.getText().toString(), editTextLiId.getText().toString());
        }
    }

    private void loginUser(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication Success.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                            // identify their account and update UI accordingly
                            Intent i = new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void registerUser(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Registration Success.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
