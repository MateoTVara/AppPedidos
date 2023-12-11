package pe.edu.idat.apppedidos.retrofit.response;

public class ListpedResponse {

    private int idped;
    private String documento;

    private String razonsocial;

    private String rucdni;

    private String direccion;

    private String fchareparto;

    private String nombre;

    public int getIdped() {
        return idped;
    }

    public void setIdped(int idped) {
        this.idped = idped;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFchareparto() {
        return fchareparto;
    }

    public void setFchareparto(String fchareparto) {
        this.fchareparto = fchareparto;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
