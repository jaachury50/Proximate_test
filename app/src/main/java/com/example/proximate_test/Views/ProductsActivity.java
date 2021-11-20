package com.example.proximate_test.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.proximate_test.DataBase.DBHelper;
import com.example.proximate_test.R;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.ExecutionException;

public class ProductsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        NavigationView navigationView = findViewById(R.id.navDrawer);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(id == R.id.navigation_home){
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new PrincipalFragment()).commit();
            Toast.makeText(ProductsActivity.this,"home",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.sension_end){
            CloseSesion();
            Toast.makeText(ProductsActivity.this,"cerrar sesion",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void CloseSesion() {
        try {
            dbHelper = new DBHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(dbHelper.TABLE_USERS,null,null);
            db.close();
            finish();
        }catch (Exception e){
            Log.d("ERRORBORRAR", String.valueOf(e));
        }

    }
}