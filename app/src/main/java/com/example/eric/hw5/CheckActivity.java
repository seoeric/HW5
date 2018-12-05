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

import org.w3c.dom.Text;

public class CheckActivity extends Activity implements View.OnClickListener {

    private TextView textViewChAuthor, textViewChBorrowedBy, textViewChCondition;
    private EditText editTextChTitle;
    private Button buttonChCheck;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Books");


        textViewChAuthor = findViewById(R.id.textViewChAuthor);
        textViewChBorrowedBy = findViewById(R.id.textViewChBorrowedBy);
        textViewChCondition = findViewById(R.id.textViewChCondition);
        editTextChTitle = findViewById(R.id.editTextChTitle);

        buttonChCheck = findViewById(R.id.buttonChCheck);
        buttonChCheck.setOnClickListener(this);
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
                startActivity(new Intent(CheckActivity.this, ConditionActivity.class));
                return true;
            case R.id.menuUpdateBorrower:
                startActivity(new Intent(CheckActivity.this, BorrowerActivity.class));
                return true;
            case R.id.menuDeleteBook:
                startActivity(new Intent(CheckActivity.this, DeleteActivity.class));
                return true;
            case R.id.menuSignOut:
                startActivity(new Intent(CheckActivity.this, MainActivity.class));
                return true;
            case R.id.menuCheckBook:
                startActivity(new Intent(CheckActivity.this, CheckActivity.class));
                return true;
            case R.id.menuAddBook:
                startActivity(new Intent(CheckActivity.this, CheckActivity.class));
                return true;
            default:
                return false;

        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChCheck) {
            DatabaseReference bookChkRef = database.getReference("Books").child(mAuth.getUid()).child(editTextChTitle.getText().toString().toUpperCase());
            bookChkRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Book tempBook = dataSnapshot.getValue(Book.class);
                    textViewChAuthor.setText(tempBook.Author);
                    textViewChCondition.setText(tempBook.Condition);
                    textViewChBorrowedBy.setText(tempBook.BorrowedBy);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(CheckActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    // Failed to read value
                }
            });

        }

    }
}
