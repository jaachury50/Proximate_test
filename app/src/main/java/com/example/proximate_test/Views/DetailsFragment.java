package com.example.proximate_test.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proximate_test.R;
import com.squareup.picasso.Picasso;


public class DetailsFragment extends Fragment {

    View view;
    TextView Tvtitle,Tvdescripcion;
    ImageView Imimage;
    String title,descripcion,imageurl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle RescueInfo = getArguments();
        title =RescueInfo.getString("title");
        descripcion =RescueInfo.getString("longDescripcion");
        imageurl =RescueInfo.getString("image");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);
        Tvtitle = view.findViewById(R.id.title_product_detail);
        Tvdescripcion = view.findViewById(R.id.long_descripcion_detail);
        Imimage = view.findViewById(R.id.image_product_detail);

        Tvtitle.setText(title);
        Tvdescripcion.setText(descripcion);
        Picasso.get().load(imageurl)
                .placeholder(R.drawable.imagen_no_disponible)
                .error(R.drawable.imagen_no_disponible)
                .into(Imimage);
        return view;
    }
}