package pe.edu.idat.apppedidos.retrofit.response;

public class ListcliResponse {

    private int idcli;

    private String razonsocial;

    private String rucdni;

    private String direccion;

    @Override
    public String toString() {
        return razonsocial; // O cualquier campo que desees mostrar
    }
    public int getIdcli() {
        return idcli;
    }

    public void setIdcli(int idcli) {
        this.idcli = idcli;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getRucdni() {
        return rucdni;
    }

    public void setRucdni(String rucdni) {
        this.rucdni = rucdni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
