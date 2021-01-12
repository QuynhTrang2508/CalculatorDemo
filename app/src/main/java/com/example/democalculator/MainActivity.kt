package com.example.democalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var operand: String? = null
    private var operator: String? = null
    private var numbers: Set<String>? = null
    private var operators: Set<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View?) {
        tvTitle.visibility = View.GONE
        val clicked: Button = p0 as Button
        val label: String = clicked.text.toString()
        val result: String = tvResult.text.toString()
        if (label == getString(R.string.label_AC)) {
            tvResult.text = getString(R.string.label_0)
            operator = null
            operand = null
        } else if (isNumeric(label)) {
            if (isDefaultResult(result) || isOperator(result)) {
                tvResult.text = label
            } else {
                tvResult.text = result + label
            }
        } else if (isOperator(label)) {
            operator = label
            operand = result
            tvResult.text = label
        } else if (label == getString(R.string.label_equal)) {
            tvTitle.visibility = View.VISIBLE
            val display: Double
            if (operator == null || operand == null) {
                return
            }
            val input1: Double = operand!!.toDouble()
            val input2: Double = result.toDouble()
            display = when (operator) {
                getString(R.string.label_add) -> {
                    input1 + input2
                }
                getString(R.string.label_sub) -> {
                    input1 - input2
                }
                getString(R.string.label_mul) -> {
                    input1 * input2
                }
                else -> {
                    input1 / input2
                }
            }
            operand = display.toString()
            tvResult.text = operand
        }
    }

    private fun initNumbers() {
        numbers = HashSet()
        for (i in 0..9) {
            (numbers as HashSet<String>).add(i.toString())
        }
    }

    private fun isNumeric(value: String): Boolean {
        if (numbers == null) {
            initNumbers()
        }
        return numbers!!.contains(value)
    }

    private fun initOperators() {
        operators = HashSet()
        val ops = arrayOf(
            getString(R.string.label_add),
            getString(R.string.label_sub),
            getString(R.string.label_mul),
            getString(R.string.label_div)
        )
        for (operator in ops) {
            (operators as HashSet<String>).add(operator)
        }
    }

    private fun isOperator(value: String): Boolean {
        if (operators == null) {
            initOperators()
        }
        return operators!!.contains(value)
    }

    private fun isDefaultResult(value: String): Boolean {
        return value == getString(R.string.label_0)
    }
}