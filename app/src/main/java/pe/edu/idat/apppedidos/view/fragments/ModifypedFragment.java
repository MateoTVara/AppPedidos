package pe.edu.idat.apppedidos.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import pe.edu.idat.apppedidos.R;
import pe.edu.idat.apppedidos.databinding.FragmentCreapedBinding;
import pe.edu.idat.apppedidos.databinding.FragmentModifypedBinding;
import pe.edu.idat.apppedidos.retrofit.request.ModifypedRequest;
import pe.edu.idat.apppedidos.retrofit.request.RegisdetalleRequest;
import pe.edu.idat.apppedidos.retrofit.request.RegispedRequest;
import pe.edu.idat.apppedidos.retrofit.response.ListcliResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListdetalleResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListpeddetailedResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListproResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListusuResponse;
import pe.edu.idat.apppedidos.view.adapters.ClienteAutoCompleteAdapter;
import pe.edu.idat.apppedidos.view.adapters.ListdetailsAdapter;
import pe.edu.idat.apppedidos.view.adapters.ProductoAutoCompleteAdapter;
import pe.edu.idat.apppedidos.view.adapters.UsuarioAutoCompleteAdapter;
import pe.edu.idat.apppedidos.viewmodel.CreapedViewModel;
import pe.edu.idat.apppedidos.viewmodel.ListdetalleViewModel;
import pe.edu.idat.apppedidos.viewmodel.ListpedViewModel;
import pe.edu.idat.apppedidos.viewmodel.PedidoViewModel;

public class ModifypedFragment extends Fragment {

    private FragmentModifypedBinding binding;
    private ListdetalleViewModel listdetalleViewModel;
    private ListdetailsAdapter listdetailsAdapter = new ListdetailsAdapter();
    private CreapedViewModel creapedViewModel;
    private PedidoViewModel pedidoViewModel;
    private ListpedViewModel listpedViewModel;
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
    private AutoCompleteTextView ptvendedor;
    private Button btncancelar;
    private EditText ptidped;
    private TextView tvtotal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentModifypedBinding.inflate(inflater, container, false);
        listdetalleViewModel = new ViewModelProvider(requireActivity())
                .get(ListdetalleViewModel.class);
        listpedViewModel = new ViewModelProvider(requireActivity())
                .get(ListpedViewModel.class);
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
                        actualizarSumaImportes();
                    }
                }
        );

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
        ptidusu = binding.ptidusu;
        ptvendedor = binding.ptvendedor;

        btncancelar = binding.btncancelar;

        ptidped = binding.ptidped;
        tvtotal = binding.tvtotal;

        creapedViewModel = new ViewModelProvider(this).get(CreapedViewModel.class);
        if (getArguments() != null && getArguments().containsKey("idped")) {
            int idped = getArguments().getInt("idped");
            // Ahora puedes utilizar el idped para obtener los detalles del pedido
            creapedViewModel.listarDetallesPorPedido(idped);
            listpedViewModel.buscarPedidoDetallado(idped);
        }
        listpedViewModel.listpeddetailedResponseMutableLiveData
                .observe(getViewLifecycleOwner(), new Observer<ListpeddetailedResponse>() {
            @Override
            public void onChanged(ListpeddetailedResponse listpeddetailedResponse) {
                // Aquí puedes actualizar la vista con los detalles del pedido
                if (listpeddetailedResponse != null) {
                    // Actualizar la vista con los detalles del pedido
                    actualizarVistaConDetalles(listpeddetailedResponse);
                    actualizarSumaImportes();
                }
            }
        });
        creapedViewModel.detallesPorPedidoLiveData.observe(
                getViewLifecycleOwner(),
                new Observer<List<ListdetalleResponse>>() {
                    @Override
                    public void onChanged(List<ListdetalleResponse> listdetalleResponses) {
                        // Actualizar la vista con los detalles del pedido
                        listdetailsAdapter.setDetalles(listdetalleResponses);
                    }
        });

        setupViewsCliente();
        setupViewsProducto();
        setupViewsUsuarios();
        setupAgregarDetalleButton();
        borrarDetalle();
        setupModificarPedidoButton();
        setupActualizarListadoButton();
        actualizarSumaImportes();
        setupCancelarButton();

        // Inflate the layout for this fragment
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
                creapedViewModel.sugerenciasPorRazonSocial(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        creapedViewModel.sugerenciasLiveData.observe(getViewLifecycleOwner(), sugerencias -> mostrarSugerenciasDeClientes(sugerencias));
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
                creapedViewModel.sugerenciasPorDescripcion(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        creapedViewModel.sugerenciasproductosLiveData.observe(getViewLifecycleOwner(), sugerencias -> mostrarSugerenciasDeProductos(sugerencias));
    }

    private void setupViewsUsuarios() {
        ptvendedor = binding.ptvendedor;
        ptidusu= binding.ptidusu;

        ptvendedor.setOnItemClickListener((parent, view, position, id) -> {
            ListusuResponse usuarioSeleccionado = (ListusuResponse) parent.getItemAtPosition(position);
            if (usuarioSeleccionado != null) {
                ptidusu.setText(String.valueOf(usuarioSeleccionado.getIdusu()));
            }
        });

        ptvendedor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                creapedViewModel.sugerenciasPorNombre(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        creapedViewModel.sugerenciasusuariosLiveData.observe(getViewLifecycleOwner(), sugerencias -> mostrarSugerenciasDeVendedores(sugerencias));
    }

    private void mostrarSugerenciasDeProductos(List<ListproResponse> sugerencias) {
        ProductoAutoCompleteAdapter adapter = new ProductoAutoCompleteAdapter(requireContext(), sugerencias);
        ptdescripcionproducto.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void mostrarSugerenciasDeVendedores(List<ListusuResponse> sugerencias) {
        UsuarioAutoCompleteAdapter adapter = new UsuarioAutoCompleteAdapter(requireContext(), sugerencias);
        ptvendedor.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void actualizarVistaConDetalles(ListpeddetailedResponse listpeddetailedResponse) {
        ptidped.setText(String.valueOf(listpeddetailedResponse.getIdped()));
        ptidcli.setText(String.valueOf(listpeddetailedResponse.getIdcli()));
        ptidusu.setText(String.valueOf(listpeddetailedResponse.getIdusu()));
        ptdocumento.setText(listpeddetailedResponse.getDocumento());
        ptrazonsocial.setText(listpeddetailedResponse.getRazonsocial());
        ptvendedor.setText(listpeddetailedResponse.getNombre());
        ptfchareparto.setText(listpeddetailedResponse.getFchareparto());
        ptrucdni.setText(listpeddetailedResponse.getRucdni());
        ptdireccion.setText(listpeddetailedResponse.getDireccion());
    }

    private void setupAgregarDetalleButton() {
        if (btnagregardetalle != null){
            btnagregardetalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Paso 1: Obtener valores de ptidproduc y ptcantidadproducto
                    String idproducText = ptidproduc.getText().toString().trim();
                    String cantidadText = ptcantidadproducto.getText().toString().trim();
                    String idpedText = ptidped.getText().toString().trim();

                    if (!idproducText.isEmpty() && !cantidadText.isEmpty() && !idpedText.isEmpty()) {
                        try {
                            // Ambas cadenas no están vacías, puedes convertirlas a números enteros
                            int idproduc = Integer.parseInt(idproducText);
                            int cantidad = Integer.parseInt(cantidadText);
                            int idped = Integer.parseInt(idpedText);

                            // Paso 2: Crear el objeto RegisdetalleRequest
                            RegisdetalleRequest regisdetalleRequest = new RegisdetalleRequest();
                            regisdetalleRequest.setIdproduc(idproduc);
                            regisdetalleRequest.setCantidad(cantidad);
                            regisdetalleRequest.setIdped(idped);

                            // Paso 3: Llamar al método registrarDetalleParcial del viewModel
                            creapedViewModel.registrarDetalleParcialConIdped(regisdetalleRequest);

                            actualizarListadoDetalles();
                            limpiarDetallesCampos();
                            actualizarSumaImportes();
                        } catch (NumberFormatException e) {
                            // Manejar la excepción si ocurre un formato incorrecto al convertir a entero
                            Log.e("ModifypedFragment", "Error al convertir cadena a número", e);
                            // Puedes mostrar un mensaje de error al usuario o tomar otra acción según tus necesidades.
                        }
                    } else {
                        // Manejar el caso en que una o ambas cadenas están vacías
                        // Puedes mostrar un mensaje al usuario o tomar otra acción según tus necesidades.
                    }
                }
            });
        }else{
            Log.e("ModifypedFragment", "btnagregardetalle es nulo");
        }

    }

    private void limpiarDetallesCampos(){
        ptidproduc.getText().clear();
        ptdescripcionproducto.getText().clear();
        ptunidadproducto.getText().clear();
        ptcantidadproducto.getText().clear();
        ptprecioproducto.getText().clear();
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
        ptidped.getText().clear();
    }

    private void borrarDetalle(){
        listdetailsAdapter.setOnDeleteButtonClickListener(new ListdetailsAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int idDetalle) {
                String idpedText = ptidped.getText().toString().trim();
                int idped = Integer.parseInt(idpedText);
                // Llama al método para eliminar el detalle en el ViewModel
                creapedViewModel.eliminarDetalle(idDetalle);
                creapedViewModel.listarDetallesPorPedido(idped);
                actualizarSumaImportes();
            }
        });
    }

    private void setupModificarPedidoButton() {
        if (btnguardar != null) {
            btnguardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Paso 1: Obtener valores de los campos requeridos
                    String idpedText = ptidped.getText().toString().trim();
                    String documento = ptdocumento.getText().toString().trim().toUpperCase();
                    String idcliText = ptidcli.getText().toString().trim();
                    String fchareparto = ptfchareparto.getText().toString().trim();
                    String idusuText = ptidusu.getText().toString().trim();

                    if (!documento.isEmpty() && !idcliText.isEmpty() && !fchareparto.isEmpty() && !idusuText.isEmpty()) {
                        try {
                            int idped = Integer.parseInt(idpedText);
                            // Ambas cadenas no están vacías, puedes convertirlas a números enteros
                            int idcli = Integer.parseInt(idcliText);
                            int idusu = Integer.parseInt(idusuText);

                            // Paso 2: Crear el objeto RegispedRequest
                            ModifypedRequest modifypedRequest = new ModifypedRequest();
                            modifypedRequest.setDocumento(documento);
                            modifypedRequest.setIdcli(idcli);
                            modifypedRequest.setFchareparto(fchareparto);
                            modifypedRequest.setIdusu(idusu);

                            // Paso 3: Llamar al método registrarPedido del viewModel
                            creapedViewModel.modificarPedido(idped, modifypedRequest);
                            regresarALaVistaAnterior();

                        } catch (NumberFormatException e) {
                            // Manejar la excepción si ocurre un formato incorrecto al convertir a entero
                            Log.e("ModifypedFragment", "Error al convertir cadena a número", e);
                            // Puedes mostrar un mensaje de error al usuario o tomar otra acción según tus necesidades.
                        }
                    } else {
                        // Manejar el caso en que una o varias cadenas estén vacías
                        // Puedes mostrar un mensaje al usuario o tomar otra acción según tus necesidades.
                    }
                }
            });
        } else {
            Log.e("ModifypedFragment", "btnguardar es nulo");
        }
    }

    private void setupActualizarListadoButton() {
        btnactualizarlistado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarListadoDetalles();
            }
        });
    }

    private void actualizarListadoDetalles() {
        // Obtén el idped actual
        String idpedText = ptidped.getText().toString().trim();
        if (!idpedText.isEmpty()) {
            try {
                int idped = Integer.parseInt(idpedText);

                // Llama al método para actualizar la lista de detalles
                creapedViewModel.listarDetallesPorPedido(idped);
                actualizarSumaImportes();
            } catch (NumberFormatException e) {
                Log.e("ModifypedFragment", "Error al convertir cadena a número", e);
            }
        }
    }

    private void actualizarSumaImportes() {
        double sumaImportes = listdetailsAdapter.calcularSumaImportes();
        tvtotal.setText(String.format(Locale.getDefault(), "Total: %.2f", sumaImportes));
    }

    private void setupCancelarButton() {
        if (btncancelar != null) {
            btncancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    regresarALaVistaAnterior();
                }
            });
        } else {
            Log.e("ModifypedFragment", "btncancelar es nulo");
        }
    }

    private void regresarALaVistaAnterior() {
        // Obtén el FragmentManager
        FragmentManager fragmentManager = getParentFragmentManager();

        // Realiza la transacción para volver al Fragment anterior
        fragmentManager.popBackStack();
    }

}