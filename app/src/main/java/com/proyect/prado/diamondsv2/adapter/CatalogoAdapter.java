package com.proyect.prado.diamondsv2.adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.proyect.prado.diamondsv2.R;
import com.proyect.prado.diamondsv2.pojo.Catalogo;
import com.proyect.prado.diamondsv2.volley.VolleySingleton;

import java.io.IOException;
import java.util.ArrayList;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.CatalogoViewHolder>{

    ArrayList<Catalogo>catalogos;
    Activity activity;
    Context context;

    View view;
    public CatalogoAdapter (ArrayList<Catalogo>catalogo, Activity activity, Context context){
        this.catalogos = catalogo;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public CatalogoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_servicios_item,parent,false);
        return new CatalogoViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull CatalogoViewHolder holder, int position) {

        final Catalogo catalogo = catalogos.get(position);
        holder.txt_titulo_catalogo.setText(catalogo.getTitulo());
        holder.txt_detalle_catalogo.setText(catalogo.getDetalle());

        if (catalogos.get(position).getImagen()!=null){
            //
            cargarImagenWebService(catalogos.get(position).getImagen(),holder);
        }else{
            holder.img_catalogo.setImageResource(R.drawable.card2);

        }


    }



    @Override
    public int getItemCount() {
        return catalogos.size();
    }

    public class CatalogoViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_catalogo;
        private TextView txt_titulo_catalogo, txt_detalle_catalogo;


        public CatalogoViewHolder(View itemView) {

            super(itemView);
            img_catalogo = itemView.findViewById(R.id.img_Catalogo);
            txt_titulo_catalogo = itemView.findViewById(R.id.txt_titulo_catalogo);
            txt_detalle_catalogo = itemView.findViewById(R.id.txt_detalle_catalogo);
        }
    }

    private void cargarImagenWebService(String rutaImagen, final CatalogoViewHolder holder) {

        String urlImagen="http://www.carseys.com/imagenes3/"+rutaImagen; //Declaramos la URL donde se encuentran las imagenes
        urlImagen=urlImagen.replace(" ","%20");


        try {
            ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    holder.img_catalogo.setImageBitmap(response);
                }

            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,

                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,"Error, Faltan cargar algunas imagenes",Toast.LENGTH_LONG).show();
                        }
                        //Si encuentra error al cargar una imagen nos monstrará un error

                    });

            VolleySingleton.getInstanciaVolley(context).addToRequestQueue(imageRequest);
        }catch (Exception e){

            Toast.makeText(context,"Error, Faltan cargar algunas imagenes",Toast.LENGTH_LONG).show();
        }


    }


}
