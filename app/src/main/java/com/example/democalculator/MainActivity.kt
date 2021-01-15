package com.example.democalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var operand = ""
    private var operator = ""
    private val numbers = hashSetOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    private val operators = hashSetOf("+", "-", "x", ":")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View?) {
        textTitle.visibility = View.GONE
        val clicked = if (p0 is Button) p0 else return
        val label = clicked.text.toString()
        val result = textResult.text.toString()
        when {
            label == getString(R.string.title_AC) -> {
                textResult.text = getString(R.string.title_0)
                operator = ""
                operand = ""
            }
            isNumeric(label) -> {
                if (isDefaultResult(result) || isOperator(result)) textResult.text = label
                else textResult.text = result + label
            }
            isOperator(label) -> {
                operator = label
                operand = result
                textResult.text = label
            }
            label == getString(R.string.title_equal) -> {
                textTitle.visibility = View.VISIBLE
                if (operator.isEmpty() || operand.isEmpty()) {
                    return
                }
                val display: Double = clickEqual(operand.toDouble(), result.toDouble())
                operand = display.toString()
                textResult.text = operand
            }
        }
    }

    private fun isNumeric(value: String): Boolean = numbers.contains(value)

    private fun isOperator(value: String): Boolean = operators.contains(value)

    private fun isDefaultResult(value: String): Boolean = value == getString(R.string.title_0)

    private fun clickEqual(input1: Double, input2: Double) = when (operator) {
        getString(R.string.title_add) -> input1 + input2
        getString(R.string.title_sub) -> input1 - input2
        getString(R.string.title_mul) -> input1 * input2
        else -> input1 / input2
    }
}
