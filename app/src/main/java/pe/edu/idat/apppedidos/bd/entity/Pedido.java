package pe.edu.idat.apppedidos.bd.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pedido")
public class Pedido {

    @PrimaryKey
    private int idped;
    private int idcli;
    private int idusu;
    private String documento;
    private String razonsocial;
    private String nombre;
    private String rucdni;
    private String fchareparto;
    private String direccion;

    public int getIdped() {
        return idped;
    }

    public void setIdped(int idped) {
        this.idped = idped;
    }

    public int getIdcli() {
        return idcli;
    }

    public void setIdcli(int idcli) {
        this.idcli = idcli;
    }

    public int getIdusu() {
        return idusu;
    }

    public void setIdusu(int idusu) {
        this.idusu = idusu;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRucdni() {
        return rucdni;
    }

    public void setRucdni(String rucdni) {
        this.rucdni = rucdni;
    }

    public String getFchareparto() {
        return fchareparto;
    }

    public void setFchareparto(String fchareparto) {
        this.fchareparto = fchareparto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
