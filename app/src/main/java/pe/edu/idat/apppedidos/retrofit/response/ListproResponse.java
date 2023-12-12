package pe.edu.idat.apppedidos.retrofit.response;

public class ListproResponse {

    private int idproduc;
    private String desproduc;
    private String uniproduc;
    private double precio;

    @Override
    public String toString() {
        return desproduc; // o cualquier otro campo que desees mostrar
    }
    public int getIdproduc() {
        return idproduc;
    }

    public void setIdproduc(int idproduc) {
        this.idproduc = idproduc;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
