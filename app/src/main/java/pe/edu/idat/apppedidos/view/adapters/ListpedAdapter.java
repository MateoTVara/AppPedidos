package pe.edu.idat.apppedidos.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.apppedidos.databinding.ItemListpedBinding;
import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;

public class ListpedAdapter extends RecyclerView.Adapter<ListpedAdapter.ViewHolder> {

    List<ListpedResponse> listpedResponseList =  new ArrayList<>();

    @NonNull
    @Override
    public ListpedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemListpedBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ListpedAdapter.ViewHolder holder, int position) {
        final ListpedResponse listpedResponse = listpedResponseList
                .get(position);
        holder.binding.tvrazonsocial.setText(listpedResponse.getRazonsocial());
        holder.binding.tvdocumento.setText(listpedResponse.getDocumento());
        holder.binding.tvfchareparto.setText(listpedResponse.getFchareparto());
    }

    @Override
    public int getItemCount() {
        return listpedResponseList.size();
    }

    public void setPedidos(List<ListpedResponse> pedidos){
        listpedResponseList.addAll(pedidos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemListpedBinding binding;
        public ViewHolder(ItemListpedBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
