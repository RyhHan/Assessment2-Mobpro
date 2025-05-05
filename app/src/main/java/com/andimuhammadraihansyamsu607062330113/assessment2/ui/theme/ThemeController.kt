package com.andimuhammadraihansyamsu607062330113.assessment2.ui.theme

import androidx.compose.runtime.Composable
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.theme.green.greenTheme
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.theme.normal.normalTheme

@Composable
fun ThemeController(
    isGreenTheme: Boolean,
    content: @Composable () -> Unit
) {
    if (isGreenTheme) {
        greenTheme(content = content)
    } else {
        normalTheme(content = content)
    }
}