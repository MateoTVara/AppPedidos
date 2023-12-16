package pe.edu.idat.apppedidos.retrofit.request;

public class RegisdetalleRequest {

    private int idped;
    private int idproduc;
    private int cantidad;

    public int getIdped() {
        return idped;
    }

    public void setIdped(int idped) {
        this.idped = idped;
    }

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
