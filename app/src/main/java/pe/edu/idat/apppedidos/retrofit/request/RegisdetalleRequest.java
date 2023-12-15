package pe.edu.idat.apppedidos.retrofit.request;

public class RegisdetalleRequest {

    private int idproduc;
    private int cantidad;

    public int getIdproduc() {
        return idproduc;
    }

    public void setIdproduc(int idproduc) {
        this.idproduc = idproduc;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
