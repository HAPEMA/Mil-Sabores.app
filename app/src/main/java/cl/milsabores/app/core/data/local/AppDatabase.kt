package cl.milsabores.app.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cl.milsabores.app.core.data.local.purchase.CompraDao
import cl.milsabores.app.core.data.local.purchase.entity.CompraEntity
import cl.milsabores.app.core.data.local.purchase.entity.DetalleCompraEntity
import cl.milsabores.app.core.data.local.user.UserDao
import cl.milsabores.app.core.data.local.user.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        CompraEntity::class,
        DetalleCompraEntity::class
    ],
    version = 5, // súbelo cuando cambies entidades
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun compraDao(): CompraDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mil_sabores.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                get(context).userDao().insertIgnore(
                                    UserEntity(
                                        nombres = "Admin",
                                        apellidoP = "Mil",
                                        apellidoM = "Sabores",
                                        email = "admin@milsabores.cl",
                                        password = "admin123",
                                        birthDate = "01-01-2000",
                                        rol = "admin"
                                    )
                                )
                            }
                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
