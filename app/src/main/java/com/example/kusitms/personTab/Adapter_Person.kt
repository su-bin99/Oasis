package com.example.kusitms.personTab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kusitms.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class Adapter_Person(options: FirebaseRecyclerOptions<Data_Person>) :
    FirebaseRecyclerAdapter<Data_Person, Adapter_Person.ViewHolder>(options) {
    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pWriterText : TextView = itemView.findViewById(R.id.pWriterText)
        var pSubjectText : TextView = itemView.findViewById(R.id.pSubjectText)
        var pTagText : TextView = itemView.findViewById(R.id.pTagText)


        init {
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
        holder.pWriterText.text = model.person_writer
        holder.pSubjectText.text = model.person_subject
        var tag= ""
        for ( i in model.person_tag){
            tag += "#"+i
        }
        holder.pTagText.text = tag
    }

}
