package com.brainerx.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var GLOBAL_RESULT = 0.0
    var CURRENT_OPERATION = ""
    var SECOND_OPERAND = 0.0
    var OPERATION_CLICKED = false
    var TYPING = false
    lateinit var OPERATIONS : ArrayList<HistoryModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtons()
        OPERATIONS= ArrayList()
        calculation_history.adapter=HistoryAdapter(OPERATIONS,this)
        val manager = LinearLayoutManager(this)
        manager.reverseLayout = true
        //manager.stackFromEnd=true
        calculation_history.layoutManager = manager
    }

    fun initButtons() {
        one.setOnClickListener {
            clickDigit("1")
        }
        two.setOnClickListener {
            clickDigit("2")
        }
        three.setOnClickListener {
            clickDigit("3")
        }
        four.setOnClickListener {
            clickDigit("4")
        }
        five.setOnClickListener {
            clickDigit("5")
        }
        six.setOnClickListener {
            clickDigit("6")
        }
        seven.setOnClickListener {
            clickDigit("7")
        }
        eight.setOnClickListener {
            clickDigit("8")
        }
        nine.setOnClickListener {
            clickDigit("9")
        }
        zero.setOnClickListener {
            clickDigit("0")
        }
        delete.setOnClickListener {
            removeDigit()
        }
        plus.setOnClickListener {
            operationClicked("+")

        }
        minus.setOnClickListener {
            operationClicked("-")
        }
        multiply.setOnClickListener {
            operationClicked("*")
        }
        devide.setOnClickListener {
            operationClicked("/")
        }
        equal.setOnClickListener {
            calcResult()
        }
        ac.setOnClickListener {
            reset()
        }
        sign.setOnClickListener {
            addSign()
        }
        point.setOnClickListener {
            addPoint()
        }
    }

    fun clickDigit(numberClicked: String) {
        if (!OPERATION_CLICKED && !TYPING) {
            result.text = numberClicked // in case we did an operation then we clicked a digit
            GLOBAL_RESULT = 0.0
        }else if (result.text.toString() == "0") {
            result.text = numberClicked
        } else if (OPERATION_CLICKED) {
            OPERATION_CLICKED = false
            result.text = numberClicked
        } else if (result.text=="ERROR"){
            reset()
        } else {
            result.text = "${result.text}${numberClicked}"
        }
        TYPING = true

    }

    fun operationClicked(operation: String) {
        if(result.text!="ERROR"){
            if(CURRENT_OPERATION==""){ // In case we didn't click any operation
                CURRENT_OPERATION = operation
                OPERATION_CLICKED = true
                if(GLOBAL_RESULT==0.0){
                    GLOBAL_RESULT=result.text.toString().toDouble()
                }else{
                    calcResult()
                    CURRENT_OPERATION=""
                }
                TYPING=false
            }else{ // in case we clicked before an operation and we
                // clicked new one we have to calculate the previous
                // operation before going to the next op
                OPERATION_CLICKED = true
                if(GLOBAL_RESULT==0.0){
                    GLOBAL_RESULT=result.text.toString().toDouble()
                }else{
                    calcResult()
                    CURRENT_OPERATION = operation
                }
                TYPING=false
            }

        }


    }

    fun calcResult() {

        if (CURRENT_OPERATION == "+") {
            SECOND_OPERAND = result.text.toString().toDouble()
            if (TYPING) {
                OPERATIONS.add(HistoryModel("${GLOBAL_RESULT}+${SECOND_OPERAND}"))
                calculation_history.adapter!!.notifyDataSetChanged()
                GLOBAL_RESULT += SECOND_OPERAND
                result.text = "${GLOBAL_RESULT}"
                TYPING = false
            }

        }else if (CURRENT_OPERATION== "-"){
            SECOND_OPERAND = result.text.toString().toDouble()
            if (TYPING) {
                OPERATIONS.add(HistoryModel("${GLOBAL_RESULT}-${SECOND_OPERAND}"))
                calculation_history.adapter!!.notifyDataSetChanged()
                GLOBAL_RESULT -= SECOND_OPERAND
                result.text = "${GLOBAL_RESULT}"
                TYPING = false
            }
        }else if (CURRENT_OPERATION=="/"){
            OPERATIONS.add(HistoryModel("${GLOBAL_RESULT}/${SECOND_OPERAND}"))
            calculation_history.adapter!!.notifyDataSetChanged()
            SECOND_OPERAND = result.text.toString().toDouble()
            if (TYPING) {
                if(SECOND_OPERAND==0.0){
                    result.text = "ERROR"
                    GLOBAL_RESULT=0.0
                }else{
                    GLOBAL_RESULT /= SECOND_OPERAND
                    result.text = "${GLOBAL_RESULT}"
                    TYPING = false
                }

            }
        }else if (CURRENT_OPERATION=="*"){
            OPERATIONS.add(HistoryModel("${GLOBAL_RESULT}x${SECOND_OPERAND}"))
            calculation_history.adapter!!.notifyDataSetChanged()
            SECOND_OPERAND = result.text.toString().toDouble()
            if (TYPING) {
                GLOBAL_RESULT *= SECOND_OPERAND
                result.text = "${GLOBAL_RESULT}"
                TYPING = false
            }
        }



    }

    fun reset() {
        TYPING=false
        OPERATION_CLICKED=false
        GLOBAL_RESULT=0.0
        CURRENT_OPERATION=""
        result.text="0"
    }

    fun removeDigit(){
        if(result.text!="ERROR"){
            if(TYPING){
                if (result.text.toString().length > 1) {
                    result.text = result.text.subSequence(0, result.text.toString().length - 1)
                } else {
                    result.text = "0"
                }
            }

        }

    }
    fun addSign(){
        if(result.text!="ERROR"){
            if(result.text.toString()!="0"){
                if(result.text.toString()[0]=='-'){
                    result.text=result.text.toString().replace("-","")
                }else{
                    result.text = "-${result.text}"
                    if(GLOBAL_RESULT!=0.0){
                        GLOBAL_RESULT=-GLOBAL_RESULT
                    }

                }
            }
        }


    }
    fun addPoint(){
        if(result.text!="ERROR"){
            if(!result.text.toString().contains(".")){
                result.text = "${result.text}."
            }
        }


    }
}