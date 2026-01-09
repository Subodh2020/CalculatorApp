package com.subodh.calculatorapp.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.subodh.calculatorapp.theme.ThemeViewModel

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel(),
    themeViewModel: ThemeViewModel
) {
    val display by viewModel.display.collectAsState()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Theme Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = if (isDarkTheme == true) "🌙" else "☀️",
                    fontSize = 20.sp
                )
                Switch(
                    checked = isDarkTheme == true,
                    onCheckedChange = { themeViewModel.setDarkTheme(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }

        // Display
        DisplaySection(display = display)

        // Buttons
        ButtonGrid(viewModel = viewModel)
    }
}

@Composable
fun DisplaySection(display: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        // Adjust font size based on expression length
        val fontSize = when {
            display.length > 20 -> 20.sp
            display.length > 15 -> 24.sp
            display.length > 10 -> 32.sp
            else -> 32.sp
        }
        
        Text(
            text = display,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            maxLines = 3,
            lineHeight = fontSize * 1.2f
        )
    }
}

@Composable
fun ButtonGrid(viewModel: CalculatorViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Row 1: Clear, Delete, Divide
        CalculatorRow(
            buttons = listOf(
                CalculatorButton("C", ButtonType.FUNCTION) { viewModel.onClearClick() },
                CalculatorButton("⌫", ButtonType.FUNCTION) { viewModel.onDeleteClick() },
                CalculatorButton("÷", ButtonType.OPERATION) { viewModel.onOperationClick(Operation.DIVIDE) }
            )
        )

        // Row 2: 7, 8, 9, Multiply
        CalculatorRow(
            buttons = listOf(
                CalculatorButton("7", ButtonType.NUMBER) { viewModel.onNumberClick("7") },
                CalculatorButton("8", ButtonType.NUMBER) { viewModel.onNumberClick("8") },
                CalculatorButton("9", ButtonType.NUMBER) { viewModel.onNumberClick("9") },
                CalculatorButton("×", ButtonType.OPERATION) { viewModel.onOperationClick(Operation.MULTIPLY) }
            )
        )

        // Row 3: 4, 5, 6, Subtract
        CalculatorRow(
            buttons = listOf(
                CalculatorButton("4", ButtonType.NUMBER) { viewModel.onNumberClick("4") },
                CalculatorButton("5", ButtonType.NUMBER) { viewModel.onNumberClick("5") },
                CalculatorButton("6", ButtonType.NUMBER) { viewModel.onNumberClick("6") },
                CalculatorButton("−", ButtonType.OPERATION) { viewModel.onOperationClick(Operation.SUBTRACT) }
            )
        )

        // Row 4: 1, 2, 3, Add
        CalculatorRow(
            buttons = listOf(
                CalculatorButton("1", ButtonType.NUMBER) { viewModel.onNumberClick("1") },
                CalculatorButton("2", ButtonType.NUMBER) { viewModel.onNumberClick("2") },
                CalculatorButton("3", ButtonType.NUMBER) { viewModel.onNumberClick("3") },
                CalculatorButton("+", ButtonType.OPERATION) { viewModel.onOperationClick(Operation.ADD) }
            )
        )

        // Row 5: 0, Decimal, Equals
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CalculatorButton(
                text = "0",
                type = ButtonType.NUMBER,
                onClick = { viewModel.onNumberClick("0") },
                modifier = Modifier.weight(2f)
            )
            CalculatorButton(
                text = ".",
                type = ButtonType.NUMBER,
                onClick = { viewModel.onNumberClick(".") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                text = "=",
                type = ButtonType.EQUALS,
                onClick = { viewModel.onEqualsClick() },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CalculatorRow(buttons: List<CalculatorButton>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        buttons.forEach { button ->
            // Give operation buttons more width (1.3x) compared to other buttons
            val weight = if (button.type == ButtonType.OPERATION) {
                1.3f
            } else {
                1f
            }
            CalculatorButton(
                text = button.text,
                type = button.type,
                onClick = button.onClick,
                modifier = Modifier.weight(weight)
            )
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    type: ButtonType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (type) {
        ButtonType.NUMBER -> MaterialTheme.colorScheme.surface
        ButtonType.OPERATION -> MaterialTheme.colorScheme.primary
        ButtonType.FUNCTION -> MaterialTheme.colorScheme.secondary
        ButtonType.EQUALS -> MaterialTheme.colorScheme.tertiary
    }

    val contentColor = when (type) {
        ButtonType.NUMBER -> MaterialTheme.colorScheme.onSurface
        ButtonType.OPERATION -> MaterialTheme.colorScheme.onPrimary
        ButtonType.FUNCTION -> MaterialTheme.colorScheme.onSecondary
        ButtonType.EQUALS -> MaterialTheme.colorScheme.onTertiary
    }

    // Use circular shape for numbers and function buttons, rounded corners for operations and equals
    val buttonShape = when (type) {
        ButtonType.NUMBER, ButtonType.FUNCTION -> CircleShape
        ButtonType.OPERATION, ButtonType.EQUALS -> RoundedCornerShape(16.dp)
    }

    // For number and function buttons, use fixed size to ensure perfect circle
    // For operation and equals buttons, use the provided modifier (with weight)
    val buttonModifier = when (type) {
        ButtonType.NUMBER, ButtonType.FUNCTION -> {
            Modifier
                .size(80.dp)
                .clip(buttonShape)
                .background(backgroundColor)
        }
        ButtonType.OPERATION, ButtonType.EQUALS -> {
            modifier
                .height(80.dp)
                .clip(buttonShape)
                .background(backgroundColor)
        }
    }

    TextButton(
        onClick = onClick,
        modifier = buttonModifier,
        shape = buttonShape
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

data class CalculatorButton(
    val text: String,
    val type: ButtonType,
    val onClick: () -> Unit
)

enum class ButtonType {
    NUMBER, OPERATION, FUNCTION, EQUALS
}

