package com.senati.tiendaauto;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.text.UStringsKt;

public class RegistrarVehiculo extends AppCompatActivity {

    Spinner idSpiner, spIdMarca, spIdTipoCom;
    private int idVehiculoSeleccionado;
    private int idMarcaSeleccionada;
    private int idCombustibleSeleccionado;
    private List<Marca> marcas;
    private  List<TipoCombustible> combustibles;
    private List<TipoVehiculo> vehiculos;

    private TextView edtColor, edtAFab, edtNumAsiento, edtUrl;
    private Button btnGuardarLibro;


    private void loadUI(){
        idSpiner = findViewById(R.id.spIdtipoVeh);
        spIdMarca = findViewById(R.id.spIdMarca);
        spIdTipoCom = findViewById(R.id.spIdTipoCom);

        marcas = new ArrayList<>();

        combustibles = new ArrayList<>();
        vehiculos = new ArrayList<>();

        edtColor = findViewById(R.id.edtColor);
        edtAFab = findViewById(R.id.edtAFab);
        edtNumAsiento = findViewById(R.id.edtNumAsiento);
        edtUrl = findViewById(R.id.edtUrl);

        btnGuardarLibro = findViewById(R.id.btnGuardarLibro);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_vehiculo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();
        getAllMArcas();
        getAllTipoCombustible();
        getAllTipoVeehiculo();

        btnGuardarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idCombustibleSeleccionado == -1) {
                    Toast.makeText(getApplicationContext(), "Por favor seleccione un tipo de combustible ", Toast.LENGTH_SHORT).show();
                } else if (idMarcaSeleccionada == -1) {
                    Toast.makeText(getApplicationContext(), "Por favor seleccione una marca", Toast.LENGTH_SHORT).show();
                } else if (idVehiculoSeleccionado == -1) {
                    Toast.makeText(getApplicationContext(), "Por favor seleccione un tipo de vehículo", Toast.LENGTH_SHORT).show();
                } else {
                    validarProceso(); // Solo procede si todas las selecciones son válidas

                }
            }
        });
    }

    private void validarProceso(){
        Utils.mostrarDialogConfirmar(
                RegistrarVehiculo.this,
                "Registrar Vehiculo",
                "¿Esta seguro de regutrar?",
                new Runnable() {
                    @Override
                    public void run() {
                        registrarVehiculo();
                        resetUI();
                    }
                }
        );
    }


    private void registrarVehiculo () {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("idtipovehiculo",idVehiculoSeleccionado);
            jsonObject.put("idmarca", idMarcaSeleccionada);
            jsonObject.put("idtipocombustible", idCombustibleSeleccionado);
            jsonObject.put("color",edtColor.getText().toString());
            jsonObject.put("afabricacion", edtAFab.getText().toString());
            jsonObject.put("numasientos", edtNumAsiento.getText().toString());
            jsonObject.put("fotografia", edtUrl.getText().toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    Utils.URL,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                boolean guardado = response.getBoolean("Guardado");
                                if(guardado){
                                    Utils.notificar(getApplicationContext(),"Registro exitoso");
                                }
                            }catch (Exception e){
                                Log.e("Error JSON",e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error en Webservice", error.toString());
                        }
                    }
            );
            requestQueue.add(jsonObjectRequest);
        }catch(Exception e){

        }


    }

    private void getAllMArcas(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Utils.URLMARCA,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Log.d("API RESPONDE OK =====>", response.toString());
                        try{
                            for(int i = 0; i <  response.length(); i++){
                                JSONObject marcajso = response.getJSONObject(i);
                                int idmarca = marcajso.getInt("idmarca");
                                String marca = marcajso.getString("marca");

                                marcas.add(new Marca(idmarca, marca));
                            }
                            llenarSpinner(marcas);
                        }catch (Exception e){
                            Log.e("Error JSON",e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error WebService", error.toString());
                    }
                }
        );
        requestQueue.add((jsonArrayRequest));
    }

    private void getAllTipoCombustible(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Utils.URLTIPOCOM,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Log.d("API RESPONDE OK =====>", response.toString());
                        try{
                            for(int i = 0; i <  response.length(); i++){
                                JSONObject combJson = response.getJSONObject(i);
                                int idtipocombustible = combJson.getInt("idtipocombustible");
                                String tipocombustible = combJson.getString("tipocombustible");

                                combustibles.add(new TipoCombustible(idtipocombustible, tipocombustible));
                            }
                            llenarSpinnerComb(combustibles);
                        }catch (Exception e){
                            Log.e("Error JSON",e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error WebService", error.toString());
                    }
                }
        );
        requestQueue.add((jsonArrayRequest));
    }

    /////////////////////////////////
    private void getAllTipoVeehiculo(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Utils.URLTIPOVEH,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Log.d("API RESPONDE OK =====>", response.toString());
                        try{
                            for(int i = 0; i <  response.length(); i++){
                                JSONObject tipovJson = response.getJSONObject(i);
                                int idtipovehiculo = tipovJson.getInt("idtipovehiculo");
                                String tipovehiculo = tipovJson.getString("tipovehiculo");

                                vehiculos.add(new TipoVehiculo(idtipovehiculo, tipovehiculo));
                            }
                            llenarSpinnerVeh(vehiculos);
                        }catch (Exception e){
                            Log.e("Error JSON",e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error WebService", error.toString());
                    }
                }
        );
        requestQueue.add((jsonArrayRequest));
    }

    private void llenarSpinnerVeh(List<TipoVehiculo> vehiculos){
        List<String> listVehiculos = new ArrayList<>();
        listVehiculos.add("Seleccione un tipo de vehiculo");
        for (TipoVehiculo vehiculo : vehiculos){
            listVehiculos.add(vehiculo.getTipovehiculo());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listVehiculos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIdTipoCom.setAdapter(adapter);

        spIdTipoCom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i ==0){
                    idVehiculoSeleccionado = -1;
                }else{
                    TipoVehiculo selectedVeh = vehiculos.get(i-1);
                    idVehiculoSeleccionado = selectedVeh.getIdtipovehiculo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                idVehiculoSeleccionado = -1;
            }
        });
    }


    ////////////////////////////////
    private void llenarSpinnerComb(List<TipoCombustible> combustibles){
        List<String> listCombustibles = new ArrayList<>();
        listCombustibles.add("Selecione un tipo de combustible");

        for(TipoCombustible combustible : combustibles){
            listCombustibles.add(combustible.getTipocombustible());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCombustibles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idSpiner.setAdapter(adapter);

        idSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i ==0){
                    idCombustibleSeleccionado = -1;
                }else{
                    TipoCombustible selectedComb = combustibles.get(i-1);
                    idCombustibleSeleccionado = selectedComb.getIdtipocombustible();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    idCombustibleSeleccionado = -1;
            }
        });

    }

    private void llenarSpinner(List<Marca> marcas){
        List<String> listMarcas = new ArrayList<>();
        listMarcas.add("Seleccione una marca");

        for(Marca marca : marcas){
            listMarcas.add(marca.getMarca());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listMarcas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIdMarca.setAdapter(adapter);

        spIdMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i ==0){
                    idMarcaSeleccionada = -1;
                }else{
                    Marca selectedComb = marcas.get(i-1);
                    idMarcaSeleccionada = selectedComb.getIdmarca();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                idMarcaSeleccionada = -1;
            }
        });
    }

    private void resetUI() {
        // Restablecer EditTexts
        edtColor.setText("");         // Restablece el campo de color
        edtAFab.setText("");          // Restablece el campo de año de fabricación
        edtNumAsiento.setText("");    // Restablece el campo de número de asientos
        edtUrl.setText("");           // Restablece el campo de URL

        // Restablecer Spinners a la primera posición ("Seleccione item")
        idSpiner.setSelection(0);     // Restablece el Spinner de tipo de vehículo
        spIdMarca.setSelection(0);    // Restablece el Spinner de marcas
        spIdTipoCom.setSelection(0);  // Restablece el Spinner de tipo de combustible

        // Restablecer las variables de selección
        idVehiculoSeleccionado = -1;  // Restablece la variable de tipo de vehículo seleccionado
        idMarcaSeleccionada = -1;     // Restablece la variable de marca seleccionada
        idCombustibleSeleccionado = -1; // Restablece la variable de tipo de combustible seleccionado
    }
}