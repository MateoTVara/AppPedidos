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
import pe.edu.idat.apppedidos.view.adapters.ClienteAutoCompleteAdapter;
import pe.edu.idat.apppedidos.viewmodel.CreapedViewModel;


public class CreapedFragment extends Fragment {

    private CreapedViewModel viewModel;
    private AutoCompleteTextView ptrazonsocial;
    private EditText ptrucdni;
    private EditText ptdireccion;
    private ClienteAutoCompleteAdapter clienteAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creaped, container, false);

        ptrazonsocial = view.findViewById(R.id.ptrazonsocial);
        ptrucdni = view.findViewById(R.id.ptrucdni);
        ptdireccion = view.findViewById(R.id.ptdireccion);

        viewModel = new ViewModelProvider(this).get(CreapedViewModel.class);

        ptrazonsocial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Actualizar datos correspondientes
                ListcliResponse clienteSeleccionado = (ListcliResponse) parent.getItemAtPosition(position);
                if (clienteSeleccionado != null) {
                    ptrucdni.setText(clienteSeleccionado.getRucdni());
                    ptdireccion.setText(clienteSeleccionado.getDireccion());
                }
            }
        });
        ptrazonsocial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.buscarClientePorRazonSocialYSugerencias(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewModel.sugerenciasLiveData.observe(getViewLifecycleOwner(), new Observer<List<ListcliResponse>>() {
            @Override
            public void onChanged(List<ListcliResponse> sugerencias) {
                //Mostrar las sugerencias en el listado desplegado
                mostrarSugerencias(sugerencias);
            }
        });
        return view;
    }

    private void mostrarSugerencias(List<ListcliResponse> sugerencias) {
        ClienteAutoCompleteAdapter adapter = new ClienteAutoCompleteAdapter(requireContext(), sugerencias);
        ptrazonsocial.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}