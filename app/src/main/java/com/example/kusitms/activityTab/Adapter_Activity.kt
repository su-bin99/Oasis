package com.example.kusitms.activityTab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kusitms.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class Adapter_Activity(options: FirebaseRecyclerOptions<Data_Activity>) :
    FirebaseRecyclerAdapter<Data_Activity, Adapter_Activity.ViewHolder>(options) {
    //options : 쿼리가 들어가는것 / 어떤 질의에 대한 어댑터냐

    var itemClickListener : OnItemClickListener?= null

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        var writerText : TextView = itemView.findViewById(R.id.writerText)
        var subjectText : TextView = itemView.findViewById(R.id.subjectText)
        var typeText : TextView = itemView.findViewById(R.id.activityTypeText)
        var activityIdText : TextView = itemView.findViewById(R.id.activityIdText)

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
            .inflate(R.layout.row_activity, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Data_Activity) {
        holder.writerText.text = model.writer
        holder.subjectText.text = model.subject
        holder.activityIdText.text = model.activity_id.toString()
        var type = ""
        for ( i in model.activity_type){
            type += " #" + i
        }
        holder.typeText.text = type

    }
}