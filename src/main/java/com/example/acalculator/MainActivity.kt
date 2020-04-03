package com.example.acalculator

import android.os.Build
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val VISOR_KEY = "visor";

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG,"O método onCreate foi invocado");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_historic?.adapter = HistoryAdapter(this, android.R.layout.simple_list_item_1, arrayListOf("1+1=2","2+3=5"));

        button_0.setOnClickListener { onClickSymbol("0")}
        button_1.setOnClickListener { onClickSymbol("1")}
        button_2.setOnClickListener { onClickSymbol("2")}
        button_3.setOnClickListener { onClickSymbol("3")}
        button_4.setOnClickListener { onClickSymbol("4")}
        button_5.setOnClickListener { onClickSymbol("5")}
        button_6.setOnClickListener { onClickSymbol("6")}
        button_7?.setOnClickListener { onClickSymbol("7")}
        button_8?.setOnClickListener { onClickSymbol("8")}
        button_9?.setOnClickListener { onClickSymbol("9")}
        button_00?.setOnClickListener { onClickSymbol("00")}
        button_decimal.setOnClickListener { onClickSymbol(".")}
        button_multiply.setOnClickListener { onClickSymbol("*")}
        button_division.setOnClickListener { onClickSymbol("/")}
        button_adition.setOnClickListener { onClickSymbol("+")}
        button_subtract.setOnClickListener { onClickSymbol("-")}
        button_equals.setOnClickListener { onClickEquals()}
        button_clear.setOnClickListener { onClickClear()}
        button_delete.setOnClickListener { onClickDelete()}

    }

    override fun onDestroy() {
        Log.i(TAG,"O método onDestroy foi invocado");
        super.onDestroy();
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        text_visor.text = savedInstanceState?.getString(VISOR_KEY);
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run { putString(VISOR_KEY, text_visor.text.toString())}
        super.onSaveInstanceState(outState);
    }

    fun onClickSymbol(symbol: String) {
        Log.i(TAG, "Click no botão $symbol");
        if(text_visor.text == "0") {
            text_visor.text = symbol;
        } else {
            text_visor.append(symbol);
        }

        Toast.makeText(this, "Button $symbol at ${SimpleDateFormat("hh:mm:ss").format(Date())}",Toast.LENGTH_SHORT).show();
    }

    fun onClickClear() {
        Log.i(TAG, "Click no botão Clear");
        text_visor.text = "0";

        Toast.makeText(this, "Button Clear at ${SimpleDateFormat("hh:mm:ss").format(Date())}",Toast.LENGTH_SHORT).show();
    }

    fun onClickDelete() {
        Log.i(TAG, "Click no botão Delete");
        if(text_visor.text.length == 1) {
            text_visor.text = "0";
        } else {
            text_visor.text = text_visor.text.dropLast(1);
        }
        Toast.makeText(this, "Button < at ${SimpleDateFormat("hh:mm:ss").format(Date())}",Toast.LENGTH_SHORT).show();
    }

    fun onClickEquals() {
        val text = text_visor.text.toString();
        val expression = ExpressionBuilder(text).build();

        val result = expression.evaluate();
        val longResult = result.toLong();
        if (result == longResult.toDouble()) {
            text_visor.text = longResult.toString();
            text_last?.text = "${text} = ${longResult.toString()}";
            Log.i(TAG, "O resultado da expressão é ${text_visor.text}");
        } else {
            text_visor.text = result.toString();
            text_last?.text = "${text} = ${longResult.toString()}";
            Log.i(TAG, "O resultado da expressão é ${text_visor.text}");
        }

        Toast.makeText(this, "Button = at ${SimpleDateFormat("hh:mm:ss").format(Date())}",Toast.LENGTH_SHORT).show();
    }
}
