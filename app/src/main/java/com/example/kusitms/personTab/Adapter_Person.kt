package com.example.kusitms.personTab

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kusitms.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Adapter_Person(options: FirebaseRecyclerOptions<Data_Person>) :
    FirebaseRecyclerAdapter<Data_Person, Adapter_Person.ViewHolder>(options) {
    var itemClickListener: OnItemClickListener? = null

    val user = Firebase.auth.currentUser
    val uid = user?.uid

    var myRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid.toString()).child("people").child("following")


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pWriterText : TextView = itemView.findViewById(R.id.pWriterText)
        var pSubjectText : TextView = itemView.findViewById(R.id.pSubjectText)
        var pTagText1 : TextView = itemView.findViewById(R.id.pTagText1)
        var pTagText2 : TextView = itemView.findViewById(R.id.pTagText2)
        var pTagText3 : TextView = itemView.findViewById(R.id.pTagText3)
        var pTagTextArray = arrayListOf<TextView>(pTagText1, pTagText2, pTagText3)
        var imageView : ImageView = itemView.findViewById(R.id.personImg)
        var pTagLayout1 : LinearLayout =  itemView.findViewById(R.id.pTagLayout1)
        var pTagLayout2 : LinearLayout =  itemView.findViewById(R.id.pTagLayout2)
        var pTagLayout3 : LinearLayout =  itemView.findViewById(R.id.pTagLayout3)
        var pTagLayoutArray = arrayListOf<LinearLayout>(pTagLayout1, pTagLayout2, pTagLayout3)
        var pFollowbtn : Button = itemView.findViewById(R.id.followbtn)


        init {
            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(it, adapterPosition)
            }

            pFollowbtn.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    myRef.child("asdf").setValue("asdf")
                }
            })
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

        for ( i in 0..2 ){
            if(i < model.person_tag.size){
                holder.pTagTextArray[i].text = model.person_tag[i]
            }else{
                holder.pTagTextArray[i].visibility = View.INVISIBLE
                holder.pTagLayoutArray[i].setBackgroundResource(R.drawable.back_none)
            }
        }

    }

}