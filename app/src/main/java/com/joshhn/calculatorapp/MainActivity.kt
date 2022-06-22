package com.joshhn.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var lastDot: Boolean = false
    private var lastNumeric: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastDot = false
        lastNumeric = true
    }

    fun onCLR(view: View){
        tvInput?.text = ""
    }

    fun onPercent(view: View){
        if(lastNumeric && !lastDot){
            onEqual(view)
            var tvValue = ""

                tvInput?.text?.let{
                tvValue = (it.toString().toDouble()/100).toString()
            }
            tvInput?.text = tvValue
        }
    }

    fun onSwitch(view: View){
        if(lastNumeric && !lastDot){
            onEqual(view)
            var tvValue = tvInput?.text

            tvValue?.let {
                tvValue = if (it.toString().startsWith("-")){
                    it.toString().substring(1)
                }else{
                    "-$it"
                }
            }
            tvInput?.text =tvValue
        }
    }

    fun onDecimalPoint(view: View){
        if((tvInput?.text!!.isEmpty()) || (lastNumeric && !lastDot)){
            tvInput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    val splitvalue = tvValue.split("-")
                    var firstValue = splitvalue[0]
                    var secondValue = splitvalue[1]

                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }

                    var res = firstValue.toDouble() - secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(res.toString())

                }else if(tvValue.contains("+")){
                    val splitvalue = tvValue.split("+")
                    var firstValue = splitvalue[0]
                    var secondValue = splitvalue[1]

                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }

                    var res = firstValue.toDouble() + secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(res.toString())

                }else if(tvValue.contains("*")){
                    val splitvalue = tvValue.split("*")
                    var firstValue = splitvalue[0]
                    var secondValue = splitvalue[1]

                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }

                    var res = firstValue.toDouble() * secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(res.toString())
                }else if(tvValue.contains("/")){
                    val splitvalue = tvValue.split("/")
                    var firstValue = splitvalue[0]
                    var secondValue = splitvalue[1]

                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }

                    var res = firstValue.toDouble() / secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(res.toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(res: String): String{
        var value = res
        if(res.contains(".0")){
            value = res.substring(0, res.length -2)
        }
        return value
    }
}