package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.CalculatorCell.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorApp()
        }
    }
}

val colorGreen = Color(0x07, 0xe7, 0x9a)
val colorPink = Color(0xe4, 0x93, 0xb1)
val backgroundColor = Color(0x01, 0x01, 0x01)
val backgroundColorButton = Color(0x17, 0x17, 0x17)

enum class CalculatorCell(val text: String, val color: Color) {
    Clear(text = "C", color = Color.Red),
    RoundQuotes(text = "( )", color = colorPink),
    Percent(text = "%", color = colorPink),
    Div(text = "/", color = colorPink),
    Seven(text = "7", color = colorGreen),
    Eight(text = "8", color = colorGreen),
    Nine(text = "9", color = colorGreen),
    Mul(text = "x", color = colorPink),
    Four(text = "4", color = colorGreen),
    Five(text = "5", color = colorGreen),
    Six(text = "6", color = colorGreen),
    Sub(text = "―", color = colorPink),
    One(text = "1", color = colorGreen),
    Two(text = "2", color = colorGreen),
    Three(text = "3", color = colorGreen),
    Add(text = "+", color = colorPink),
    UnarySub(text = "+/-", color = colorGreen),
    Zero(text = "0", color = colorGreen),
    Comma(text = ",", color = colorGreen),
    Assign(text = "=", color = colorPink)
}

val matrix = listOf(
    listOf(Clear, RoundQuotes, Percent, Div),
    listOf(Seven, Eight, Nine, Mul),
    listOf(Four, Five, Six, Sub),
    listOf(One, Two, Three, Add),
    listOf(UnarySub, Zero, Comma, Assign)
)

class MyViewModel : ViewModel() {
    var cells by mutableStateOf(listOf<Char>())
    var sum by mutableDoubleStateOf(0.0)
}

var counterOpenQuote = 0
var counterCloseQuote = 0
var checkForComma = true
var numberSwitcher = mutableListOf<Double>()
var operatorSwitcher = mutableListOf<String>()

fun onClick(viewModel: MyViewModel, cell: Char) {
    if (viewModel.cells.isEmpty())
        return
    if (viewModel.cells.isNotEmpty()) {
        if (viewModel.cells.last().isDigit() || viewModel.cells.last() == ')') {
            viewModel.cells += cell
            checkForComma = true
        } else if (viewModel.cells.last() in "%/*-+" && cell != viewModel.cells.last()) {
            viewModel.cells = viewModel.cells.dropLast(1) + cell
        }
    }
}
fun onDigit(viewModel: MyViewModel, digit: Char) {
    if (viewModel.cells.isNotEmpty() &&
        viewModel.cells.last() == ')') {
        return
    }
    viewModel.cells += digit

    if(viewModel.cells.isNotEmpty() && viewModel.cells.last() !in "%*-/+("
        && counterOpenQuote == counterCloseQuote) {
        val exp = solveParenthesis(viewModel.cells.joinToString(""))
        viewModel.sum = evaluateExpression(exp)
    }
}
// Assign functions below
fun solveParenthesis(expression: String): String {
    var exp = expression

    while (exp.contains('(')) {
        val start = exp.lastIndexOf('(')
        val end = exp.indexOf(')', start)
        if (end == -1) return ""
        val innerExpression = exp.slice(start + 1..<end)
        val evaluatedValue = evaluateExpression(innerExpression).toString()
        exp = exp.substring(0, start) + evaluatedValue + exp.substring(end + 1)
    }

    return exp
}

fun evaluateInside(tokens: List<String>, start: Int): Double {
    val numbers = mutableListOf<Double>()
    val operators = mutableListOf<String>()
    var counter = start

    while (counter < tokens.size) {
        val token = tokens[counter]
        if (token.any { it.isDigit() }) {
            numbers += token.toDouble()
        } else {
            operators += token
        }
        counter += 1
    }

    return evaluateEverything(numbers, operators)
}
fun indexAfterQuote(expression: String, index: Int): Int {
    var counter = index

    while (counter < expression.length) {
        if(expression[counter] != ')') {
            counter += 1
        } else {
            break
        }
    }

    return counter + 2
}
fun evaluateEverything(numbers: MutableList<Double>, operators: MutableList<String>): Double {
    var i = 1
    var index = 0

    numbers.forEachIndexed { idx, element ->
        if(element < 0) {
            operators.add(idx - 1, "+")
        }
    }

    while(i < numbers.size) {
        when(operators[index]) {
            "*" -> {
                numbers[i - 1] *= numbers[i]
                numbers.removeAt(i)
                operators.removeAt(index)
                index -= 1
                i -= 1
                // 15 % 5 = 15 + 3%
            }
            "/" -> {
                numbers[i - 1] /= numbers[i]
                numbers.removeAt(i)
                operators.removeAt(index)
                index -= 1
                i -= 1
            }
        }
        i += 1
        if(index < operators.size)
            index += 1
    }

    i = 1

    var sum = numbers[0]

    while(i < numbers.size) {
        when(operators[i - 1]) {
            "+" -> sum += numbers[i]
            "-" -> sum -= numbers[i]
        }
        i += 1
    }

    return sum
}
fun evaluateExpression(expression: String): Double {
    var tokens = Regex("-?\\d*[.,]\\d+|-?\\d+|[-+*/()]").findAll(expression).map { it.value }.toList()
    tokens = tokens.map { it.map { char ->
        if(char == ',') "." else char
    }.joinToString("") }

    var index = 0
    val numbers = mutableListOf<Double>()
    val operators = mutableListOf<String>()

    while(index < tokens.size) {
        if(tokens[index].any { it.isDigit() }) {
            numbers += tokens[index].toDouble()
            index += 1
        } else if(tokens[index] == "(") {
            numbers += evaluateInside(tokens, index)
            index = indexAfterQuote(expression, index)
        } else {
            operators += tokens[index]
            index += 1
        }
    }

    numberSwitcher = numbers
    operatorSwitcher = operators

    val result = evaluateEverything(numbers, operators)
    return result
}
fun getResult(viewModel: MyViewModel) {
    if(viewModel.cells.isEmpty() || viewModel.cells.last() in "%*-/+(")
        return
    if(counterOpenQuote != counterCloseQuote)
        return
    val result = solveParenthesis(viewModel.cells.joinToString(""))
    val temp = evaluateExpression(result)
    viewModel.sum = temp
    viewModel.cells = temp.toString().toCharArray().toList()
}
fun displayer(viewModel: MyViewModel): Double {
    if(viewModel.cells.isEmpty() || viewModel.cells.last() in "%*-/+(")
        return viewModel.sum
    return viewModel.sum
}
// Other
fun parenthesis(viewModel: MyViewModel) {
    if (viewModel.cells.isEmpty() || viewModel.cells.last() in "(+-/*%") {
        viewModel.cells += '('
        counterOpenQuote += 1
        return
    }
    if (viewModel.cells.isNotEmpty() && counterOpenQuote > counterCloseQuote) {
        viewModel.cells += ')'
        counterCloseQuote += 1
        if(counterOpenQuote == counterCloseQuote) {
            val exp = solveParenthesis(viewModel.cells.joinToString(""))
            viewModel.sum = evaluateExpression(exp)
        }

        return
    }
}
fun onDelete(viewModel: MyViewModel) {
    if (viewModel.cells.isNotEmpty()) {
        if(viewModel.cells.last() == ')') counterCloseQuote -= 1
        else if(viewModel.cells.last() == '(') counterOpenQuote -= 1
        viewModel.cells = viewModel.cells.dropLast(1)
    }
}
fun onCommaClick(viewModel: MyViewModel) {
    if( viewModel.cells.isNotEmpty() && viewModel.cells.last().isDigit() && checkForComma ) {
        viewModel.cells += ','
        checkForComma = false
    }
}
fun onClearClick(viewModel: MyViewModel) {
    viewModel.cells = emptyList()
    viewModel.sum = 0.0
    counterOpenQuote = 0
    counterCloseQuote = 0
    numberSwitcher = emptyList<Double>().toMutableList()
    operatorSwitcher = emptyList<String>().toMutableList()
}
// 8 + (--6)
fun onUnarySubClick(viewModel: MyViewModel) {
    if(viewModel.cells.isEmpty() || viewModel.cells.isNotEmpty() && viewModel.cells.last() in "(+-*/") return

    val numbers = numberSwitcher
    val operators = operatorSwitcher

    if(viewModel.cells.isNotEmpty() && viewModel.cells.last() == ')') {
        val expression = viewModel.cells.joinToString("")
        val openIndex = expression.lastIndexOf('(')
        val closeIndex = expression.indexOf(')', openIndex)

        if (openIndex != -1 && closeIndex != -1) {
            val innerExpression = expression.substring(openIndex + 1, closeIndex)
            var innerResult = evaluateExpression(innerExpression)
            innerResult = -(innerResult)
            val updatedExpression = expression.substring(0, openIndex) +
                    "(" +
                    (innerResult).toString() +
                    ")" +
                    expression.substring(closeIndex + 1)

            viewModel.cells = updatedExpression.toCharArray().toList()
        }
    } else {
        val expression = viewModel.cells.joinToString("")
        var index = expression.length - 1
        while (index >= 0 && (expression[index].isDigit() || expression[index] == '.'
                    || expression[index] == '-')) {
            index--
        }
        val numberStartIndex = index + 1
        val number = expression.substring(numberStartIndex).toDoubleOrNull()

        if (number != null) {
            val updatedExpression = expression.substring(0, numberStartIndex) +
                    "(-${number})"

            viewModel.cells = updatedExpression.toCharArray().toList()
        }
    }
    if(viewModel.cells.isNotEmpty() && counterOpenQuote == counterCloseQuote) {
        viewModel.sum = evaluateEverything(numbers, operators)
    }
}
fun onPercentClick(viewModel: MyViewModel) {
    if(viewModel.cells.isEmpty() || numberSwitcher.isEmpty() && operatorSwitcher.isEmpty()) return
    if(viewModel.cells.last() in "(+-*/") return

    val numbers = numberSwitcher
    val operators = operatorSwitcher

    val expression = viewModel.cells.joinToString("")
    val openIndex = expression.lastIndexOf('(')
    val closeIndex = expression.indexOf(')', openIndex)

    if (openIndex != -1 && closeIndex != -1) {
        val innerExpression = expression.substring(openIndex + 1, closeIndex)
        val innerResult = evaluateExpression(innerExpression)

        val updatedExpression = expression.substring(0, openIndex) +
                (innerResult / 100).toString() +
                expression.substring(closeIndex + 1)

        viewModel.cells = updatedExpression.toCharArray().toList()
    }

    if(viewModel.cells.isNotEmpty() && viewModel.cells.last() !in "*-/+("
        && counterOpenQuote == counterCloseQuote) {
        viewModel.sum = evaluateEverything(numbers, operators)
    }
}
@Composable
fun CalculatorApp(viewModel: MyViewModel = viewModel()) {
    val onCalculation = { clickedSpot: CalculatorCell ->
        when(clickedSpot) {
            Clear -> onClearClick(viewModel)
            RoundQuotes -> parenthesis(viewModel)
            Percent -> onPercentClick(viewModel)
            Div -> onClick(viewModel, '/')
            Seven -> onDigit(viewModel, '7')
            Eight -> onDigit(viewModel, '8')
            Nine -> onDigit(viewModel, '9')
            Mul -> onClick(viewModel, '*')
            Four -> onDigit(viewModel, '4')
            Five -> onDigit(viewModel, '5')
            Six -> onDigit(viewModel, '6')
            Sub -> onClick(viewModel, '-')
            One -> onDigit(viewModel, '1')
            Two -> onDigit(viewModel, '2')
            Three -> onDigit(viewModel, '3')
            Add -> onClick(viewModel, '+')
            UnarySub -> onUnarySubClick(viewModel)
            Zero -> onDigit(viewModel, '0')
            Comma -> onCommaClick(viewModel)
            Assign -> getResult(viewModel)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = viewModel.cells.joinToString("") { if(it.isDigit() || it == ',' || it == '.') "$it" else " $it " },
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = colorGreen,
                modifier = Modifier
                    .padding(vertical = 50.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${displayer(viewModel)}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = colorGreen.copy(alpha = .5f),
                modifier = Modifier
                    .padding(bottom = 35.dp)
            )
            Icon(
                painter = painterResource(R.drawable.backspace_24),
                contentDescription = null,
                tint = colorPink,
                modifier = Modifier
                    .size(55.dp, 30.dp)
                    .clickable(onClick = { onDelete(viewModel) })
            )
        }
        HorizontalDivider(
            color = backgroundColorButton,
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 35.dp)
        )
        ButtonContainer(
            matrix = matrix,
            onClick = { onCalculation(it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    CalculatorApp()
}

@Composable
fun CalcButton(
    text: String,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.then(
            Modifier
                .fillMaxHeight()
                .background(backgroundColorButton, CircleShape)
                .clip(CircleShape)
                .clickable(onClick = onClick))
    ) {
        Text(
            text = text,
            fontSize = 35.sp,
            color = textColor,
            fontWeight = FontWeight.W900
        )
    }
}

@Composable
fun ButtonContainer(
    matrix: List<List<CalculatorCell>>,
    onClick: (CalculatorCell) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        matrix.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                row.forEach { column ->
                    CalcButton(
                        text = column.text,
                        textColor = column.color,
                        onClick = { onClick(column) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}