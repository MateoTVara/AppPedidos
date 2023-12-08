package pe.edu.idat.apppedidos.retrofit;

import java.util.List;

import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MobileServicio {
    @GET("pedido/listarCustom")
    Call<List<ListpedResponse>> listarPedidos();
}
