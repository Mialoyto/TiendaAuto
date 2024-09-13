package com.senati.tiendaauto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnRegistrarVeh, btnListarVeh, btnFiltrarTipVeh;

    private void loadUI(){
        btnRegistrarVeh = findViewById(R.id.btnRegistrarVeh);
        btnListarVeh = findViewById(R.id.btnListarVeh);
        btnFiltrarTipVeh = findViewById(R.id.btnFiltrarTipVeh);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();

        btnRegistrarVeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(RegistrarVehiculo.class);
            }
        });

        btnListarVeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(ListarVehiculos.class);
            }
        });

        btnFiltrarTipVeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(ListarTipoVehiculos.class);
            }
        });

    }

    private void openActivity(Class nameActivity){
        Intent intent = new Intent(this, nameActivity);
        startActivity(intent);
    }
}