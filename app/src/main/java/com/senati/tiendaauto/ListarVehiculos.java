package com.senati.tiendaauto;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListarVehiculos extends AppCompatActivity {
    private RecyclerView recyclerViewItems;
    private VehiculoAdapter vehiculoAdapter;
    private List<Vehiculo>  lisVehiculos;

    private void loadUI(){
        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        lisVehiculos = new ArrayList<>();
        vehiculoAdapter = new VehiculoAdapter(lisVehiculos);
        recyclerViewItems.setAdapter(vehiculoAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_vehiculos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();
        getAllVehiculos();
    }

    private void getAllVehiculos(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Utils.URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++){
                            try{
                                JSONObject vehiculoJson = response.getJSONObject(i);
                                String tipovehiculo = vehiculoJson.getString("tipovehiculo");
                                String marca = vehiculoJson.getString("marca");
                                String tipoCombustible = vehiculoJson.getString("tipocombustible");
                                String afabricacion = vehiculoJson.getString("afabricacion");

                                Vehiculo vehiculo = new Vehiculo( tipovehiculo,marca, tipoCombustible, afabricacion);
                                lisVehiculos.add(vehiculo);

                            }catch (Exception e){
                                Log.e("Error JSON",e.toString());
                            }
                        }
                        vehiculoAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error WebService", error.toString());
                    }
                }

        );
        requestQueue.add(jsonArrayRequest);
    }
}