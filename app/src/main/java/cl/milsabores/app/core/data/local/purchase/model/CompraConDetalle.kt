package cl.milsabores.app.core.data.local.purchase.model

import androidx.room.Embedded
import androidx.room.Relation
import cl.milsabores.app.core.data.local.purchase.entity.CompraEntity
import cl.milsabores.app.core.data.local.purchase.entity.DetalleCompraEntity

data class CompraConDetalle(
    @Embedded val compra: CompraEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "compraId"
    )
    val items: List<DetalleCompraEntity>
)
