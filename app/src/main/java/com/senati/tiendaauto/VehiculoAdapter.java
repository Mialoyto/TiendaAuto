package com.senati.tiendaauto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder> {

    public List<Vehiculo> listVehiculos;

    public  VehiculoAdapter(List<Vehiculo> listVehiculos){this.listVehiculos = listVehiculos;}

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehiculo,parent,false);
        return new VehiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int position) {
        Vehiculo vehiculo = listVehiculos.get(position);
        holder.tvTipoVehiculo.setText(vehiculo.getTipovehiculo());
        holder.tvMarca.setText(vehiculo.getMarca());
        holder.tvTipoCom.setText(vehiculo.getTipoCombustible());
        holder.tvAnioFab.setText(vehiculo.getAnioFabricacion());
    }

    @Override
    public int getItemCount() {
        return listVehiculos.size();
    }

    public static class VehiculoViewHolder extends RecyclerView.ViewHolder{
        TextView tvTipoVehiculo, tvMarca, tvTipoCom, tvAnioFab;

        public VehiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipoVehiculo = itemView.findViewById(R.id.tvTipoVehiculo);
            tvMarca = itemView.findViewById(R.id.tvMarca);
            tvTipoCom = itemView.findViewById(R.id.tvTipoCom);
            tvAnioFab = itemView.findViewById(R.id.tvAnioFab);
        }
    }

}
