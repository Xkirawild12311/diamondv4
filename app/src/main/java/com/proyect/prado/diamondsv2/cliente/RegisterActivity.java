package com.proyect.prado.diamondsv2.cliente;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.proyect.prado.diamondsv2.MenuActivity;
import com.proyect.prado.diamondsv2.pojo.Cliente;
import com.proyect.prado.diamondsv2.R;
import com.proyect.prado.diamondsv2.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, last_name, dni, email, password;
    private Button btnRegistrar;

    RelativeLayout relativeLayout;

    private String URL_REGISTRAR = "http://www.carseys.com/api/Api.php?apicall=createcliente";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name        = (EditText)findViewById(R.id.txtName);
        last_name   = (EditText)findViewById(R.id.txtLastName);
        dni         = (EditText)findViewById(R.id.txtDni);
        email       = (EditText)findViewById(R.id.txtEmail);
        password    = (EditText)findViewById(R.id.txtPassword);

        relativeLayout = (RelativeLayout)findViewById(R.id.RegisterActivity);

        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
            }
        });


    }
    public void Registrar(){

        //RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTRAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Cliente cliente = new Cliente();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String estado = jsonObject.get("error").toString();

                    if (!estado.equalsIgnoreCase("false")){

                        Snackbar snackbar = Snackbar.make(relativeLayout,"Registro Fallido",Snackbar.LENGTH_LONG);
                        snackbar.show();

                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(getResources().getColor(R.color.colorDangerSnacBar));
                    }else{

                        Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            },
            new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"Registro Fallido"+error.toString(),Toast.LENGTH_LONG).show();
                Log.i("tagconvertstr", "["+error+"]");

                }
            })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("name", name.getText().toString().trim());
                params.put("last_name", last_name.getText().toString().trim());
                params.put("dni",dni.getText().toString().trim());
                params.put("email",email.getText().toString().trim());
                params.put("password",password.getText().toString().trim());
                return params;
            }
        };


        //requestQueue.add(stringRequest);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
