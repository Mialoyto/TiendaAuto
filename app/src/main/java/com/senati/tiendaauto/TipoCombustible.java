package com.senati.tiendaauto;

public class TipoCombustible {
    private int idtipocombustible ;
    private  String tipocombustible;

    public  TipoCombustible (int idtipocombustible, String tipocombustible){
        this.idtipocombustible = idtipocombustible;
        this.tipocombustible = tipocombustible;
    }

    public int getIdtipocombustible() {
        return idtipocombustible;
    }

    public String getTipocombustible() {
        return tipocombustible;
    }
    @Override
    public String toString(){
        return tipocombustible;
    }

}
