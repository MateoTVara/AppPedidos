package pe.edu.idat.apppedidos.retrofit;

import java.util.List;

import pe.edu.idat.apppedidos.retrofit.request.ModifypedRequest;
import pe.edu.idat.apppedidos.retrofit.request.RegisdetalleRequest;
import pe.edu.idat.apppedidos.retrofit.request.RegispedRequest;
import pe.edu.idat.apppedidos.retrofit.response.ListcliResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListdetalleResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListpedResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListpeddetailedResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListproResponse;
import pe.edu.idat.apppedidos.retrofit.response.ListusuResponse;
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

    @DELETE("pedido/eliminar/{id}")
    Call<String> eliminacionPedido(@Path("id") int id);

    @GET("pedido/buscarDetallado/{id}")
    Call<ListpeddetailedResponse> buscarPedidoDetallado(@Path("id") int id);

    @POST("pedido/modificarParcial/{idped}")
    Call<String> modificarPedido(@Path("idped") int idped, @Body ModifypedRequest modifypedRequest);

    @GET("cliente/buscarPorRazonSocialParcial/{partialRazonSocial}")
    Call<List<ListcliResponse>> sugerenciasPorRazonSocial(@Path("partialRazonSocial") String razonSocial);

    @GET("producto/buscarPorDescripcionParcial/{partialDescripcion}")
    Call<List<ListproResponse>> sugerenciasPorDescripcion(@Path("partialDescripcion") String descripcion);

    @GET("usuarios/buscarPorNombreParcial/{partialNombre}")
    Call<List<ListusuResponse>> sugerenciasPorNombre(@Path("partialNombre") String nombre);

    @GET("detalle/listarNoAsignados")
    Call<List<ListdetalleResponse>> listarDetallesNoAsignados();

    @GET("detalle/listarPorPedido/{idped}")
    Call<List<ListdetalleResponse>> listarDetallesPorPedido(@Path("idped") int idped);

    @PUT("detalle/registrarDetalleParcial")
    Call<String> registroDetalleParcial(@Body RegisdetalleRequest regisdetalleRequest);

    @PUT("detalle/registrarDetalleParcialConIdped")
    Call<String> registroDetalleParcialConIdped(@Body RegisdetalleRequest regisdetalleRequest);

    @DELETE("detalle/eliminar/{id}")
    Call<String> eliminacionDetalle(@Path("id") int id);

    @DELETE("detalle/eliminarNoAsignados")
    Call<String> eliminacionDetallesNoAsignados();

    @POST("detalle/asignarIdped/{idped}")
    Call<String> asignacionIdpedADetalles(@Path("idped") int idped);
}
