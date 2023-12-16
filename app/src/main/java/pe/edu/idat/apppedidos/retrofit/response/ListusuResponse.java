package pe.edu.idat.apppedidos.retrofit.response;

public class ListusuResponse {

    private int idusu;
    private String usuario;
    private String contrasenia;
    private String nombre;

    @Override
    public String toString() {
        return nombre; // o cualquier otro campo que desees mostrar
    }

    public int getIdusu() {
        return idusu;
    }

    public void setIdusu(int idusu) {
        this.idusu = idusu;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
