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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ConditionActivity extends Activity implements View.OnClickListener {
    private Button buttonCnCheck, buttonCnUpdate;
    private EditText editTextCnTitle, editTextCnCondition, editTextCnBorrowedBy;
    private TextView textViewCnAuthor;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Books");

        buttonCnCheck = findViewById(R.id.buttonCnCheck);
        buttonCnCheck.setOnClickListener(this);

        buttonCnUpdate   = findViewById(R.id.buttonCnUpdate);
        buttonCnUpdate.setOnClickListener(this);

        editTextCnCondition = findViewById(R.id.editTextCnCondition);
        editTextCnTitle = findViewById(R.id.editTextCnTitle);

        textViewCnAuthor = findViewById(R.id.textViewCnAuthor);
        editTextCnBorrowedBy = findViewById(R.id.editTextCnBorrowedBy);

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
                startActivity(new Intent(ConditionActivity.this, ConditionActivity.class));
                return true;
            case R.id.menuUpdateBorrower:
                startActivity(new Intent(ConditionActivity.this, BorrowerActivity.class));
                return true;
            case R.id.menuDeleteBook:
                startActivity(new Intent(ConditionActivity.this, DeleteActivity.class));
                return true;
            case R.id.menuSignOut:
                startActivity(new Intent(ConditionActivity.this, MainActivity.class));
                return true;
            case R.id.menuCheckBook:
                startActivity(new Intent(ConditionActivity.this, CheckActivity.class));
            case R.id.menuAddBook:
                startActivity(new Intent(ConditionActivity.this, CheckActivity.class));
            default:
                return false;

        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonCnCheck) {
            DatabaseReference bookChkRef = database.getReference("Books").child(mAuth.getUid()).child(editTextCnTitle.getText().toString().toUpperCase());
            bookChkRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Book tempBook = dataSnapshot.getValue(Book.class);
                    editTextCnBorrowedBy.setText(tempBook.BorrowedBy);
                    textViewCnAuthor.setText(tempBook.Author);
                    editTextCnCondition.setText(tempBook.Condition);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(ConditionActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    // Failed to read value
                }
            });

        }

        else if (v == buttonCnUpdate) {
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Books").child(mAuth.getUid()).child(editTextCnTitle.getText().toString().toUpperCase());
            Map<String, Object> updates = new HashMap<String,Object>();

            updates.put("Condition", editTextCnCondition.getText().toString().toUpperCase());
            updates.put("BorrowedBy", editTextCnBorrowedBy.getText().toString().toUpperCase());

            ref.updateChildren(updates);


        }

    }
}
