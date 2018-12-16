package com.proyect.prado.diamondsv2;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.proyect.prado.diamondsv2.adapter.CatalogoAdapter;
import com.proyect.prado.diamondsv2.pojo.Catalogo;
import com.proyect.prado.diamondsv2.pojo.Cliente;
import com.proyect.prado.diamondsv2.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    private RecyclerView recyclerFragmentHome;
    ArrayList<Catalogo>listaCatalogo;
    public CatalogoAdapter catalogoAdapter;
    JsonObjectRequest jsonObjectRequest;

    LinearLayout linearLayoutCatalogo;


    private String URL_CATALOGO = "http://www.carseys.com/api/Api.php?apicall=readcatalogo";


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerFragmentHome = vista.findViewById(R.id.recyclerFragmentHome);
        llenarlista();

        recyclerFragmentHome.setLayoutManager(new LinearLayoutManager(getContext()));

        listaCatalogo = new ArrayList<>();
        inicializarAdaptador();
        return vista;
    }

    private void llenarlista() {



        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,URL_CATALOGO,null,this,this);
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void inicializarAdaptador(){
        catalogoAdapter = new CatalogoAdapter(listaCatalogo,getActivity(),getContext());
        recyclerFragmentHome.setAdapter(catalogoAdapter);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Snackbar snackbar = Snackbar.make(linearLayoutCatalogo,"Error"+error,Snackbar.LENGTH_LONG);
        snackbar.show();

        View snackView = snackbar.getView();
        snackView.setBackgroundColor(getResources().getColor(R.color.colorDangerSnacBar));

    }

    @Override
    public void onResponse(JSONObject response) {
        Catalogo catalogo=null;

        JSONArray json=response.optJSONArray("contenido");  //mediante un JSON, obtenemos los datos de manera estructurada

        try { //Un try catch para evitar o saltarnos BUGS

            for (int i=0;i<json.length();i++){//Recorremos un FOR para obtener los datos de la base de datos, este
                catalogo=new Catalogo();      //for se recorrerá mientras tengamos información dentro de nuestra BD
                JSONObject jsonObject=null;     //Y las irá añadiendo en forma de "contactos.add"
                jsonObject=json.getJSONObject(i);

                catalogo.setTitulo(jsonObject.optString("titulo"));
                catalogo.setDetalle(jsonObject.optString("detalle"));
                catalogo.setImagen(jsonObject.optString("imagen"));

                listaCatalogo.add(catalogo);
            }
            CatalogoAdapter adapter=new CatalogoAdapter(listaCatalogo,getActivity(),getContext());
            recyclerFragmentHome.setAdapter(adapter);

        } catch (JSONException e) { //Un try catch para evitar o saltarnos BUGS
            Snackbar snackbar = Snackbar.make(linearLayoutCatalogo,"No se a podido conectar con el Servidor",Snackbar.LENGTH_LONG);
            snackbar.show();

            View snackView = snackbar.getView();
            snackView.setBackgroundColor(getResources().getColor(R.color.colorDangerSnacBar));
        }
    }
}
