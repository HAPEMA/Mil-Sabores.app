package cl.milsabores.app.core.data.local.purchase.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalle_compra",
    foreignKeys = [
        ForeignKey(
            entity = CompraEntity::class,
            parentColumns = ["id"],
            childColumns = ["compraId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("compraId")]
)
data class DetalleCompraEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val compraId: Long,
    val productoId: String,
    val nombre: String,
    val precio: Int,
    val cantidad: Int,
    val fotoUrl: String
)
