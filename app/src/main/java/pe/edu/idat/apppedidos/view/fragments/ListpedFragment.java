package pe.edu.idat.apppedidos.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pe.edu.idat.apppedidos.R;
import pe.edu.idat.apppedidos.bd.entity.Pedido;
import pe.edu.idat.apppedidos.databinding.FragmentListpedBinding;
import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListpeddetailedResponse;
import pe.edu.idat.apppedidos.view.adapters.ListpedAdapter;
import pe.edu.idat.apppedidos.viewmodel.ListpedViewModel;
import pe.edu.idat.apppedidos.viewmodel.PedidoViewModel;


public class ListpedFragment extends Fragment
        implements SearchView.OnQueryTextListener
        ,ListpedAdapter.OnEditButtonClickListener{

    private FragmentListpedBinding binding;
    private ListpedViewModel listpedViewModel;
    private PedidoViewModel pedidoViewModel;
    private ListpedAdapter listpedAdapter = new ListpedAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListpedBinding.inflate(inflater,
                container, false);
        listpedViewModel = new ViewModelProvider(requireActivity())
                .get(ListpedViewModel.class);
        pedidoViewModel = new ViewModelProvider(requireActivity())
                .get(PedidoViewModel.class);
        binding.rvlisped.setLayoutManager(
                new LinearLayoutManager(requireActivity())
        );
        binding.rvlisped.setAdapter(listpedAdapter);
        listpedViewModel.listarPedidos();
        listpedViewModel.listMutableLiveData.observe(
                getViewLifecycleOwner(),
                new Observer<List<ListpedResponse>>() {
                    @Override
                    public void onChanged(List<ListpedResponse> listpedResponses) {
                        listpedAdapter.setPedidos(listpedResponses);
                    }
                }
        );

        listpedViewModel = new ViewModelProvider(this).get(ListpedViewModel.class);

        binding.svlisped.setOnQueryTextListener(this);
        listpedAdapter.setOnEditButtonClickListener(this);


        borrarPedido();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listpedAdapter.filtrarPedidos(newText);
        return false;
    }

    private void borrarPedido(){
        listpedAdapter.setOnDeleteButtonClickListener(new ListpedAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int idPed) {
                listpedViewModel.eliminarPedido(idPed);
                listpedViewModel.listarPedidos();
            }
        });
    }

    @Override
    public void onEditButtonClick(int idPed) {
        // Implementar la navegación a la vista correspondiente con el idped
        NavController navController = Navigation.findNavController(requireView());
        // Navegar al fragmento CreapedFragment y pasar el idped como argumento
        Bundle bundle = new Bundle();
        bundle.putInt("idped", idPed);
        navController.navigate(R.id.action_listpedFragment_to_modifypedFragment, bundle);
    }

}