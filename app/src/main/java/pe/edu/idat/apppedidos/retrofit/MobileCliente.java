package pe.edu.idat.apppedidos.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MobileCliente {
    private static final String BASE_URL =
            "http://mobileped.jelastic.saveincloud.net/MobileService-Ped/rest/";
            //"http://localhost:8080/MobileService-Ped/rest/";
    private MobileServicio mobileServicio;

    public MobileCliente() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mobileServicio = retrofit.create(MobileServicio.class);
    }

    public MobileServicio getInstance() {
        return mobileServicio;
    }
}
