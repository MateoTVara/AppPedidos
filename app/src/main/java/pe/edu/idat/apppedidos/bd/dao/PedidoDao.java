package pe.edu.idat.apppedidos.bd.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import pe.edu.idat.apppedidos.bd.entity.Pedido;

@Dao
public interface PedidoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertar(Pedido pedido);
    @Update
    void actualizar(Pedido pedido);
    @Query("DELETE FROM pedido")
    void eliminar();
    @Query("SELECT * FROM pedido LIMIT 1")
    LiveData<Pedido> obtener();

}
