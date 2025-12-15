package cl.milsabores.app.core.data.local.purchase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import cl.milsabores.app.core.data.local.purchase.entity.CompraEntity
import cl.milsabores.app.core.data.local.purchase.entity.DetalleCompraEntity
import cl.milsabores.app.core.data.local.purchase.model.CompraConDetalle


data class CompraConDetalle(
    val compra: CompraEntity,
    val items: List<DetalleCompraEntity>
)
@Dao
interface CompraDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCompra(compra: CompraEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDetalles(detalles: List<DetalleCompraEntity>)

    @Transaction
    @Query("SELECT * FROM compras WHERE usuarioId = :userId ORDER BY fechaCreacion DESC")
    suspend fun getComprasConDetalleByUser(userId: Long): List<CompraConDetalle>
}
