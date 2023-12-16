package pe.edu.idat.apppedidos.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import pe.edu.idat.apppedidos.bd.MobileRoomDatabase;
import pe.edu.idat.apppedidos.bd.dao.PedidoDao;
import pe.edu.idat.apppedidos.bd.entity.Pedido;

public class PedidoRepository {

    private PedidoDao pedidoDao;

    public PedidoRepository(Application application){
        MobileRoomDatabase db = MobileRoomDatabase.getDatabase(application);
        pedidoDao = db.pedidoDao();
    }

    public LiveData<Pedido> obtenerPedido(){return pedidoDao.obtener();}

    public void registrarPedido(Pedido pedido){
        new registrarAsyncTask(pedidoDao).execute(pedido);
    }

    private static class registrarAsyncTask
            extends AsyncTask<Pedido,Void, Void>{
        private PedidoDao pedidoDao;
        registrarAsyncTask(PedidoDao _pedidoDao){pedidoDao = _pedidoDao;}
        @Override
        protected Void doInBackground(Pedido... pedidos){
            pedidoDao.insertar(pedidos[0]);
            return null;
        }
    }

    public void actualizarPedido(Pedido pedido){
        new actualizarAsyncTask(pedidoDao).execute(pedido);
    }
    private static class actualizarAsyncTask
            extends AsyncTask<Pedido, Void, Void>{
        private PedidoDao pedidoDao;
        actualizarAsyncTask(PedidoDao _pedidoDao){
            pedidoDao = _pedidoDao;
        }
        @Override
        protected Void doInBackground(Pedido... pedidos) {
            pedidoDao.actualizar(pedidos[0]);
            return null;
        }
    }

    public void eliminarPedido(){
        new eliminarAsyncTask(pedidoDao).execute();
    }
    private static class eliminarAsyncTask
            extends AsyncTask<Void, Void, Void>{
        private PedidoDao pedidoDao;
        eliminarAsyncTask(PedidoDao _pedidoDao){
            pedidoDao = _pedidoDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            pedidoDao.eliminar();
            return null;
        }
    }

}
