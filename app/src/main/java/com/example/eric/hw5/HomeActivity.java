package com.example.eric.hw5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends Activity implements View.OnClickListener {
    private EditText editTextHmTitle, editTextHmAuthor, editTextHmCondition, editTextHmBorrowedBy;
    private Button buttonHmCheck, buttonHmAdd;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Books");


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
            if (editTextHmTitle.getText().toString() != "") {

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        if (dataSnapshot.child(mAuth.getUid()).child(editTextHmTitle.getText().toString().toUpperCase()).exists()) {
                            Toast.makeText(HomeActivity.this, "Book Found, updating values", Toast.LENGTH_SHORT).show();
                            Book tempBook = dataSnapshot.child(mAuth.getUid()).child(editTextHmTitle.getText().toString().toUpperCase()).getValue(Book.class);
                            editTextHmAuthor.setText(tempBook.Author);
                            editTextHmBorrowedBy.setText(tempBook.BorrowedBy);
                            editTextHmCondition.setText(tempBook.Condition);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });

            }
            else {
                Toast.makeText(HomeActivity.this, "Please Enter Book Title to Check", Toast.LENGTH_SHORT).show();
            }
        }
        else if (v == buttonHmAdd) {
            Book newBook = new Book(editTextHmTitle.getText().toString().toUpperCase(), editTextHmAuthor.getText().toString().toUpperCase(), editTextHmCondition.getText().toString().toUpperCase(), editTextHmBorrowedBy.getText().toString().toUpperCase());

            String key = mAuth.getUid();
            myRef.child(key).child(editTextHmTitle.getText().toString().toUpperCase()).setValue(newBook);

            Toast.makeText(HomeActivity.this, "Book Added.", Toast.LENGTH_SHORT).show();

            editTextHmTitle.setText("");
            editTextHmAuthor.setText("");
            editTextHmCondition.setText("");
            editTextHmBorrowedBy.setText("");
        }

    }
}
