package com.senati.tiendaauto;

public class TipoVehiculo {
    private int idtipovehiculo;
    private  String tipovehiculo;

    public TipoVehiculo(int idtipovehiculo, String tipovehiculo){
        this.idtipovehiculo = idtipovehiculo;
        this.tipovehiculo = tipovehiculo;
    }

    public int getIdtipovehiculo() {
        return idtipovehiculo;
    }

    public String getTipovehiculo() {
        return tipovehiculo;
    }
    @Override
    public String toString(){
        return tipovehiculo;
    }
}
