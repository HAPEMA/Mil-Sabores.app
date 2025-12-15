package cl.milsabores.app.core.data.local.purchase

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import cl.milsabores.app.core.data.local.purchase.model.CompraConDetalle

@Dao
interface CompraDaoRelations {

    @Transaction
    @Query("""
        SELECT * FROM compras
        WHERE usuarioId = :usuarioId
        ORDER BY fechaCreacion DESC
    """)
    suspend fun getComprasConDetalle(usuarioId: Long): List<CompraConDetalle>
}
