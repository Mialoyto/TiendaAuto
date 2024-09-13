package com.senati.tiendaauto;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class Utils {
    public static final String URL= "http://192.168.5.19/wstiendaauto/api/vehiculo.api.php";
    public static final String URLMARCA = "http://192.168.5.19/wstiendaauto/api/marca.api.php";
    public static final String URLTIPOVEH = "http://192.168.5.19/wstiendaauto/api/tipovehiculo.api.php";
    public static final String URLTIPOCOM = "http://192.168.5.19/wstiendaauto/api/tipocombustible.api.php";

    public static void notificar(Context context, String mensaje){
        Toast.makeText(
                context,
                mensaje,
                Toast.LENGTH_SHORT
        ).show();
    }

    public static void mostrarDialogConfirmar(Context context, String titulo, String mensaje, Runnable metodo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                metodo.run();

            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
