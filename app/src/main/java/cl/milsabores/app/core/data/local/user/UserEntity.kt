package cl.milsabores.app.core.data.local.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombres: String,
    val apellidoP: String,
    val apellidoM: String,
    val email: String,
    val password: String,
    val birthDate: String,
    val rol: String = "cliente",
    val photoUri: String? = null
)
@Entity(tableName = "compras")
data class CompraEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val usuarioId: Long,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val total: Int
)

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