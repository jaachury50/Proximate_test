package com.example.proximate_test.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.proximate_test.Interfaces.InterfaceDetailsProduct;
import com.example.proximate_test.Models.ModelProducts;
import com.example.proximate_test.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ModelProducts> modelProducts;
    private InterfaceDetailsProduct interfaceDetailsProduct;


    public ProductsAdapter(Context context, ArrayList<ModelProducts> modelProducts, InterfaceDetailsProduct interfaceDetailsProduct) {
        this.context = context;
        this.modelProducts = modelProducts;
        this.interfaceDetailsProduct = interfaceDetailsProduct;
    }



    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        holder.titulo.setText(modelProducts.get(position).getTitle());
        holder.descripcion.setText(modelProducts.get(position).getShortDescription());

        Picasso.get().load(modelProducts.get(position).getImage())
                .placeholder(R.drawable.imagen_no_disponible)
                .error(R.drawable.imagen_no_disponible)
                .into(holder.imagen);

        String title = modelProducts.get(position).getTitle();
        String url_image = modelProducts.get(position).getImage();
        String long_descripcion = modelProducts.get(position).getLongDescription();
        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceDetailsProduct.SendData(title,url_image,long_descripcion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo,descripcion;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.title_product_list);
            descripcion = itemView.findViewById(R.id.short_description_list);
            imagen = itemView.findViewById(R.id.image_product_list);
        }
    }
}
