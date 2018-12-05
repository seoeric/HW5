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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BorrowerActivity extends Activity implements View.OnClickListener {
    private EditText editTextBrBorrowedBy, editTextBrTitle, editTextBrAuthor, editTextBrCondition;
    private Button buttonBrLookUp;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private boolean isFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Books");

        editTextBrBorrowedBy = findViewById(R.id.editTextBrBorrowedBy);
        editTextBrTitle = findViewById(R.id.editTextBrTitle);
        editTextBrAuthor = findViewById(R.id.editTextBrAuthor);
        editTextBrCondition = findViewById(R.id.editTextBrCondition);

        buttonBrLookUp = findViewById(R.id.buttonBrLookUp);
        buttonBrLookUp.setOnClickListener(this);
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
                startActivity(new Intent(BorrowerActivity.this, ConditionActivity.class));
                return true;
            case R.id.menuUpdateBorrower:
                startActivity(new Intent(BorrowerActivity.this, BorrowerActivity.class));
                return true;
            case R.id.menuDeleteBook:
                startActivity(new Intent(BorrowerActivity.this, DeleteActivity.class));
                return true;
            case R.id.menuSignOut:
                startActivity(new Intent(BorrowerActivity.this, MainActivity.class));
                return true;
            case R.id.menuCheckBook:
                startActivity(new Intent(BorrowerActivity.this, CheckActivity.class));
                return true;
            case R.id.menuAddBook:
                startActivity(new Intent(BorrowerActivity.this, HomeActivity.class));
                return true;
            default:
                return false;

        }
    }

    @Override
    public void onClick(View v) {
        isFound = false;
        if (v == buttonBrLookUp) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot.getChildren()) {

                            Book tempBookBr = snapshot2.getValue(Book.class);
                            if (tempBookBr.BorrowedBy.equals(editTextBrBorrowedBy.getText().toString().toUpperCase())) {
                                System.out.println("match found");
                                editTextBrTitle.setText(tempBookBr.Title);
                                editTextBrAuthor.setText(tempBookBr.Author);
                                editTextBrCondition.setText(tempBookBr.Condition);
                                isFound = true;
                                Toast.makeText(BorrowerActivity.this, "Book Found!", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println("no match");
                            }
                        }
                    }
                    if (isFound = false) {
                        Toast.makeText(BorrowerActivity.this, "Book not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(BorrowerActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    // Failed to read value
                }
            });
        }

    }
}
