package com.example.eric.hw5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends Activity implements View.OnClickListener {
    private EditText editTextHmTitle, editTextHmAuthor, editTextHmCondition, editTextHmBorrowedBy;
    private Button buttonHmCheck, buttonHmAdd;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        editTextHmTitle = findViewById(R.id.editTextHmTitle);
        editTextHmAuthor = findViewById(R.id.editTextHmAuthor);
        editTextHmCondition = findViewById(R.id.editTextHmCondition);
        editTextHmBorrowedBy = findViewById(R.id.editTextHmBorrowedBy);
        buttonHmCheck = findViewById(R.id.buttonHmCheck);
        buttonHmAdd = findViewById(R.id.buttonHmAdd);

        buttonHmCheck.setOnClickListener(this);
        buttonHmAdd.setOnClickListener(this);
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
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                return true;
            case R.id.menuUpdateBorrower:
                startActivity(new Intent(HomeActivity.this, BorrowerActivity.class));
                return true;
            case R.id.menuDeleteBook:
                startActivity(new Intent(HomeActivity.this, DeleteActivity.class));
                return true;
            case R.id.menuSignOut:
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                return true;
            default:

                return false;

        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonHmCheck) {

        }
        else if (v == buttonHmAdd) {
            Book newBook = new Book(editTextHmTitle.getText().toString().toUpperCase(), editTextHmAuthor.getText().toString().toUpperCase(), editTextHmCondition.getText().toString().toUpperCase(), editTextHmBorrowedBy.getText().toString().toUpperCase());
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            String user = mAuth.getCurrentUser().getEmail().toString().toUpperCase();
            DatabaseReference myRef = database.getReference(user);
            myRef.setValue(newBook);

        }

    }
}
