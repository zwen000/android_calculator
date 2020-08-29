package academy.learnprogramming.calculator

import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException
import kotlinx.android.synthetic.main.activity_main.*

//save key
private const val PENDING_OP = "PendingOperator"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1_Stored"

class MainActivity : AppCompatActivity() {
//    private lateinit var result: EditText
//    private lateinit var newNumber: EditText
//    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    //variables to hold the operands and type of calculation
    private var operand1: Double? = null

    private var pendingOperation = "="



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.result)
//        newNumber = findViewById(R.id.newNumber)
//
//        //Data input buttons
//        val button0: Button = findViewById(R.id.button0)
//        val button1: Button = findViewById(R.id.button1)
//        val button2: Button = findViewById(R.id.button2)
//        val button3: Button = findViewById(R.id.button3)
//        val button4: Button = findViewById(R.id.button4)
//        val button5: Button = findViewById(R.id.button5)
//        val button6: Button = findViewById(R.id.button6)
//        val button7: Button = findViewById(R.id.button7)
//        val button8: Button = findViewById(R.id.button8)
//        val button9: Button = findViewById(R.id.button9)
//        val button_dot: Button = findViewById(R.id.button_dot)
//
//        //Operation buttons
//        val buttonEquals = findViewById<Button>(R.id.button_equal)
//        val buttonDivide = findViewById<Button>(R.id.button_divide)
//        val buttonMultiply = findViewById<Button>(R.id.button_multiply)
//        val buttonMinus = findViewById<Button>(R.id.button_minus)
//        val buttonPlus = findViewById<Button>(R.id.button_plus)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        button_dot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }

            pendingOperation = op
            operation.text = pendingOperation
        }

        button_equal.setOnClickListener(opListener)
        button_divide.setOnClickListener(opListener)
        button_multiply.setOnClickListener(opListener)
        button_minus.setOnClickListener(opListener)
        button_plus.setOnClickListener(opListener)

        button_neg.setOnClickListener ({view ->
            val value = newNumber.text.toString()
            if (value.isEmpty()){
                newNumber.setText("-")
            } else {
                try {
                    var doubleValue = value.toDouble();
                    doubleValue *= -1
                    newNumber.setText(doubleValue.toString())
                } catch (e: NumberFormatException) {
                    // newNumBER was "-" or ".", so clear it
                    newNumber.setText("")
                }
            }

        })

        button_clear.setOnClickListener({view ->
            result.setText("")
            operand1 = null
            newNumber.setText("")
            pendingOperation = "="
            operation.setText(pendingOperation)
        })
    }

    private fun performOperation(value: Double, operation: String) {

        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> if (value == 0.0) {
                    operand1 = Double.NaN
                } else {
                    operand1 = operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PENDING_OP, pendingOperation)
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORED, true)
        }


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getBoolean(STATE_OPERAND1_STORED, false)) {
            operand1 = savedInstanceState.getDouble(STATE_OPERAND1)
        } else {
            operand1 = null
        }

        val savedOperation = savedInstanceState.getString(PENDING_OP, "")
        pendingOperation = savedOperation
        operation.text = pendingOperation
    }
}