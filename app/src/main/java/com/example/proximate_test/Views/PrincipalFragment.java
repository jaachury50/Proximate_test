package com.example.proximate_test.Views;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.proximate_test.Adapters.ProductsAdapter;
import com.example.proximate_test.DataBase.DBHelper;
import com.example.proximate_test.Interfaces.InterfaceDetailsProduct;
import com.example.proximate_test.Models.ModelProducts;
import com.example.proximate_test.Models.ModelResponse;
import com.example.proximate_test.R;
import com.example.proximate_test.Volley.SingletonVolley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PrincipalFragment extends Fragment implements InterfaceDetailsProduct {

    View view;
    ArrayList<ModelProducts> modelProducts;
    RecyclerView recyclerViewProducts;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_principal, container, false);

        dbHelper = new DBHelper(getContext());
        modelProducts = new ArrayList<>();
        recyclerViewProducts = view.findViewById(R.id.Rvproducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        RecoverData();

        return view;
    }

    private void PeticionProductos(String token) {
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Cargando Productos...","Espere por favor...",false,false);
        String url = "https://serveless.proximateapps-services.com.mx/proximatetools/dev/webadmin/testproximate/getproducts";
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("userToken",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                    EjecutarServicio(response);

                    loading.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Compruebe su conexión a internet", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
        SingletonVolley.getInstance(getContext()).addToRequestQueue(postRequest);
    }

    private void EjecutarServicio(JSONObject response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response.toString());
            String responseData = String.valueOf(jsonObject.getString("data"));
            JSONObject jsonObject1 = new JSONObject(responseData);
            JSONArray jsonObjectProduct =  jsonObject1.getJSONArray("products");
            Log.v("RESPONSEOBJECT",jsonObjectProduct.toString());

            ModelProducts productsModel = null;
            Gson gson = new Gson();
            for (int i=0; i<jsonObjectProduct.length(); i++){
                productsModel = gson.fromJson(jsonObjectProduct.getJSONObject(i).toString(),new TypeToken<ModelProducts>(){}.getType());
                modelProducts.add(productsModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ProductsAdapter adapter = new ProductsAdapter(getContext(),modelProducts,this);
        recyclerViewProducts.setAdapter(adapter);
    }

    private void RecoverData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_USERS, null);
        while (cursor.moveToNext()) {
            String token = cursor.getString(2);
            Log.d("NOMBRETOKEN",token);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(token);
                String responseData = String.valueOf(jsonObject.getString("userToken"));
                PeticionProductos(responseData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




    }

    @Override
    public void SendData(String title, String image_url, String longDescripcion) {
        Bundle sendData = new Bundle();
        sendData.putString("title", title);
        sendData.putString("image", image_url);
        sendData.putString("longDescripcion",longDescripcion);
        Fragment fragmento = new DetailsFragment();
        fragmento.setArguments(sendData);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragmento);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}