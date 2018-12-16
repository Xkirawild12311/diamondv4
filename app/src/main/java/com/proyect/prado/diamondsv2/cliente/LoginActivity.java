package com.proyect.prado.diamondsv2.cliente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.proyect.prado.diamondsv2.R;
import com.proyect.prado.diamondsv2.pojo.Cliente;
import com.proyect.prado.diamondsv2.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login, btn_signup;


    private String URL_LOGIN = "http://www.carseys.com/api/Api.php?apicall=logincliente";

    RelativeLayout loginRelative;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        loginRelative = findViewById(R.id.loginRelative);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPassword.isEmpty()){
                    Login();
                }else{
                    email.setError("Por favor inserte un Correo");
                    password.setError("Por favor inserte una Contraseña");
                }


            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

    }


    private void Login() {

        //RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Cliente cliente = new Cliente();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("error");

                    if (!succes.equalsIgnoreCase("false")){

                        Snackbar snackbar = Snackbar.make(loginRelative,"Correo o Contraseña erronea",Snackbar.LENGTH_LONG);
                        snackbar.show();

                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(getResources().getColor(R.color.colorDangerSnacBar));

                    }else{

                        cliente.setName(jsonObject.getJSONObject("contenido").optString("name"));
                        cliente.setLast_name(jsonObject.getJSONObject("contenido").optString("last_name"));
                        cliente.setDni(jsonObject.getJSONObject("contenido").optInt("dni"));
                        cliente.setEmail(jsonObject.getJSONObject("contenido").optString("email"));

                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_SHORT).show();
//                Snackbar snackbar = Snackbar.make(loginRelative,"Error"+error,Snackbar.LENGTH_LONG);
//                snackbar.show();
//
//                View snackView = snackbar.getView();
//                snackView.setBackgroundColor(getResources().getColor(R.color.colorDangerSnacBar));

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("email", email.getText().toString().trim());
                params.put("password", password.getText().toString().trim());
                return params;
            }
        };


        //requestQueue.add(stringRequest);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);

    }



}
