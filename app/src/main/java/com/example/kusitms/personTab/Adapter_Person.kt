package com.example.kusitms.personTab

import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.kusitms.R
import com.example.kusitms.SplashActivity
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_mypage.*

class Adapter_Person(options: FirebaseRecyclerOptions<Data_Person>) :
    FirebaseRecyclerAdapter<Data_Person, Adapter_Person.ViewHolder>(options) {
    var itemClickListener: OnItemClickListener? = null

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    val user = Firebase.auth.currentUser
    val uid = user?.uid
    val folname = user?.displayName.toString()

    var myRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid.toString()).child("people").child("following")

    var yourRef = FirebaseDatabase.getInstance().reference


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


        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView?.context, Info_Person::class.java)
            intent.putExtra("person_content",model.person_content)
            intent.putExtra("person_subject",model.person_subject)
            intent.putStringArrayListExtra("person_tag",model.person_tag)
            intent.putExtra("person_time",model.person_time)
            intent.putExtra("person_work",model.person_work)
            intent.putExtra("person_writer",model.person_writer)
            intent.putExtra("person_pic_url",model.person_pic_url)
            holder.itemView.context.startActivity(intent)
        }

        var a = model.person_uid

        var imgRef: StorageReference =storageRef.child("images/${model.person_pic_url}")
        imgRef.downloadUrl.addOnSuccessListener {
                Uri->
            val imageURL=Uri.toString()
            if(model.person_pic_url == "")
                Glide.with(holder.itemView.context).load(R.drawable.ic_launcher_foreground)
            else {
                Glide.with(holder.itemView.context).load(imageURL)
                    .circleCrop()
//                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
//                    .override(120, 120)
                    .into(holder.imageView);
            }
        }

        holder.pFollowbtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var name = model.person_writer
                myRef.child(name).setValue(a)

                var personuid = model.person_uid
                yourRef.child("my_page").child(personuid).child("people").child("follower").child(folname).setValue(uid)
            }
        })


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