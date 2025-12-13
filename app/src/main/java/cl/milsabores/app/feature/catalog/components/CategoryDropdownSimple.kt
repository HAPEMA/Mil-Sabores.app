package cl.milsabores.app.feature.catalog.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cl.milsabores.app.core.domain.model.ProductsStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownSimple(
    selectedCategoryId: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedName =
        ProductsStore.categories.firstOrNull { it.id == selectedCategoryId }?.name
            ?: "Seleccionar"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Categoría") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ProductsStore.categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategorySelected(category.id)
                        expanded = false
                    }
                )
            }
        }
    }
}
