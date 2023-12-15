package pe.edu.idat.apppedidos.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pe.edu.idat.apppedidos.databinding.ItemListpedBinding;
import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;

public class ListpedAdapter extends RecyclerView.Adapter<ListpedAdapter.ViewHolder> {

    List<ListpedResponse> listpedResponseList =  new ArrayList<>();
    List<ListpedResponse> listpedResponseListOriginal = new ArrayList<>();

    @NonNull
    @Override
    public ListpedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemListpedBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
        ));
    }

    // Interfaz para manejar eventos del botón btnborrardetalle
    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int idPed);
    }

    private ListpedAdapter.OnDeleteButtonClickListener onDeleteButtonClickListener;

    // Método para establecer el listener desde fuera del adaptador
    public void setOnDeleteButtonClickListener(ListpedAdapter.OnDeleteButtonClickListener listener) {
        this.onDeleteButtonClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ListpedAdapter.ViewHolder holder, int position) {
        final ListpedResponse listpedResponse = listpedResponseList
                .get(position);
        holder.binding.tvid.setText(String.valueOf(listpedResponse.getIdped()));
        holder.binding.tvrazonsocial.setText(listpedResponse.getRazonsocial());
        holder.binding.tvdocumento.setText(listpedResponse.getDocumento());
        holder.binding.tvfchareparto.setText(listpedResponse.getFchareparto());

        holder.binding.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llama al método de la interfaz para manejar la eliminación del detalle
                if (onDeleteButtonClickListener != null) {
                    onDeleteButtonClickListener.onDeleteButtonClick(listpedResponse.getIdped());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listpedResponseList.size();
    }

    public void setPedidos(List<ListpedResponse> pedidos){
        listpedResponseList.clear();
        listpedResponseList.addAll(pedidos);
        notifyDataSetChanged();
        listpedResponseListOriginal.clear();
        listpedResponseListOriginal.addAll(pedidos);
    }

    public void filtrarPedidos(String filtro){
        if(filtro.isEmpty()){
            listpedResponseList.clear();
            listpedResponseList.addAll(listpedResponseListOriginal);
        }else{
            List<ListpedResponse> busquedaPedido =
                    listpedResponseList.stream()
                            .filter(p -> p.getRazonsocial()
                                    .toLowerCase().contains(
                                            filtro.toLowerCase()))
                            .collect(Collectors.toList());
            listpedResponseList.clear();
            listpedResponseList.addAll(busquedaPedido);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemListpedBinding binding;
        public ViewHolder(ItemListpedBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
