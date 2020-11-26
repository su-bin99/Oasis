package com.example.kusitms.placeTab

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.kusitms.R
import com.example.kusitms.activityTab.Info_Activity
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_mypage.*

class Adapter_Place(options : FirebaseRecyclerOptions<Data_Place>):
   FirebaseRecyclerAdapter<Data_Place, Adapter_Place.ViewHolder>(options){

    val user = Firebase.auth.currentUser
    val uid = user?.uid

    var context : Context?= null

    var itemClickListener : OnItemClickListener?= null

    var myRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid.toString()).child("like").child("place")


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var subjectText : TextView = itemView.findViewById(R.id.plSubjectText)
        var imageView : ImageView = itemView.findViewById(R.id.placeImg)

        init{
            itemView.setOnClickListener{
                itemClickListener?.OnItemClick(it, adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun OnItemClick(view: View, position: Int) {
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_Place.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_place, parent, false)
        context = parent.getContext()
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Data_Place) {
        holder.subjectText.text = model.place_subject
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, Info_Place::class.java)
            intent.putExtra("place_subject",model.place_subject)
            intent.putExtra("place_time",model.place_time)
            holder.itemView.context.startActivity(intent)
        }


        Glide.with(context!!).load(R.drawable.place_img)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
            .override(360, 170)
            .into(holder.imageView);
    }
}