package pe.edu.idat.apppedidos.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import pe.edu.idat.apppedidos.retrofit.MobileCliente;
import pe.edu.idat.apppedidos.retrofit.MobileServicio;
import pe.edu.idat.apppedidos.retrofit.response.ListcliResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreapedViewModel extends AndroidViewModel {

    public MutableLiveData<ListcliResponse> clienteMutableLiveData
            = new MutableLiveData<>();
    public MutableLiveData<List<ListcliResponse>> sugerenciasLiveData
            = new MutableLiveData<>();

    public CreapedViewModel(@NonNull Application application) {
        super(application);
    }

    public void buscarClientePorRazonSocialYSugerencias(String razonSocial) {
        new MobileCliente().getInstance().buscarClientePorRazonSocial(razonSocial)
                .enqueue(new Callback<ListcliResponse>() {
                    @Override
                    public void onResponse(Call<ListcliResponse> call, Response<ListcliResponse> response) {
                        if (response.isSuccessful()) {
                            clienteMutableLiveData.setValue(response.body());
                        } else {
                            handleErrorResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListcliResponse> call, Throwable t) {
                        handleFailure(t);
                    }
                });

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

}
