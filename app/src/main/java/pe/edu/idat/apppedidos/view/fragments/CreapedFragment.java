package pe.edu.idat.apppedidos.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.*;

import java.util.List;

import pe.edu.idat.apppedidos.R;
import pe.edu.idat.apppedidos.databinding.FragmentCreapedBinding;
import pe.edu.idat.apppedidos.databinding.FragmentListpedBinding;
import pe.edu.idat.apppedidos.retrofit.request.RegisdetalleRequest;
import pe.edu.idat.apppedidos.retrofit.request.RegispedRequest;
import pe.edu.idat.apppedidos.retrofit.response.ListcliResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListdetalleResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListproResponse;
import pe.edu.idat.apppedidos.view.adapters.ClienteAutoCompleteAdapter;
import pe.edu.idat.apppedidos.view.adapters.ListdetailsAdapter;
import pe.edu.idat.apppedidos.view.adapters.ProductoAutoCompleteAdapter;
import pe.edu.idat.apppedidos.viewmodel.CreapedViewModel;
import pe.edu.idat.apppedidos.viewmodel.ListdetalleViewModel;


public class CreapedFragment extends Fragment {

    private FragmentCreapedBinding binding;
    private ListdetalleViewModel listdetalleViewModel;
    private ListdetailsAdapter listdetailsAdapter = new ListdetailsAdapter();
    private CreapedViewModel viewModel;
    private AutoCompleteTextView ptrazonsocial;
    private EditText ptidcli;
    private EditText ptrucdni;
    private EditText ptdireccion;
    private AutoCompleteTextView ptdescripcionproducto;
    private EditText ptidproduc;
    private EditText ptunidadproducto;
    private EditText ptprecioproducto;
    private EditText ptcantidadproducto;
    private Button btnagregardetalle;
    private Button btnactualizarlistado;
    private Button btnguardar;
    private EditText ptdocumento;
    private EditText ptfchareparto;
    private EditText ptidusu;
    private EditText ptvendedor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreapedBinding.inflate(inflater, container, false);
        listdetalleViewModel = new ViewModelProvider(requireActivity())
                .get(ListdetalleViewModel.class);
        binding.rvdetallepedido.setLayoutManager(
                new LinearLayoutManager(requireActivity())
        );
        binding.rvdetallepedido.setAdapter(listdetailsAdapter);
        listdetalleViewModel.getListLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<List<ListdetalleResponse>>() {
                    @Override
                    public void onChanged(List<ListdetalleResponse> listdetalleResponses) {
                        listdetailsAdapter.setDetalles(listdetalleResponses);
                    }
                }
        );

        listdetalleViewModel.listarDetalles();

        ptrazonsocial = binding.ptrazonsocial;
        ptidcli = binding.ptidcli;
        ptrucdni = binding.ptrucdni;
        ptdireccion = binding.ptdireccion;

        ptdescripcionproducto = binding.ptdescripcionproducto;
        ptidproduc = binding.ptidproduc;
        ptunidadproducto = binding.ptunidadproducto;
        ptprecioproducto = binding.ptprecioproducto;

        ptcantidadproducto = binding.ptcantidadproducto;

        btnagregardetalle = binding.btnagregardetalle;
        btnactualizarlistado = binding.btnactualizarlistado;
        btnguardar = binding.btnguardar;

        ptdocumento = binding.ptdocumento;
        ptfchareparto = binding.ptfchareparto;
        ptidusu= binding.ptidusu;
        ptvendedor = binding.ptvendedor;

        viewModel = new ViewModelProvider(this).get(CreapedViewModel.class);

        setupViewsCliente();
        setupViewsProducto();
        setupAgregarDetalleButton();
        onclickActualizarListado();
        borrarDetalle();
        setupGuardarPedidoButton();

        return binding.getRoot();
    }

    private void setupViewsCliente() {
        ptrazonsocial.setOnItemClickListener((parent, view, position, id) -> {
            ListcliResponse clienteSeleccionado = (ListcliResponse) parent.getItemAtPosition(position);
            if (clienteSeleccionado != null) {
                ptidcli.setText(String.valueOf(clienteSeleccionado.getIdcli()));
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
        ptidproduc = binding.ptidproduc;
        ptunidadproducto = binding.ptunidadproducto;
        ptprecioproducto = binding.ptprecioproducto;

        ptdescripcionproducto.setOnItemClickListener((parent, view, position, id) -> {
            ListproResponse productoSeleccionado = (ListproResponse) parent.getItemAtPosition(position);
            if (productoSeleccionado != null) {
                ptidproduc.setText(String.valueOf(productoSeleccionado.getIdproduc()));
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

    private void setupAgregarDetalleButton() {
        if (btnagregardetalle != null){
            btnagregardetalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Paso 1: Obtener valores de ptidproduc y ptcantidadproducto
                    String idproducText = ptidproduc.getText().toString().trim();
                    String cantidadText = ptcantidadproducto.getText().toString().trim();

                    if (!idproducText.isEmpty() && !cantidadText.isEmpty()) {
                        try {
                            // Ambas cadenas no están vacías, puedes convertirlas a números enteros
                            int idproduc = Integer.parseInt(idproducText);
                            int cantidad = Integer.parseInt(cantidadText);

                            // Paso 2: Crear el objeto RegisdetalleRequest
                            RegisdetalleRequest regisdetalleRequest = new RegisdetalleRequest();
                            regisdetalleRequest.setIdproduc(idproduc);
                            regisdetalleRequest.setCantidad(cantidad);

                            // Paso 3: Llamar al método registrarDetalleParcial del viewModel
                            viewModel.registrarDetalleParcial(regisdetalleRequest);

                            // Paso 4: Después de agregar el detalle, actualizar la lista llamando a listarDetalles
                            listdetalleViewModel.listarDetalles();
                            limpiarDetallesCampos();
                        } catch (NumberFormatException e) {
                            // Manejar la excepción si ocurre un formato incorrecto al convertir a entero
                            Log.e("CreapedFragment", "Error al convertir cadena a número", e);
                            // Puedes mostrar un mensaje de error al usuario o tomar otra acción según tus necesidades.
                        }
                    } else {
                        // Manejar el caso en que una o ambas cadenas están vacías
                        // Puedes mostrar un mensaje al usuario o tomar otra acción según tus necesidades.
                    }
                }
            });
        }else{
            Log.e("CreapedFragment", "btnagregardetalle es nulo");
        }

    }

    private void borrarDetalle(){
        listdetailsAdapter.setOnDeleteButtonClickListener(new ListdetailsAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int idDetalle) {
                // Llama al método para eliminar el detalle en el ViewModel
                viewModel.eliminarDetalle(idDetalle);
                listdetalleViewModel.listarDetalles();
            }
        });
    }

    private void limpiarDetallesCampos(){
        ptidproduc.getText().clear();
        ptdescripcionproducto.getText().clear();
        ptunidadproducto.getText().clear();
        ptcantidadproducto.getText().clear();
        ptprecioproducto.getText().clear();
    }

    private void setupGuardarPedidoButton() {
        if (btnguardar != null) {
            btnguardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Paso 1: Obtener valores de los campos requeridos
                    String documento = ptdocumento.getText().toString().trim().toUpperCase();
                    String idcliText = ptidcli.getText().toString().trim();
                    String fchareparto = ptfchareparto.getText().toString().trim();
                    String idusuText = ptidusu.getText().toString().trim();

                    if (!documento.isEmpty() && !idcliText.isEmpty() && !fchareparto.isEmpty() && !idusuText.isEmpty()) {
                        try {
                            // Ambas cadenas no están vacías, puedes convertirlas a números enteros
                            int idcli = Integer.parseInt(idcliText);
                            int idusu = Integer.parseInt(idusuText);

                            // Paso 2: Crear el objeto RegispedRequest
                            RegispedRequest regispedRequest = new RegispedRequest();
                            regispedRequest.setDocumento(documento);
                            regispedRequest.setIdcli(idcli);
                            regispedRequest.setFchareparto(fchareparto);
                            regispedRequest.setIdusu(idusu);

                            // Paso 3: Llamar al método registrarPedido del viewModel
                            viewModel.registrarPedido(regispedRequest);

                            limpiarPedidosCampos();

                            listdetalleViewModel.listarDetalles();

                            // Paso 4: Después de guardar el pedido, puedes realizar alguna acción adicional si es necesario

                        } catch (NumberFormatException e) {
                            // Manejar la excepción si ocurre un formato incorrecto al convertir a entero
                            Log.e("CreapedFragment", "Error al convertir cadena a número", e);
                            // Puedes mostrar un mensaje de error al usuario o tomar otra acción según tus necesidades.
                        }
                    } else {
                        // Manejar el caso en que una o varias cadenas estén vacías
                        // Puedes mostrar un mensaje al usuario o tomar otra acción según tus necesidades.
                    }
                }
            });
        } else {
            Log.e("CreapedFragment", "btnguardar es nulo");
        }
    }

    private void limpiarPedidosCampos() {
        ptdocumento.getText().clear();
        ptrazonsocial.getText().clear();
        ptidcli.getText().clear();
        ptrucdni.getText().clear();
        ptdireccion.getText().clear();
        ptfchareparto.getText().clear();
        ptidusu.getText().clear();
        ptvendedor.getText().clear();
    }

    private void onclickActualizarListado(){
        btnactualizarlistado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listdetalleViewModel.listarDetalles();
            }
        });
    }
}