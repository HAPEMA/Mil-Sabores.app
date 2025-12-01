package cl.milsabores.app.core.domain.model

import androidx.compose.runtime.mutableStateListOf

object ProductsStore {

    val categories = mutableStateListOf(
        Category("tortas", "Tortas Circulares"),
        Category("postres", "Postres Individuales"),
        Category("kuchen", "Kuchen"),
        Category("otros", "Otros")
    )

    val products = mutableStateListOf(
        Product(
            id = "1",
            name = "Torta de chocolate",
            categoryId = "tortas",
            price = 45000,
            imageUrl = "https://www.daisybrand.com/wp-content/uploads/2019/12/DAISY-SOUR-CREAM-CHOCOLATE-CAKE-770x628.jpg"
        )
    )
}
