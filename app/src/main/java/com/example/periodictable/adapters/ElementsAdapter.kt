package com.example.periodictable.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.periodictable.R
import com.example.periodictable.classes.Element
import com.example.periodictable.ElementInfo

class ElementsAdapter: RecyclerView.Adapter<ElementsAdapter.ViewHolder>() {
    var elements: MutableList<Element.cElement> = ArrayList()
    lateinit var context: Context
//    var resultElement = (context as AppCompatActivity).registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        val data : Intent? = result.data
//        val element = data!!.getSerializableExtra(ElementInfo.EXTRA_ELEMENT) as Element.cElement
//
//        elements.first { it.number == element.number }.favorite = element.favorite
//        this.notifyDataSetChanged()
//    }

    fun ElementsAdapter(elements: MutableList<Element.cElement>, context: Context) {
        this.elements = elements
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val item = elements.get(pos)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.element_card, parent, false))
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val symbol = view.findViewById<TextView>(R.id.tvElementSymbol)
        val name = view.findViewById<TextView>(R.id.tvElementName)
        val number = view.findViewById<TextView>(R.id.tvNumber)
        val atomic_mass = view.findViewById<TextView>(R.id.tvElementAtomicMass)
        val phase = view.findViewById<TextView>(R.id.tvElementPhase)
        var card = view.findViewById<CardView>(R.id.cardElement)
        var favImage = view.findViewById<ImageView>(R.id.btnStarEmpty)
        var infoImage = view.findViewById<ImageView>(R.id.btnInfo)

        fun bind(element: Element.cElement, context: Context) {
            symbol.text = element.symbol
            name.text = element.name
            number.text = element.number.toString()
            atomic_mass.text = element.atomic_mass.toString()
            phase.text = element.phase
            var fav = !element.favorite

            when(element.category) {
                "diatomic nonmetal", "polyatomic nonmetal" -> card.setBackgroundColor(Color.parseColor("#3E6418"))
                "noble gas" -> card.setBackgroundColor(Color.parseColor("#3A2151"))
                "alkali metal" -> card.setBackgroundColor(Color.parseColor("#6c3b01"))
                "alkaline earth metal" -> card.setBackgroundColor(Color.parseColor("#846011"))
                "transition metal" -> card.setBackgroundColor(Color.parseColor("#711019"))
                "metalloid" -> card.setBackgroundColor(Color.parseColor("#015146"))
                "post-transition metal" -> card.setBackgroundColor(Color.parseColor("#003666"))
                "actinide" -> card.setBackgroundColor(Color.parseColor("#732E4C"))
                "lanthanide" ->  card.setBackgroundColor(Color.parseColor("#402C17"))
                else -> card.setBackgroundColor(Color.parseColor("#191919"))
            }

            when(element.phase) {
                "Gas" -> symbol.setTextColor(Color.parseColor("#FF4444"))
                "Solid" -> symbol.setTextColor(Color.parseColor("#B5B5B5"))
                "Liquid" -> symbol.setTextColor(Color.parseColor("#9999FF"))
                else -> symbol.setTextColor(Color.parseColor("#777777"))
            }

            infoImage.setOnClickListener() {
                infoPage(context, element)
            }

            fun updateFav() {
                if(fav) {
                    favImage.setImageResource(R.drawable.star_empty)
                    fav = false
                    element.favorite = false
                } else {
                    favImage.setImageResource(R.drawable.star_full)
                    fav = true
                    element.favorite = true
                }
            }

            favImage.setOnClickListener() {
                updateFav()
            }

            updateFav()
        }
        fun infoPage(context: Context , element: Element.cElement) {
            val intent = Intent(context, ElementInfo::class.java)
            intent.putExtra(ElementInfo.EXTRA_ELEMENT, element)
            //resultElement.launch(intent)
            startActivity(context, intent, Bundle())
        }
    }

    fun getElements2(): MutableList<Element.cElement> {
        return elements
    }
}