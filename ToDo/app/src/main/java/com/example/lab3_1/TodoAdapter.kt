package com.example.lab3_1
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
        private val itemList: ArrayList<MainActivity.Item>,
        private val listener:OnItemClickListener
        ):

    RecyclerView.Adapter<TodoAdapter.ViewHolder>(){
    var pos: Int=-1

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view),
    View.OnClickListener{
        val desc: TextView= view.findViewById(R.id.itemName)
        val image: ImageView= view.findViewById(R.id.itemIcon)
        val date: TextView= view.findViewById(R.id.itemDate)
        val time: TextView= view.findViewById(R.id.itemTime)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position=adapterPosition
            if(position!= RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: ViewHolder, position: Int) {
        viewholder.desc.text=itemList[position].name
        viewholder.time.text=itemList[position].time
        viewholder.date.text=itemList[position].date

        when(itemList[position].image){
            AddTodo.NOTI -> viewholder.image.setImageResource(R.drawable.outline_notifications_black)
            AddTodo.SCIENCE -> viewholder.image.setImageResource(R.drawable.outline_science_black)
            AddTodo.GAMES -> viewholder.image.setImageResource(R.drawable.outline_sports_esports_black)
            AddTodo.TRIP -> viewholder.image.setImageResource(R.drawable.outline_hiking_black)
            AddTodo.REPAIR -> viewholder.image.setImageResource(R.drawable.outline_construction_black)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

}