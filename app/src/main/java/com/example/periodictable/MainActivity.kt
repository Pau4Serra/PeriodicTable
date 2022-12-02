package com.example.periodictable

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.periodictable.classes.Element
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.Serializable
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    var recordGlobal : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun practice(view: View) {
        var intent = Intent(this, practicaGame::class.java)
        intent.putExtra("highScore", recordGlobal)
        highScoreResult.launch(intent)
    }

    var highScoreResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        highScore -> if (highScore.resultCode == Activity.RESULT_OK) {
        var highScore : Intent? = highScore.data
        var record = highScore!!.getIntExtra("highScore", 0)
        recordGlobal = record!!
        }
    }

    fun elementsList(view: View) {
        val i = Intent(this, recyclerViewElements::class.java)
        startActivity(i)
    }

    fun exit(view: View) {
        finish()
    }
}