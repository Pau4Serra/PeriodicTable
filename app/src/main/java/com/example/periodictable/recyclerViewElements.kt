package com.example.periodictable

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.periodictable.adapters.ElementsAdapter
import com.example.periodictable.classes.Element
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class recyclerViewElements: AppCompatActivity() {

    lateinit var myRecyclerView: RecyclerView
    val mAdapter: ElementsAdapter = ElementsAdapter()
    var favOnOff = false
    lateinit var elementsArray : MutableList<Element.cElement>
    lateinit var menu: Menu
    var categoryFilters : ArrayList<String> = ArrayList()
    var phaseFilters : String = ""
    var orderNum : Boolean = true
    var firstTime : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_element)

        setUpRecyclerView()
        elementsArray = getElements()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actionbar, menu)
        this.menu = menu!!
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val buttonOrder: MenuItem = this.menu.findItem(R.id.filterBySymbolOrNumber)
        val buttonFav: MenuItem = this.menu.findItem(R.id.orderByFav)
        val buttonDiatomicNonMetals: MenuItem = this.menu.findItem(R.id.diatomicNonMetals)
        val buttonPolyatomicNonMetals: MenuItem = this.menu.findItem(R.id.polyatomicNonMetals)
        val buttonNobleGases: MenuItem = this.menu.findItem(R.id.nobleGases)
        val buttonAlkaliMetals: MenuItem = this.menu.findItem(R.id.alkalineMetals)
        val buttonAlkalineEarthMetals: MenuItem = this.menu.findItem(R.id.alkalineEarthMetals)
        val buttonTransitionMetals: MenuItem = this.menu.findItem(R.id.transitionMetals)
        val buttonMetalloids: MenuItem = this.menu.findItem(R.id.metalloids)
        val buttonPostTransitionMetals: MenuItem = this.menu.findItem(R.id.postTransitionMetals)
        val buttonActinides: MenuItem = this.menu.findItem(R.id.actinides)
        val buttonLanthanides: MenuItem = this.menu.findItem(R.id.lanthanides)

        when(item.itemId) {
            R.id.orderByFav -> {
                if(!favOnOff) {
                    favOnOff = true
                    buttonFav.setIcon(R.drawable.star_full)
                } else {
                    favOnOff = false
                    buttonFav.setIcon(R.drawable.star_empty)
                }
                filter()
            }
            R.id.filterBySymbolOrNumber -> {
                if (!orderNum) {
                    orderNum = true
                    buttonOrder.setIcon(R.drawable.sort_by_alphabet)

                } else {
                    orderNum = false
                    buttonOrder.setIcon(R.drawable.sort_by_numeric_order)
                }
                filter()
            }
            R.id.filterAllPhases -> {
                phaseFilters = ""
                filter()
            }
            R.id.filterGases -> {
                phaseFilters = "Gas"
                filter()
            }
            R.id.filterSolids -> {
                phaseFilters = "Solid"
                filter()
            }
            R.id.filterLiquids -> {
                phaseFilters = "Liquid"
                filter()
            }

            R.id.allCategories -> {
                categoryFilters.clear()
                filter()

                buttonAlkaliMetals.setChecked(false)
                buttonActinides.setChecked(false)
                buttonNobleGases.setChecked(false)
                buttonLanthanides.setChecked(false)
                buttonPolyatomicNonMetals.setChecked(false)
                buttonAlkalineEarthMetals.setChecked(false)
                buttonMetalloids.setChecked(false)
                buttonTransitionMetals.setChecked(false)
                buttonPostTransitionMetals.setChecked(false)
                buttonDiatomicNonMetals.setChecked(false)
            }
            R.id.diatomicNonMetals -> {
                if (!buttonDiatomicNonMetals.isChecked) {
                    categoryFilters.add("diatomic nonmetal")
                    filter()
                } else {
                    categoryFilters.remove("diatomic nonmetal")
                    filter()
                }
                checksController(buttonDiatomicNonMetals)
            }
            R.id.polyatomicNonMetals -> {
                if (!buttonPolyatomicNonMetals.isChecked) {
                    categoryFilters.add("polyatomic nonmetal")
                    filter()
                } else {
                    categoryFilters.remove("polyatomic nonmetal")
                    filter()
                }
                checksController(buttonPolyatomicNonMetals)
            }
            R.id.nobleGases -> {
                if (!buttonNobleGases.isChecked) {
                    categoryFilters.add("noble gas")
                    filter()
                } else {
                    categoryFilters.remove("noble gas")
                    filter()
                }
                checksController(buttonNobleGases)
            }
            R.id.alkalineMetals -> {
                if (!buttonAlkaliMetals.isChecked) {
                    categoryFilters.add("alkali metal")
                    filter()
                } else {
                    categoryFilters.remove("alkali metal")
                    filter()
                }
                checksController(buttonAlkaliMetals)
            }
            R.id.alkalineEarthMetals -> {
                if (!buttonAlkalineEarthMetals.isChecked) {
                    categoryFilters.add("alkaline earth metal")
                    filter()
                } else {
                    categoryFilters.remove("alkaline earth metal")
                    filter()
                }
                checksController(buttonAlkalineEarthMetals)
            }
            R.id.transitionMetals -> {
                if (!buttonTransitionMetals.isChecked) {
                    categoryFilters.add("transition metal")
                    filter()
                } else {
                    categoryFilters.remove("transition metal")
                    filter()
                }
                checksController(buttonTransitionMetals)
            }
            R.id.metalloids -> {
                if (!buttonMetalloids.isChecked) {
                    categoryFilters.add("metalloid")
                    filter()
                } else {
                    categoryFilters.remove("metalloid")
                    filter()
                }
                checksController(buttonMetalloids)
            }
            R.id.postTransitionMetals -> {
                if (!buttonPostTransitionMetals.isChecked) {
                    categoryFilters.add("post-transition metal")
                    filter()
                } else {
                    categoryFilters.remove("post-transition metal")
                    filter()
                }
                checksController(buttonPostTransitionMetals)
            }
            R.id.actinides -> {
                if (!buttonActinides.isChecked) {
                    categoryFilters.add("actinide")
                    filter()
                } else {
                    categoryFilters.remove("actinide")
                    filter()
                }
                checksController(buttonActinides)
            }
            R.id.lanthanides -> {
                if (!buttonLanthanides.isChecked) {
                    categoryFilters.add("lanthanide")
                    filter()
                } else {
                    categoryFilters.remove("lanthanide")
                    filter()
                }
                checksController(buttonLanthanides)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setUpRecyclerView() {
        myRecyclerView = findViewById(R.id.rvElements) as RecyclerView
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter.ElementsAdapter(getElements(), this)

        myRecyclerView.adapter = mAdapter
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

    fun checksController (button : MenuItem) {
        if (!button.isChecked()) {
            button.setChecked(true)
        }
        else {
            button.setChecked(false)
        }
    }

    fun showDialogFav() {
        MaterialAlertDialogBuilder(this)
            .setTitle("No favorites yet!")
            .setMessage("You don't have any favorite elements added yet, after adding some elements you will see them here.")
            .setPositiveButton("OK") { dialog, which -> }
            .show()
    }

    fun filter() {
        var filteredList: MutableList<Element.cElement>
        var elementsArray2 = mAdapter.getElements2()
        var usableList:MutableList<Element.cElement>
        if (!firstTime) {
            usableList = elementsArray2
            firstTime = true
            elementsArray = elementsArray2
        } else { usableList = elementsArray }
        if(favOnOff) {
            filteredList = usableList.filter { element -> element.favorite } as MutableList<Element.cElement>
            if (filteredList.isEmpty()) { showDialogFav() }
        } else {
            filteredList = elementsArray
        }
        if ((categoryFilters.isEmpty()) && phaseFilters == "") {
            mAdapter.elements = orderBy(filteredList)
            mAdapter.notifyDataSetChanged()
        } else {
            var elementsFinal = elementsArray
            if (categoryFilters.isNotEmpty()) {
                elementsFinal = orderBy(filteredList.filter { element -> categoryFilters.contains(element.category) } as MutableList<Element.cElement>)
            }
            if (phaseFilters.isNotEmpty()) {
                elementsFinal = orderBy(elementsFinal.filter { element -> element.phase == phaseFilters } as MutableList<Element.cElement>)
            }
            mAdapter.elements = elementsFinal
            mAdapter.notifyDataSetChanged()
        }
    }

    fun orderBy(elements: MutableList<Element.cElement> = elementsArray): MutableList<Element.cElement> {
        if(orderNum) {
            elements.sortBy { it.number }
        } else {
            elements.sortBy { it.symbol }
        }
        return elements
    }
}