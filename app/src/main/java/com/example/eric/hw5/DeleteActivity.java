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

public class DeleteActivity extends Activity implements View.OnClickListener {
    private EditText editTextDlTitle, editTextDlAuthor, editTextDlBorrowedBy;
    private Button buttonDlDelete;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Books");


        editTextDlTitle = findViewById(R.id.editTextDlTitle);
        editTextDlAuthor = findViewById(R.id.editTextDlAuthor);
        editTextDlBorrowedBy = findViewById(R.id.editTextDlBorrowedBy);

        buttonDlDelete = findViewById(R.id.buttonDlDelete);
        buttonDlDelete.setOnClickListener(this);
    }

    /*
        I was able to ensure that Users cannot delete books that do not belong to them by structuring the data so that only the user who input them can access them in the first place.
        By structuring the data under mAuth.getUid(), each user's unique ID is used to access the books that exist for each user. This ensures that you have private information
        for the books you own. Each data reference uses getUid() which is hidden from the user and ensures that they cannot manipulate data that does not belong to them.
     */

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
                startActivity(new Intent(DeleteActivity.this, ConditionActivity.class));
                return true;
            case R.id.menuUpdateBorrower:
                startActivity(new Intent(DeleteActivity.this, BorrowerActivity.class));
                return true;
            case R.id.menuDeleteBook:
                startActivity(new Intent(DeleteActivity.this, DeleteActivity.class));
                return true;
            case R.id.menuSignOut:
                startActivity(new Intent(DeleteActivity.this, MainActivity.class));
                return true;
            case R.id.menuCheckBook:
                startActivity(new Intent(DeleteActivity.this, CheckActivity.class));
                return true;
            case R.id.menuAddBook:
                startActivity(new Intent(DeleteActivity.this, HomeActivity.class));
                return true;
            default:
                return false;

        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonDlDelete) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (dataSnapshot.child(mAuth.getUid()).child(editTextDlTitle.getText().toString().toUpperCase()).exists()) {
                        Book tempBook = dataSnapshot.child(mAuth.getUid()).child(editTextDlTitle.getText().toString().toUpperCase()).getValue(Book.class);
                        if (tempBook.Owner.equals(mAuth.getUid())) {
                            if (tempBook.Author.equals(editTextDlAuthor.getText().toString().toUpperCase()) & tempBook.BorrowedBy.equals(editTextDlBorrowedBy.getText().toString().toUpperCase())) {

                                Toast.makeText(DeleteActivity.this, "Book Found, deleting book from database", Toast.LENGTH_SHORT).show();
                                dataSnapshot.child(mAuth.getUid()).child(editTextDlTitle.getText().toString().toUpperCase()).getRef().removeValue();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        }

    }
}
