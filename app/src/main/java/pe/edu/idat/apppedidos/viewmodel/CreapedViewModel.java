package pe.edu.idat.apppedidos.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.apppedidos.retrofit.MobileCliente;
import pe.edu.idat.apppedidos.retrofit.request.ModifypedRequest;
import pe.edu.idat.apppedidos.retrofit.request.RegisdetalleRequest;
import pe.edu.idat.apppedidos.retrofit.request.RegispedRequest;
import pe.edu.idat.apppedidos.retrofit.response.ListcliResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListdetalleResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListpeddetailedResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListproResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListusuResponse;
import pe.edu.idat.apppedidos.retrofit.response.RegispedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreapedViewModel extends AndroidViewModel {

    public MutableLiveData<List<ListcliResponse>> sugerenciasLiveData
            = new MutableLiveData<>();

    public MutableLiveData<List<ListproResponse>> sugerenciasproductosLiveData
            = new MutableLiveData<>();

    public MutableLiveData<List<ListusuResponse>> sugerenciasusuariosLiveData
            = new MutableLiveData<>();

    public MutableLiveData<RegispedResponse> regispedResponseMutableLiveData
            = new MutableLiveData<>();

    public MutableLiveData<String> mensajeRegistroLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ListdetalleResponse>> detallesPorPedidoLiveData
            = new MutableLiveData<>();

    public MutableLiveData<String> mensajeModificacionPedidoLiveData = new MutableLiveData<>();



    public CreapedViewModel(@NonNull Application application) {
        super(application);
    }

    public void sugerenciasPorRazonSocial(String razonSocial) {

        new MobileCliente().getInstance().sugerenciasPorRazonSocial(razonSocial)
                .enqueue(new Callback<List<ListcliResponse>>() {
                    @Override
                    public void onResponse(Call<List<ListcliResponse>> call, Response<List<ListcliResponse>> response) {
                        if (response.isSuccessful()) {
                            sugerenciasLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ListcliResponse>> call, Throwable t) {
                        handleFailure(t);
                    }
                });
    }

    private void handleErrorResponse(Response<ListcliResponse> response) {
        Log.e("CreapedViewModel", "Error en la respuesta: " + response.code());
    }

    private void handleFailure(Throwable t) {
        Log.e("CreapedViewModel", "Error en la conexión", t);
    }

    public void sugerenciasPorDescripcion(String descripcion){
        new MobileCliente().getInstance().sugerenciasPorDescripcion(descripcion)
                .enqueue(new Callback<List<ListproResponse>>() {
                    @Override
                    public void onResponse(Call<List<ListproResponse>> call, Response<List<ListproResponse>> response) {
                        if (response.isSuccessful()) {
                            sugerenciasproductosLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ListproResponse>> call, Throwable t) {

                    }
                });
    }

    public void sugerenciasPorNombre(String nombre){
        new MobileCliente().getInstance().sugerenciasPorNombre(nombre)
                .enqueue(new Callback<List<ListusuResponse>>() {
                    @Override
                    public void onResponse(Call<List<ListusuResponse>> call, Response<List<ListusuResponse>> response) {
                        if (response.isSuccessful()){
                            sugerenciasusuariosLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ListusuResponse>> call, Throwable t) {

                    }
                });
    }

    public void registrarPedido(RegispedRequest regispedRequest) {
        new MobileCliente().getInstance().registroPedido(regispedRequest)
                .enqueue(new Callback<RegispedResponse>() {
                    @Override
                    public void onResponse(Call<RegispedResponse> call, Response<RegispedResponse> response) {
                        if (response.isSuccessful()) {
                            RegispedResponse regispedResponse = response.body();
                            int idped = regispedResponse.getIdped();

                            // Asignar idped a detalles no asignados
                            asignarIdpedADetalles(idped);

                            regispedResponseMutableLiveData.setValue(regispedResponse);
                            mensajeRegistroLiveData.setValue("Registro de pedido exitoso");
                        }
                    }

                    @Override
                    public void onFailure(Call<RegispedResponse> call, Throwable t) {
                        handleFailure(t);
                    }
                });
    }



    // Método privado para manejar errores en respuestas de tipo String
    private void handleErrorResponseString(Response<String> response) {
        Log.e("CreapedViewModel", "Error en la respuesta: " + response.code());
        // Puedes manejar el error de otra manera si es necesario
    }

    public void registrarDetalleParcial(RegisdetalleRequest regisdetalleRequest) {
        Log.d("MobileCliente", "Enviando solicitud de registro de detalle parcial...");

        new MobileCliente().getInstance().registroDetalleParcial(regisdetalleRequest)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("MobileCliente", "Respuesta recibida.");

                        if (response.isSuccessful()) {
                            // Puedes hacer algo con la respuesta si es necesario
                            mensajeRegistroLiveData.setValue("Registro de detalle parcial exitoso");
                        } else {
                            // Manejar errores en la respuesta
                            handleErrorResponseString(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("MobileCliente", "Error en la conexión", t);
                        handleFailure(t);
                    }
                });
    }

    public void eliminarDetalle(int idDetalle) {
        new MobileCliente().getInstance().eliminacionDetalle(idDetalle)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            mensajeRegistroLiveData.setValue("Detalle eliminado exitosamente");
                        } else {
                            // Manejar errores en la respuesta
                            handleErrorResponseString(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        handleFailure(t);
                    }
                });
    }

    public void eliminarDetallesNoAsignados(){
        new MobileCliente().getInstance().eliminacionDetallesNoAsignados()
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    public void asignarIdpedADetalles(int idped) {
        new MobileCliente().getInstance().asignacionIdpedADetalles(idped)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            // Puedes hacer algo con la respuesta si es necesario
                            mensajeRegistroLiveData.setValue("Asignación de Idped a detalles exitosa");
                        } else {
                            // Manejar errores en la respuesta
                            handleErrorResponseString(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("MobileCliente", "Error en la conexión", t);
                        handleFailure(t);
                    }
                });
    }

    public void modificarPedido(int idPedido, ModifypedRequest modifypedRequest) {
        new MobileCliente().getInstance().modificarPedido(idPedido, modifypedRequest)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            // Puedes hacer algo con la respuesta si es necesario
                            mensajeModificacionPedidoLiveData.setValue("Modificación de pedido exitosa");
                        } else {
                            // Manejar errores en la respuesta
                            handleErrorResponseString(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("MobileCliente", "Error en la conexión", t);
                        handleFailure(t);
                    }
                });
    }

    // Método para interactuar con el método listarDetallesPorPedido
    public void listarDetallesPorPedido(int idPedido) {
        new MobileCliente().getInstance().listarDetallesPorPedido(idPedido)
                .enqueue(new Callback<List<ListdetalleResponse>>() {
                    @Override
                    public void onResponse(Call<List<ListdetalleResponse>> call, Response<List<ListdetalleResponse>> response) {
                        if (response.isSuccessful()) {
                            detallesPorPedidoLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ListdetalleResponse>> call, Throwable t) {
                        handleFailure(t);
                    }
                });
    }

    public void registrarDetalleParcialConIdped(RegisdetalleRequest regisdetalleRequest) {
        Log.d("MobileCliente", "Enviando solicitud de registro de detalle parcial...");

        new MobileCliente().getInstance().registroDetalleParcialConIdped(regisdetalleRequest)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("MobileCliente", "Respuesta recibida.");

                        if (response.isSuccessful()) {
                            // Puedes hacer algo con la respuesta si es necesario
                            mensajeRegistroLiveData.setValue("Registro de detalle parcial exitoso");
                        } else {
                            // Manejar errores en la respuesta
                            handleErrorResponseString(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("MobileCliente", "Error en la conexión", t);
                        handleFailure(t);
                    }
                });
    }

}
