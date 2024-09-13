package com.senati.tiendaauto;

public class Vehiculo {
    private String tipovehiculo;
    private  String marca;
    private String tipoCombustible;
    private String afabricacion;

    public  Vehiculo(String tipovehiculo, String modelo, String tipocombustible, String anioFabricacion){
        this.tipovehiculo = tipovehiculo;
        this.marca = modelo;
        this.tipoCombustible = tipocombustible;
        this.afabricacion = anioFabricacion;
    }
    // getters
    public String getTipovehiculo() {
        return tipovehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public String getAnioFabricacion() {
        return afabricacion;
    }
}
