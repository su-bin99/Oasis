package com.example.kusitms.activityTab

import android.content.Context
import android.content.Intent
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
import com.example.kusitms.personTab.Info_Person
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Adapter_Activity(options: FirebaseRecyclerOptions<Data_Activity>) :
    FirebaseRecyclerAdapter<Data_Activity, Adapter_Activity.ViewHolder>(options) {
    //options : 쿼리가 들어가는것 / 어떤 질의에 대한 어댑터냐

    var itemClickListener: OnItemClickListener? = null
    var context: Context? = null

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjectText: TextView = itemView.findViewById(R.id.subjectText)
        var imageView: ImageView = itemView.findViewById(R.id.activityImg)

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
            .inflate(R.layout.row_activity, parent, false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Data_Activity) {
        holder.subjectText.text = model.activity_subject

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView?.context, Info_Activity::class.java)
            holder.itemView.context.startActivity(intent)
        }

        var imgRef: StorageReference =storageRef.child("images/${model.activity_pic_url}")
        imgRef.downloadUrl.addOnSuccessListener {
                Uri->
            val imageURL=Uri.toString()
            if(model.activity_pic_url == "")
                Glide.with(context!!).load(R.drawable.place_img)
            else {
                Glide.with(holder.itemView.context).load(imageURL)

                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                    .override(360, 170)
                    .into(holder.imageView);
            }
        }
    }


}