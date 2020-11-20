package com.example.kusitms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class Adapter_Person(options: FirebaseRecyclerOptions<Data_Person>) :
    FirebaseRecyclerAdapter<Data_Person, Adapter_Person.ViewHolder>(options) {
    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pNameText : TextView
        var pDepartText : TextView

        init {
            pNameText = itemView.findViewById(R.id.personName)
            pDepartText = itemView.findViewById(R.id.personDepart)
            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(it, adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun OnItemClick(view: View, position: Int) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_person, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Data_Person) {
        holder.pNameText.text = model.name
        holder.pDepartText.text = model.depart
    }
}
