package pe.edu.idat.apppedidos.bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pe.edu.idat.apppedidos.bd.dao.PedidoDao;
import pe.edu.idat.apppedidos.bd.entity.Pedido;

@Database(entities = {Pedido.class}, version=1)
public abstract class MobileRoomDatabase extends RoomDatabase {

    public abstract PedidoDao pedidoDao();
    private static volatile MobileRoomDatabase INSTANCIA;
    public static MobileRoomDatabase getDatabase(final Context context){
        if (INSTANCIA == null){
            synchronized (MobileRoomDatabase.class){
                if (INSTANCIA == null){
                    INSTANCIA = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MobileRoomDatabase.class,
                                    "mobilebd")
                            .build();
                }
            }
        }
        return INSTANCIA;
    }

}
