package pe.edu.idat.apppedidos.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pe.edu.idat.apppedidos.R;
import pe.edu.idat.apppedidos.databinding.FragmentListpedBinding;
import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;
import pe.edu.idat.apppedidos.view.adapters.ListpedAdapter;
import pe.edu.idat.apppedidos.viewmodel.ListpedViewModel;


public class ListpedFragment extends Fragment {

    private FragmentListpedBinding binding;

    private ListpedViewModel listpedViewModel;
    private ListpedAdapter listpedAdapter = new ListpedAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListpedBinding.inflate(inflater,
                container, false);
        listpedViewModel = new ViewModelProvider(requireActivity())
                .get(ListpedViewModel.class);
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
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}