package com.special.place.proto.ui.place.register.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SelectCategory(vm: CategoryEventListener) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text("카테고리", modifier = Modifier.width(120.dp))

        CategoryDropdown(vm)
    }
}

@Composable
fun CategoryDropdown(vm: CategoryEventListener) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    val list by vm.placeCategories.observeAsState(listOf())
    var categoryName = "선택 하여 주세요."

    if (list.isNotEmpty()) {
        vm.setCategory(list[selectedIndex])
        categoryName = list[selectedIndex].name
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = categoryName, modifier = Modifier
            .clickable { expanded = true }
            .padding(12.dp)
            .fillMaxWidth())
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            list.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) { Text(item.name) }

            }
        }
    }
}