package pe.edu.idat.apppedidos.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.apppedidos.databinding.ItemDetallepedBinding;
import pe.edu.idat.apppedidos.databinding.ItemListpedBinding;
import pe.edu.idat.apppedidos.retrofit.response.ListdetalleResponse;

public class ListdetailsAdapter extends RecyclerView.Adapter<ListdetailsAdapter.ViewHolder> {
    
    List<ListdetalleResponse> listdetalleResponseList = new ArrayList<>();
    
    @NonNull
    @Override
    public ListdetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListdetailsAdapter.ViewHolder(ItemDetallepedBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    // Interfaz para manejar eventos del botón btnborrardetalle
    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int idDetalle);
    }

    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    // Método para establecer el listener desde fuera del adaptador
    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
        this.onDeleteButtonClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ListdetailsAdapter.ViewHolder holder, int position) {
        final ListdetalleResponse listdetalleResponse = listdetalleResponseList
                .get(position);
        holder.binding.tviddetalle.setText(String.valueOf(listdetalleResponse.getIddetalle()));
        holder.binding.tvdescripcion.setText(listdetalleResponse.getDesproduc());
        holder.binding.tvunidad.setText(listdetalleResponse.getUniproduc());
        holder.binding.tvcantidad.setText(String.valueOf(listdetalleResponse.getCantidad()));
        holder.binding.tvimporte.setText(String.valueOf(listdetalleResponse.getImporte()) );

        // Agrega el listener directamente al botón btnborrardetalle
        holder.binding.btnborrardetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llama al método de la interfaz para manejar la eliminación del detalle
                if (onDeleteButtonClickListener != null) {
                    onDeleteButtonClickListener.onDeleteButtonClick(listdetalleResponse.getIddetalle());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdetalleResponseList.size();
    }
    
    public void setDetalles(List<ListdetalleResponse> detalles){
        int previousSize = listdetalleResponseList.size();
        listdetalleResponseList.clear();
        listdetalleResponseList.addAll(detalles);
        int newSize = listdetalleResponseList.size();

        // Notifica solo las inserciones, si las hay
        if (newSize > previousSize) {
            notifyItemRangeInserted(previousSize, newSize - previousSize);
        } else {
            notifyDataSetChanged();
        }
    }

    public double calcularSumaImportes() {
        double suma = 0;
        for (ListdetalleResponse detalle : listdetalleResponseList) {
            suma += detalle.getImporte();
        }
        return suma;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDetallepedBinding binding;
        public ViewHolder(ItemDetallepedBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;

        }
    }
}
