package pe.edu.idat.apppedidos.viewmodel;

import android.app.Application;

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
                        listMutableLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<ListpedResponse>> call, Throwable t) {

                    }
                });
    }
}
