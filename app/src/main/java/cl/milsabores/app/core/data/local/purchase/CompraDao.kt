package cl.milsabores.app.core.data.local.purchase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import cl.milsabores.app.core.data.local.purchase.entity.CompraEntity
import cl.milsabores.app.core.data.local.purchase.entity.DetalleCompraEntity

@Dao
interface CompraDao {

    // Inserta compra y devuelve ID generado
    @Insert
    suspend fun insertCompra(compra: CompraEntity): Long

    // Inserta detalle (items)
    @Insert
    suspend fun insertDetalle(detalles: List<DetalleCompraEntity>)

    // Historial por usuario
    @Query("""
        SELECT * FROM compras
        WHERE usuarioId = :usuarioId
        ORDER BY fechaCreacion DESC
    """)
    suspend fun getComprasByUsuario(usuarioId: Long): List<CompraEntity>

    // Detalle por compra
    @Query("""
        SELECT * FROM detalle_compra
        WHERE compraId = :compraId
    """)
    suspend fun getDetalleByCompra(compraId: Long): List<DetalleCompraEntity>

    // (Opcional) borrar una compra completa (cascade borra detalle)
    @Query("DELETE FROM compras WHERE id = :compraId")
    suspend fun deleteCompra(compraId: Long)

    // ✅ Método útil: guardar compra + items en 1 transacción
    @Transaction
    suspend fun createCompraConDetalle(
        compra: CompraEntity,
        detalles: List<DetalleCompraEntity>
    ): Long {
        val compraId = insertCompra(compra)
        val detallesConId = detalles.map { it.copy(compraId = compraId) }
        insertDetalle(detallesConId)
        return compraId
    }
}
