package pe.edu.idat.apppedidos.retrofit;

import java.util.List;

import pe.edu.idat.apppedidos.retrofit.response.ListcliResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MobileServicio {
    @GET("pedido/listarCustom")
    Call<List<ListpedResponse>> listarPedidos();

    @GET("pedido/buscarPorRazonSocial/{razonSocial}")
    Call<ListcliResponse> buscarClientePorRazonSocial(@Path("razonSocial") String razonSocial);

    @GET("cliente/buscarPorRazonSocialParcial/{partialRazonSocial}")
    Call<List<ListcliResponse>> sugerenciasPorRazonSocial(@Path("partialRazonSocial") String razonSocial);

}
