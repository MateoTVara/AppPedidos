package pe.edu.idat.apppedidos.retrofit;

import java.util.List;

import pe.edu.idat.apppedidos.retrofit.request.RegisdetalleRequest;
import pe.edu.idat.apppedidos.retrofit.request.RegispedRequest;
import pe.edu.idat.apppedidos.retrofit.response.ListcliResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListdetalleResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListproResponse;
import pe.edu.idat.apppedidos.retrofit.response.RegispedResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MobileServicio {
    @GET("pedido/listarCustom")
    Call<List<ListpedResponse>> listarPedidos();

    @PUT("pedido/agregarParcial")
    Call<RegispedResponse> registroPedido(@Body RegispedRequest regispedRequest);

    @GET("pedido/buscarPorRazonSocial/{razonSocial}")
    Call<ListcliResponse> buscarClientePorRazonSocial(@Path("razonSocial") String razonSocial);

    @GET("cliente/buscarPorRazonSocialParcial/{partialRazonSocial}")
    Call<List<ListcliResponse>> sugerenciasPorRazonSocial(@Path("partialRazonSocial") String razonSocial);

    @GET("producto/buscarPorDescripcionParcial/{partialDescripcion}")
    Call<List<ListproResponse>> sugerenciasPorDescripcion(@Path("partialDescripcion") String descripcion);

    @GET("detalle/listarNoAsignados")
    Call<List<ListdetalleResponse>> listarDetalles();

    @PUT("detalle/registrarDetalleParcial")
    Call<String> registroDetalleParcial(@Body RegisdetalleRequest regisdetalleRequest);

    @DELETE("detalle/eliminar/{id}")
    Call<String> eliminacionDetalle(@Path("id") int id);

    @POST("detalle/asignarIdped/{idped}")
    Call<String> asignacionIdpedADetalles(@Path("idped") int idped);
}
