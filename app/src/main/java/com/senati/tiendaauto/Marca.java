package com.senati.tiendaauto;

public class Marca {
    private int idmarca;
    private  String marca;

    public Marca(int idmarca, String marca){
        this.idmarca = idmarca;
        this.marca = marca;
    }

    public int getIdmarca(){
        return idmarca;
    }

    public String getMarca(){
        return marca;
    }

    @Override
    public String toString(){
        return marca;
    }
}
