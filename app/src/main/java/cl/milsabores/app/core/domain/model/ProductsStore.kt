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
            categoryId = "postres",
            price = 45000,
            imageUrl = "https://www.daisybrand.com/wp-content/uploads/2019/12/DAISY-SOUR-CREAM-CHOCOLATE-CAKE-770x628.jpg"
        ),
        Product(
            id = "2",
            name = "Torta de Avenllanas",
            categoryId = "tortas",
            price = 45000,
            imageUrl = "https://www.ferrerorocher.com/ar/sites/ferrerorocher20_ar/files/2021-07/chocolate-cake-carousel-1.png?t=1761070274"
        ),
        Product(
            id = "3",
            name = "Pie de limon",
            categoryId = "otros",
            price = 45000,
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ11ByHRse34fJHsi1B41cgicbGr8-BsLJ20wYIwtt03FYuLDT0ZYRF7EnVOPL34bAzMiUGIrNEPxHIHBGe28Yxyo6wfIYY0nfA2X9ezE_T&s=10"
        )

    )
}
