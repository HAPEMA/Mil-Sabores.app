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
        ),
        Product(
            id = "2",
            name = "Postre de Avellanas",
            categoryId = "postres",
            price = 5000,
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2Ht6Vt_7HQFMqC6RVbTF148k0WRbVKbNBhw&s"
        ),
        Product(
            id = "3",
            name = "Pie De Limon",
            categoryId = "otros",
            price = 8900,
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYs_83A6CvZTz2PqR3b7JDwN2maWn6tXQmUim8-i0LzzJ1jLg6rFo0RZzN5CMBBP04whSLZoYZJKfbrNn4OvFTTFC6RCs1pqh-WvzosKM0CA&s=10"
        )
    )
}
