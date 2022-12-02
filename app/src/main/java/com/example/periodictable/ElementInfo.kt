package com.example.periodictable

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.periodictable.adapters.ElementsAdapter
import com.example.periodictable.classes.Element
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.element_info.*

class ElementInfo: AppCompatActivity() {

    private lateinit var element : Element.cElement

    companion object {
        const val EXTRA_ELEMENT = "ElementInfo.element"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.element_info)

        element = intent.getParcelableExtra<Element.cElement>(EXTRA_ELEMENT)!!

        tvElementInfoName.text = (element?.number.toString() + ". " + element?.name)
        tvDiscoveredBy.text = ("Discovered by " + element?.discovered_by)
        Glide
            .with(this)
            .load(element?.bohr_model_image)
            .into(imgElement)

        when(element?.category) {
            "diatomic nonmetal", "polyatomic nonmetal" -> elementInfoLayout.setBackgroundColor(Color.parseColor("#3E6418"))
            "noble gas" -> elementInfoLayout.setBackgroundColor(Color.parseColor("#3A2151"))
            "alkali metal" -> elementInfoLayout.setBackgroundColor(Color.parseColor("#6c3b01"))
            "alkaline earth metal" -> elementInfoLayout.setBackgroundColor(Color.parseColor("#846011"))
            "transition metal" -> elementInfoLayout.setBackgroundColor(Color.parseColor("#711019"))
            "metalloid" -> elementInfoLayout.setBackgroundColor(Color.parseColor("#015146"))
            "post-transition metal" -> elementInfoLayout.setBackgroundColor(Color.parseColor("#003666"))
            "actinide" -> elementInfoLayout.setBackgroundColor(Color.parseColor("#732E4C"))
            "lanthanide" ->  elementInfoLayout.setBackgroundColor(Color.parseColor("#402C17"))
            else -> elementInfoLayout.setBackgroundColor(Color.parseColor("#191919"))}

        var fav = element?.favorite

        imgFav.setOnClickListener() {
            if(fav == true) {
                imgFav.setImageResource(R.drawable.star_empty)
                fav = false
                element?.favorite = false
            } else {
                imgFav.setImageResource(R.drawable.star_full)
                fav = true
                element?.favorite = true
            }
        }
        if(element?.favorite == true) {
            imgFav.setImageResource(R.drawable.star_full)
        } else {
            imgFav.setImageResource(R.drawable.star_empty)
        }
        tvSymbol.text = ("Symbol: " + element?.symbol)
        tvMass.text = ("Atomic Mass: " + element?.atomic_mass.toString() + " u")
        tvPhaseInfo.text = ("Phase: " + element?.phase)
        tvCategoryInfo.text = ("Category: " + ((element?.category)?.replaceFirstChar { it.uppercase() }))
        tvBoil.text = ("Boil temperature: " + element?.boil + " ºC")
        tvSummary.text = element?.summary


    }

//    override fun finish() {
//        val intent = Intent()
//        intent.putExtra(EXTRA_ELEMENT, element as java.io.Serializable)
//        setResult(RESULT_OK, intent)
//        super.finish()
//    }
}