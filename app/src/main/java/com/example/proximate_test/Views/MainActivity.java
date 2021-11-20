package com.example.proximate_test.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.proximate_test.DataBase.DBHelper;
import com.example.proximate_test.Models.ModelResponse;
import com.example.proximate_test.R;
import com.example.proximate_test.Volley.SingletonVolley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText EdUser, EdPassword;
    Button BtnLogin;
    ProgressBar progressBar;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EdUser = findViewById(R.id.input_user);
        EdPassword = findViewById(R.id.input_password);
        BtnLogin = findViewById(R.id.login);
        progressBar = findViewById(R.id.progress);
        dbHelper = new DBHelper(this);

        RecoverData();

        if (RecoverData() > 0){
            Toast.makeText(MainActivity.this,"Bienvenido",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,ProductsActivity.class);
            startActivity(intent);
        }

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EdUser.getText().toString().isEmpty() && !EdPassword.getText().toString().isEmpty()){
                    Login(EdUser.getText().toString(),EdPassword.getText().toString());
                }else {
                    Toast.makeText(MainActivity.this,"Debe diligenciar todos los datos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void Login(String user, String password) {
        final ProgressDialog loading = ProgressDialog.show(this,"Iniciando Sesión...","Espere por favor...",false,false);
        String url = "https://serveless.proximateapps-services.com.mx/proximatetools/dev/webadmin/testproximate/login";
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("user",user);
            parametros.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    ModelResponse estado = gson.fromJson(response.toString(), ModelResponse.class);
                    if (estado.status) {
                        Toast.makeText(MainActivity.this, "Ingreso Exitoso", Toast.LENGTH_SHORT).show();
                        InsertSQL(estado);
                        Intent intent = new Intent(MainActivity.this,ProductsActivity.class);
                        startActivity(intent);
                        loading.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                } catch (JSONException e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Compruebe su conexión a internet", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
        SingletonVolley.getInstance(this).addToRequestQueue(postRequest);
    }

    private void InsertSQL(ModelResponse model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.USERS_TABLE_COLUMN_STATUS, model.status);
        contentValues.put(dbHelper.USERS_TABLE_COLUMN_DATA, model.data);
        dbHelper.InsertUsers(contentValues);
    }

    private int RecoverData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(dbHelper.TABLE_USERS,
                getIntent().getStringArrayExtra(dbHelper.USERS_TABLE_COLUMN_DATA),
                null,
                null,
                null,
                null,
                null);
        return cursor.getCount();
    }
}