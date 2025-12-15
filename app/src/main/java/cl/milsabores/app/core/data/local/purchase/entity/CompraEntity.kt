package cl.milsabores.app.core.data.local.purchase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compras")
data class CompraEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val usuarioId: Long,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val total: Int
)
