package pe.edu.idat.apppedidos.retrofit.response;

public class ListdetalleResponse {

    private int iddetalle;
    private String desproduc;
    private String uniproduc;
    private int cantidad;
    private double importe;

    public int getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(int iddetalle) {
        this.iddetalle = iddetalle;
    }

    public String getDesproduc() {
        return desproduc;
    }

    public void setDesproduc(String desproduc) {
        this.desproduc = desproduc;
    }

    public String getUniproduc() {
        return uniproduc;
    }

    public void setUniproduc(String uniproduc) {
        this.uniproduc = uniproduc;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
