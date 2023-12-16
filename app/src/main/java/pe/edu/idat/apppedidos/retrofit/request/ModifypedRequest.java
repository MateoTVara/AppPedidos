package pe.edu.idat.apppedidos.retrofit.request;

public class ModifypedRequest {

    private int idped;
    private String documento;
    private int idcli;
    private String fchareparto;
    private int idusu;

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

    public int getIdusu() {
        return idusu;
    }

    public void setIdusu(int idusu) {
        this.idusu = idusu;
    }

    public int getIdcli() {
        return idcli;
    }

    public void setIdcli(int idcli) {
        this.idcli = idcli;
    }

}
