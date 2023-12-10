package pe.edu.idat.apppedidos.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import pe.edu.idat.apppedidos.retrofit.MobileCliente;
import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListpedViewModel extends AndroidViewModel {

    public MutableLiveData<List<ListpedResponse>> listMutableLiveData
            = new MutableLiveData<>();

    public ListpedViewModel(@NonNull Application application) {
        super(application);
    }

    public void listarPedidos(){
        new MobileCliente().getInstance().listarPedidos()
                .enqueue(new Callback<List<ListpedResponse>>() {
                    @Override
                    public void onResponse(Call<List<ListpedResponse>> call, Response<List<ListpedResponse>> response) {
                        if (response.isSuccessful()) {
                            // La respuesta es exitosa, actualizar los datos
                            listMutableLiveData.setValue(response.body());
                        } else {
                            // La respuesta no fue exitosa, manejar el error
                            handleErrorResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ListpedResponse>> call, Throwable t) {
                        handleFailure(t);
                    }
                });
    }

    // Método para manejar errores de respuesta
    private void handleErrorResponse(Response<List<ListpedResponse>> response) {
        // Aquí puedes manejar los errores específicos de la respuesta
        // Por ejemplo, imprimir en el registro
        Log.e("ListpedViewModel", "Error en la respuesta: " + response.code());

        // También puedes agregar lógica adicional según tus necesidades
        // Por ejemplo, mostrar un mensaje de error en la interfaz de usuario
    }

    // Método para manejar errores de conexión
    private void handleFailure(Throwable t) {
        // Aquí puedes manejar los errores de conexión
        // Por ejemplo, imprimir en el registro
        Log.e("ListpedViewModel", "Error en la conexión", t);

        // También puedes agregar lógica adicional según tus necesidades
        // Por ejemplo, mostrar un mensaje de error en la interfaz de usuario
    }
}
