package com.example.periodictable

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.periodictable.classes.Element
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.practice_layout.*

class practicaGame: AppCompatActivity() {

    var answer = true
    lateinit var element : Element.cElement
    var highScore = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practice_layout)
        element = getElements().random()
        tvQuestion.text = element.name
        tvSymbol_.text = element.symbol

        game()
    }

    fun getElements():MutableList<Element.cElement> {
        val objectType = object : TypeToken<MutableList<Element.cElement>>() {}.type

        var elementArray : MutableList<Element.cElement> = mutableListOf()

        val gson = Gson()
        var file = this.assets.open("periodicTable.json").bufferedReader().use{
            it.readText()
        }

        var element: MutableList<Element.cElement> = gson.fromJson(file, objectType)
        element.forEach {elements -> elementArray.add(Element.cElement(elements.symbol, elements.name, elements.number, elements.atomic_mass, elements.category, elements.phase, false, elements.summary, elements.discovered_by, elements.boil, elements.bohr_model_image))}

        return elementArray
    }

    fun buttonAssigner() {
        button2.text = "Solid"
        button3.text = "Liquid"
        button4.text = "Gas"
        button5.text = "Unknown"
    }

    fun buttonListener() {
        button2.setOnClickListener() {
            answer = button2.text == element.phase
            element = getElements().random()
            tvQuestion.text = element.name
            tvSymbol_.text = element.symbol
            result(answer)
        }

        button3.setOnClickListener() {
            answer = button3.text == element.phase
            element = getElements().random()
            tvQuestion.text = element.name
            tvSymbol_.text = element.symbol
            result(answer)
        }

        button4.setOnClickListener() {
            answer = button4.text == element.phase
            element = getElements().random()
            tvQuestion.text = element.name
            tvSymbol_.text = element.symbol
            result(answer)
        }

        button5.setOnClickListener() {
            answer = button5.text == element.phase
            element = getElements().random()
            tvQuestion.text = element.name
            tvSymbol_.text = element.symbol
            result(answer)
        }
    }

    fun game() {
        var highScoreGlobal = intent.getIntExtra("highScore", 0)
        if(highScoreGlobal != null) {
            tvHighScoreNum.text = highScoreGlobal.toString()
        } else {
            tvHighScoreNum.text = "0"
        }
        buttonAssigner()
        buttonListener()
    }

    fun result(answer:Boolean) {
        if(answer) {
            tvScoreNum.text = (tvScoreNum.text.toString().toInt() + 1).toString()
        } else {
            if(tvScoreNum.text.toString().toInt() > tvHighScoreNum.text.toString().toInt()) {
                tvHighScoreNum.text = tvScoreNum.text.toString()
                highScore = tvHighScoreNum.text.toString().toInt()
            }
            tvScoreNum.text = 0.toString()
            showDialogReturn()
        }
    }

    fun showDialogReturn() {
        MaterialAlertDialogBuilder(this)
            .setTitle("You lost!")
            .setCancelable(false)
            .setMessage("You selected the wrong answer, you will go back to the main menu.")
            .setPositiveButton("OK") {
                dialog, which ->
                val intent : Intent = Intent()
                intent.putExtra("highScore", highScore)
                setResult(RESULT_OK, intent)
                finish()
            }
            .show()
    }
}