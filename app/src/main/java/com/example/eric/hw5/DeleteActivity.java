package com.example.eric.hw5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DeleteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
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
                startActivity(new Intent(DeleteActivity.this, HomeActivity.class));
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
            default:

                return false;

        }
    }
}
