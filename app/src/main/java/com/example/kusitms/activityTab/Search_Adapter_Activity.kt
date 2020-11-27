package com.example.kusitms.activityTab

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.system.Os.bind
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_write.view.*


//정현이 코드
class Search_Adapter_Activity(val dataList:List<Data_Activity>):RecyclerView.Adapter<Search_viewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Search_viewHolder {
        val inflatedView=LayoutInflater.from(parent.context).inflate(R.layout.row_activity, parent, false)
        return Search_viewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: Search_viewHolder, position: Int) {
        val data:Data_Activity=dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

class Search_viewHolder(v:View):RecyclerView.ViewHolder(v){
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    var itemClickListener: Adapter_Activity.OnItemClickListener? = null
    var context: Context? = null
    var subjectText: TextView = itemView.findViewById(R.id.subjectText)
    var imageView: ImageView = itemView.findViewById(R.id.activityImg)

    init {
        itemView.setOnClickListener {
            itemClickListener?.OnItemClick(it, adapterPosition)
        }
    }

    fun bind(data : Data_Activity){
        this.subjectText.text = data.activity_subject

        this.itemView.setOnClickListener{
            val intent = Intent(this.itemView?.context, Info_Activity::class.java)
            this.itemView.context.startActivity(intent)
        }
        var imgRef: StorageReference =storageRef.child("images/${data.activity_pic_url}")
        imgRef.downloadUrl.addOnSuccessListener {
                Uri->
            val imageURL=Uri.toString()
            Glide.with(this.itemView.context).load(imageURL)

                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .override(360, 170)
                .into(this.imageView);
        }
    }
}

