package com.example.kusitms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class Adapter_Activity(options: FirebaseRecyclerOptions<Data_Activity>) :
    FirebaseRecyclerAdapter<Data_Activity, Adapter_Activity.ViewHolder>(options) {
    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var writerText : TextView
        var subjectText : TextView

        init {
            writerText = itemView.findViewById(R.id.writerText)
            subjectText = itemView.findViewById(R.id.subjectText)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Data_Activity) {
        holder.writerText.text = model.writer
        holder.subjectText.text = model.subject
    }
}
