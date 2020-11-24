package com.example.kusitms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class Adapter_Place(options : FirebaseRecyclerOptions<Data_Place>):
   FirebaseRecyclerAdapter<Data_Place, Adapter_Place.ViewHolder>(options){

    var itemClickListener : OnItemClickListener ?= null

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var writerText : TextView = itemView.findViewById(R.id.plWriterText)
        var subjectText : TextView = itemView.findViewById(R.id.plSubjectText)
        var typeText : TextView = itemView.findViewById(R.id.plTypeText)

        init{
            itemView.setOnClickListener{
                itemClickListener?.OnItemClick(it, adapterPosition)
            }
        }
    }

    interface OnItemClickListener{
        fun OnItemClick(view: View, position:Int){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_place, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Data_Place) {
        holder.writerText.text = model.place_writer
        holder.subjectText.text = model.place_subject
        holder.typeText.text = model.place_type
    }
}