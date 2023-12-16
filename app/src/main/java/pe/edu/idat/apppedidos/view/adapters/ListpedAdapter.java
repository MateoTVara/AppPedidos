package pe.edu.idat.apppedidos.view.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pe.edu.idat.apppedidos.R;
import pe.edu.idat.apppedidos.bd.entity.Pedido;
import pe.edu.idat.apppedidos.databinding.ItemListpedBinding;
import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListpeddetailedResponse;
import pe.edu.idat.apppedidos.view.activities.MainActivity;
import pe.edu.idat.apppedidos.view.fragments.CreapedFragment;
import pe.edu.idat.apppedidos.view.fragments.ListpedFragment;
import pe.edu.idat.apppedidos.viewmodel.ListpedViewModel;

public class ListpedAdapter extends RecyclerView.Adapter<ListpedAdapter.ViewHolder> {

    List<ListpedResponse> listpedResponseList =  new ArrayList<>();
    List<ListpedResponse> listpedResponseListOriginal = new ArrayList<>();
    private ListpeddetailedResponse listpeddetailedResponse = new ListpeddetailedResponse();
    private ListpedViewModel listpedViewModel;

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

    public interface OnEditButtonClickListener {
        void onEditButtonClick(int idPed);
    }

    private OnEditButtonClickListener onEditButtonClickListener;

    // Método para establecer el listener desde fuera del adaptador
    public void setOnEditButtonClickListener(OnEditButtonClickListener listener) {
        this.onEditButtonClickListener = listener;
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

        holder.binding.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditButtonClickListener != null) {
                    onEditButtonClickListener.onEditButtonClick(listpedResponse.getIdped());
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
