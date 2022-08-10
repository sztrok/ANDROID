package com.example.lab3_1

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3_1.databinding.ActivityMainBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), TodoAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val itemList = ArrayList<Item>()
    private val allItems = ArrayList<Item>()
    private var prevMenuItem : MenuItem? = null
    private var currentFilter = -1
    private var currentSorting = 0

    companion object{
        const val addSorting = 0
        const val dateSorting = 1
        const val alphabetSorting = 2
    }


    @Parcelize
    class Item(val name: String, val image: Int, val date: String, val time: String, val description: String?) : Parcelable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = TodoAdapter(itemList, this)




        touchHelper.attachToRecyclerView(binding.recyclerView)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("key", allItems)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            allItems.addAll(getParcelableArrayList<Item>("key") as ArrayList<Item>)
            itemList.addAll(allItems)
            prevMenuItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
            filter()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        prevMenuItem = menu?.findItem(R.id.menuAll)

        return true
    }

    val touchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    var position: Int = viewHolder.adapterPosition

                    when (direction) {
                        ItemTouchHelper.RIGHT -> {
                            allItems.remove(itemList[position])
                            itemList.removeAt(position)
                            sort()
                        }
                    }
                }



                override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                    RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(R.color.beige)
                            .addActionIcon(R.drawable.deleteicon)
                            .create()
                            .decorate()
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            })

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != R.id.menuSort){
            prevMenuItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            prevMenuItem = item

            when (item.itemId){
                R.id.menuAll -> currentFilter = -1
                R.id.menuNoti -> currentFilter = AddTodo.NOTI
                R.id.menuScience -> currentFilter = AddTodo.SCIENCE
                R.id.menuGames -> currentFilter = AddTodo.GAMES
                R.id.menuHiking -> currentFilter = AddTodo.TRIP
                R.id.menuConstr -> currentFilter = AddTodo.REPAIR
            }
        }
        else{
            currentSorting = (currentSorting + 1) % 3
            when (currentSorting){
                addSorting -> item.setIcon(R.drawable.outline_remove_circle_outline_white)
                dateSorting -> item.setIcon(R.drawable.outline_date_range_white)
                alphabetSorting -> item.setIcon(R.drawable.outline_sort_by_alpha_white)
            }
        }
        filter()
        return true
    }

    fun fabClick(view: View) {
        val myIntent = Intent(this, AddTodo::class.java)
        startActivityForResult(myIntent, 1)
    }


    fun filter(){
        itemList.clear()
        itemList.addAll(allItems)
        if (currentFilter != -1){
            var i = 0
            while (true){
                if (i == itemList.size){
                    break
                }
                if (itemList[i].image != currentFilter){
                    itemList.removeAt(i)
                    i--
                }
                i++
            }
        }
        sort()
    }

    fun sort(){
        when (currentSorting){
            dateSorting -> {
                Collections.sort(itemList, object : Comparator<Item> {
                    @SuppressLint("SimpleDateFormat")
                    override fun compare(o1: Item, o2: Item): Int {
                        val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm")
                        val firstDate = o1.date + " " + o1.time
                        val secDate = o2.date + " " + o2.time
                        if (dateFormat.parse(firstDate).before(dateFormat.parse(secDate))){
                            return -1
                        }
                        else if (dateFormat.parse(firstDate).equals(dateFormat.parse(secDate))){
                            return o1.time.compareTo(o2.time)
                        }
                        else{
                            return 1
                        }
                    }
                })
            }
            alphabetSorting -> {
                Collections.sort(itemList, object : Comparator<Item> {
                    @SuppressLint("SimpleDateFormat")
                    override fun compare(o1: Item, o2: Item): Int {
                        return o1.name.compareTo(o2.name)
                    }
                })
            }
        }
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val name = data?.getStringExtra("name")
        val image = data?.getIntExtra("icon", -1)
        val date = data?.getStringExtra("date")
        val time = data?.getStringExtra("time")
        val desc= data?.getStringExtra("desc")

        if (data != null){
            if (name == null || image == null || date == null || time == null){
                return
            }

            itemList.add(Item(name, image, date, time, desc))
            allItems.add(Item(name, image, date, time, desc))
            binding.recyclerView.adapter?.notifyDataSetChanged()
            filter()
        }
    }

    override fun onItemClick(position: Int) {
        val intent= Intent(this,DetailActivity::class.java)
        intent.putExtra("name",allItems[position].name)
        intent.putExtra("desc",allItems[position].description)
        intent.putExtra("time",allItems[position].time)
        intent.putExtra("date",allItems[position].date)
        intent.putExtra("image",allItems[position].image)
        startActivity(intent)

    }
}
