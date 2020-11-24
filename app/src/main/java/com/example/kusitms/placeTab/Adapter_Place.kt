package com.example.kusitms.placeTab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kusitms.R

class Adapter_Place(val items:ArrayList<Data_Place>)
    : RecyclerView.Adapter<Adapter_Place.MyViewHolder>(){
    var context : Context?= null

    interface OnItemClickListener{
        fun OnItemClick(holder : MyViewHolder, view : View, position : Int)
    }

    var itemClickListener : OnItemClickListener?= null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    var clNumText: TextView = itemView.findViewById(R.id.classNum)
//    var clNameText : TextView = itemView.findViewById(R.id.name_class)
//    var timeText : TextView = itemView.findViewById(R.id.classtime)
//    var emptyText : TextView = itemView.findViewById(R.id.emptynum)
//    var ratioText : TextView = itemView.findViewById(R.id.nowp_personnel)

        init {
            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_place, parent, false)

        context = parent.getContext()
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.clNumText.text = items[position].ClassNum.toString()
//        holder.clNameText.text = items[position].ClassName
//        holder.timeText.text = items[position].Time
//        holder.emptyText.text = ( items[position].Personnel - items[position].NowPersonnel ).toString()
//        holder.ratioText.text = items[position].NowPersonnel.toString() +" / " + items[position].Personnel.toString()
    }

}

