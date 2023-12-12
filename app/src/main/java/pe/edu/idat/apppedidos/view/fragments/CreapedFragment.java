package pe.edu.idat.apppedidos.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.List;

import pe.edu.idat.apppedidos.R;
import pe.edu.idat.apppedidos.databinding.FragmentCreapedBinding;
import pe.edu.idat.apppedidos.databinding.FragmentListpedBinding;
import pe.edu.idat.apppedidos.retrofit.response.ListcliResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListproResponse;
import pe.edu.idat.apppedidos.view.adapters.ClienteAutoCompleteAdapter;
import pe.edu.idat.apppedidos.view.adapters.ProductoAutoCompleteAdapter;
import pe.edu.idat.apppedidos.viewmodel.CreapedViewModel;


public class CreapedFragment extends Fragment {

    private FragmentCreapedBinding binding;
    private CreapedViewModel viewModel;
    private AutoCompleteTextView ptrazonsocial;
    private EditText ptrucdni;
    private EditText ptdireccion;
    private AutoCompleteTextView ptdescripcionproducto;
    private EditText ptunidadproducto;
    private EditText ptprecioproducto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreapedBinding.inflate(inflater, container, false);

        ptrazonsocial = binding.ptrazonsocial;
        ptrucdni = binding.ptrucdni;
        ptdireccion = binding.ptdireccion;
        ptdescripcionproducto = binding.ptdescripcionproducto;
        ptunidadproducto = binding.ptunidadproducto;
        ptprecioproducto = binding.ptprecioproducto;

        viewModel = new ViewModelProvider(this).get(CreapedViewModel.class);

        setupViewsCliente();
        setupViewsProducto();

        return binding.getRoot();
    }

    private void setupViewsCliente() {
        ptrazonsocial.setOnItemClickListener((parent, view, position, id) -> {
            ListcliResponse clienteSeleccionado = (ListcliResponse) parent.getItemAtPosition(position);
            if (clienteSeleccionado != null) {
                ptrucdni.setText(clienteSeleccionado.getRucdni());
                ptdireccion.setText(clienteSeleccionado.getDireccion());
            }
        });

        ptrazonsocial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.sugerenciasPorRazonSocial(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        viewModel.sugerenciasLiveData.observe(getViewLifecycleOwner(), sugerencias -> mostrarSugerenciasDeClientes(sugerencias));
    }



    private void mostrarSugerenciasDeClientes(List<ListcliResponse> sugerencias) {
        ClienteAutoCompleteAdapter adapter = new ClienteAutoCompleteAdapter(requireContext(), sugerencias);
        ptrazonsocial.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setupViewsProducto() {
        ptdescripcionproducto = binding.ptdescripcionproducto;
        ptunidadproducto = binding.ptunidadproducto;
        ptprecioproducto = binding.ptprecioproducto;

        ptdescripcionproducto.setOnItemClickListener((parent, view, position, id) -> {
            ListproResponse productoSeleccionado = (ListproResponse) parent.getItemAtPosition(position);
            if (productoSeleccionado != null) {
                ptunidadproducto.setText(productoSeleccionado.getUniproduc());
                ptprecioproducto.setText(String.valueOf(productoSeleccionado.getPrecio()));
            }
        });

        ptdescripcionproducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.sugerenciasPorDescripcion(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        viewModel.sugerenciasproductosLiveData.observe(getViewLifecycleOwner(), sugerencias -> mostrarSugerenciasDeProductos(sugerencias));
    }


    private void mostrarSugerenciasDeProductos(List<ListproResponse> sugerencias) {
        ProductoAutoCompleteAdapter adapter = new ProductoAutoCompleteAdapter(requireContext(), sugerencias);
        ptdescripcionproducto.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}