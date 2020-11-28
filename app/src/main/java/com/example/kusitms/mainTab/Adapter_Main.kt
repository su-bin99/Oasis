package com.example.kusitms.mainTab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.kusitms.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Adapter_Main(options: FirebaseRecyclerOptions<Data_home>) :
    FirebaseRecyclerAdapter<Data_home, Adapter_Main.ViewHolder>(options) {
    //options : 쿼리가 들어가는것 / 어떤 질의에 대한 어댑터냐

    var itemClickListener: OnItemClickListener? = null
    var context: Context? = null

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjectText: TextView = itemView.findViewById(R.id.home_Subject)
        var imageView: ImageView = itemView.findViewById(R.id.home_Img)

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
            .inflate(R.layout.row_main, parent, false)
        context = parent.getContext()
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Data_home) {
        holder.subjectText.text = model.subject

//        holder.itemView.setOnClickListener{
//            val intent = Intent(holder.itemView?.context, Info_Activity::class.java)
//            holder.itemView.context.startActivity(intent)
//        }
//        holder.imageView
//        Glide.with(context!!).load(R.drawable.place_img)

        var imgRef: StorageReference =storageRef.child("images/${model.pic_url}")
        imgRef.downloadUrl.addOnSuccessListener {
                Uri->
            val imageURL=Uri.toString()
            val radius = context!!.resources!!.getDimensionPixelSize(R.dimen.corner_radius)
            Glide.with(holder.itemView.context).load(imageURL)
                .transforms(CenterCrop(), RoundedCorners(radius))
                .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView);
        }
    }


}