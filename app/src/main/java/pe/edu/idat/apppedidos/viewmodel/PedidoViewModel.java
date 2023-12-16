package pe.edu.idat.apppedidos.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pe.edu.idat.apppedidos.bd.entity.Pedido;
import pe.edu.idat.apppedidos.repository.PedidoRepository;

public class PedidoViewModel extends AndroidViewModel {

    private LiveData<Pedido> pedidoLiveData;
    private MutableLiveData<Pedido> pedidoSeleccionado = new MutableLiveData<>();
    private PedidoRepository pedidoRepository;
    public PedidoViewModel(@NonNull Application application) {
        super(application);
        pedidoRepository =  new PedidoRepository(application);
    }

    public LiveData<Pedido> obtenerPedido(){
        return pedidoRepository.obtenerPedido();
    }
    public void insertarPedido(Pedido pedido){pedidoRepository.registrarPedido(pedido);}
    public void actualizarPedido(Pedido pedido){pedidoRepository.actualizarPedido(pedido);}
    public void eliminarPedido(){pedidoRepository.eliminarPedido();}

    public void setPedidoSeleccionado(Pedido pedido) {
        pedidoSeleccionado.setValue(pedido);
    }

    public LiveData<Pedido> getPedidoSeleccionado() {
        return pedidoSeleccionado;
    }

}
