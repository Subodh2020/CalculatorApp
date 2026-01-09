package com.subodh.calculatorapp.calculator

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.text.iterator

class CalculatorViewModel : ViewModel() {
    private val _display = MutableStateFlow("0")
    val display: StateFlow<String> = _display.asStateFlow()

    private var currentInput = "0"
    private var expression = StringBuilder()
    private var previousInput: String? = null
    private var operation: Operation? = null
    private var shouldResetDisplay = false
    private var showResult = false

    fun onNumberClick(number: String) {
        if (showResult) {
            // Reset everything if user starts typing after showing result
            reset()
        }
        
        if (shouldResetDisplay) {
            currentInput = if (number == ".") "0." else number
            shouldResetDisplay = false
        } else {
            currentInput = when {
                currentInput == "0" && number != "." -> number
                number == "." && currentInput.contains(".") -> currentInput // Prevent multiple decimal points
                currentInput == "0" && number == "." -> "0."
                else -> currentInput + number
            }
        }
        updateDisplay()
    }

    fun onOperationClick(op: Operation) {
        if (showResult) {
            // If showing result, start new expression with the result
            expression.clear()
            expression.append(currentInput)
            showResult = false
        }
        
        // Add current input to expression if expression is empty or doesn't end with a number
        if (expression.isEmpty()) {
            // First operation, add current input to expression
            expression.append(currentInput)
        } else if (!shouldResetDisplay) {
            // User just finished typing a number, add it to expression
            expression.append(currentInput)
        }
        // If shouldResetDisplay is true, the number is already in expression from previous operation
        
        // Add operation symbol to expression
        val opSymbol = when (op) {
            Operation.ADD -> "+"
            Operation.SUBTRACT -> "−"
            Operation.MULTIPLY -> "×"
            Operation.DIVIDE -> "÷"
        }
        expression.append(opSymbol)
        
        previousInput = currentInput
        operation = op
        shouldResetDisplay = true
        updateDisplay()
    }

    fun onEqualsClick() {
        if (previousInput != null && operation != null && !showResult) {
            // Build complete expression for calculation
            val fullExpression = StringBuilder(expression)
            if (!shouldResetDisplay) {
                // Current input is being typed, add it to expression
                fullExpression.append(currentInput)
            } else {
                // Expression ends with operator, currentInput is the number after operator
                fullExpression.append(currentInput)
            }
            
            // Calculate final result
            val result = calculateFinal(fullExpression.toString())
            
            // Show expression with result
            fullExpression.append("=")
            fullExpression.append(result)
            _display.value = fullExpression.toString()
            
            // Set current input to result for next operation
            currentInput = result
            previousInput = null
            operation = null
            shouldResetDisplay = true
            showResult = true
        }
    }

    fun onClearClick() {
        reset()
        _display.value = "0"
    }

    fun onDeleteClick() {
        if (showResult) {
            reset()
            _display.value = "0"
            return
        }

        currentInput = if (currentInput.length > 1) {
            currentInput.dropLast(1)
        } else {
            "0"
        }
        updateDisplay()
    }

    private fun updateDisplay() {
        if (expression.isNotEmpty() && !showResult) {
            // Show expression with current input being typed
            val displayText = if (shouldResetDisplay) {
                expression.toString()
            } else {
                expression.toString() + currentInput
            }
            _display.value = displayText
        } else {
            _display.value = currentInput
        }
    }

    private fun calculateFinal(exprStr: String): String {
        // Parse and evaluate the expression
        val parts = mutableListOf<String>()
        var currentPart = StringBuilder()
        
        // Split expression into numbers and operators
        for (char in exprStr) {
            when (char) {
                '+', '−', '×', '÷' -> {
                    if (currentPart.isNotEmpty()) {
                        parts.add(currentPart.toString())
                        currentPart.clear()
                    }
                    parts.add(char.toString())
                }
                else -> currentPart.append(char)
            }
        }
        if (currentPart.isNotEmpty()) {
            parts.add(currentPart.toString())
        }
        
        // Evaluate expression left to right
        if (parts.isEmpty()) return "Error"
        if (parts.size < 3) return "Error" // Need at least number, operator, number
        
        var result = parts[0].toDoubleOrNull() ?: return "Error"
        
        var i = 1
        while (i < parts.size - 1) {
            val operator = parts[i]
            val nextNum = parts[i + 1].toDoubleOrNull() ?: return "Error"
            
            result = when (operator) {
                "+" -> result + nextNum
                "−" -> result - nextNum
                "×" -> result * nextNum
                "÷" -> if (nextNum != 0.0) result / nextNum else Double.NaN
                else -> return "Error"
            }
            
            i += 2
        }
        
        return formatResult(result)
    }

    @SuppressLint("DefaultLocale")
    private fun formatResult(result: Double): String {
        return if (result.isNaN() || result.isInfinite()) {
            "Error"
        } else {
            if (result % 1.0 == 0.0) {
                result.toInt().toString()
            } else {
                // Limit decimal places
                String.format("%.10f", result).trimEnd('0').trimEnd('.')
            }
        }
    }

    private fun reset() {
        currentInput = "0"
        expression.clear()
        previousInput = null
        operation = null
        shouldResetDisplay = false
        showResult = false
    }
}

enum class Operation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}

