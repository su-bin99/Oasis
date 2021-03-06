package com.example.kusitms.personTab

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.person_write.*
import kotlinx.android.synthetic.main.person_write.picBtn
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WriteActivity_Person : AppCompatActivity() {

    private val GET_GALLERY_IMAGE = 200
    lateinit var selectedImageUri: Uri
    var fileName: String = ""

    val user = Firebase.auth.currentUser
    val person_uid = user?.uid

    var content = ""
    var subject = ""
    var tag = ""
    var uid = person_uid.toString()
    var work = ""

    var value = ""

    var myRef =
        FirebaseDatabase.getInstance().reference.child("my_page").child(uid).child("privacy")
            .child("name")
    var pic_url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_write)

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                value = snapshot.value.toString()
                println("did you")
                println(value)
            }

            override fun onCancelled(error: DatabaseError) {
                println("failed to read value")
            }
        })
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    fun init() {
        person_submitBtn.setOnClickListener {
            var tagarray = ArrayList<String>()

            content = edit_person_content.text.toString()
            subject = edit_person_subject.text.toString()
            tag = edit_person_tag.text.toString()


            println("before getname")


            print(value)
            var writer = value
            tagarray.add(tag)

            println("ㅁㅓ가 먼저찍힐까?")
            val currentDateTime = Calendar.getInstance().time
            var time = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)
            insertData(content, subject, tagarray, time, uid, work, writer, pic_url)
        }
        picBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            startActivityForResult(intent, GET_GALLERY_IMAGE)
        }
    }

    fun insertData(
        person_content: String,
        person_subject: String,
        person_tag: ArrayList<String>,
        person_time: String,
        person_uid: String,
        person_work: String,
        person_writer: String,
        person_pic_url: String
    ) {
        var dataRef = FirebaseDatabase.getInstance().reference.child("person").push()
        upload()
        dataRef.child("person_content").setValue(person_content)
        dataRef.child("person_subject").setValue(person_subject)
        for (i in 0 until person_tag.size) {
            dataRef.child("person_tag").child(i.toString()).setValue(tag)
        }
        dataRef.child("person_time").setValue(person_time)
        dataRef.child("person_uid").setValue(person_uid)
        dataRef.child("person_work").setValue(person_work)
        dataRef.child("person_writer").setValue(person_writer)
        dataRef.child("person_pic_url").setValue(fileName)

//        val myToast = Toast.makeText(this.applicationContext, "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT)
//        myToast.show()

        this.finish()
    }

    fun upload() {
        fileName = getImg(selectedImageUri)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        var imgRef = storageRef.child("images/$fileName")

        var uploadTask = imgRef.putFile(selectedImageUri)
        uploadTask.addOnFailureListener {
        }.addOnSuccessListener {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(resultCode, requestCode, data)

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {

            selectedImageUri = data.data!!
            var picView:ImageView=findViewById(R.id.picView)
            picView.setImageURI(selectedImageUri)
        }
    }

    private fun absolutelyPath(path: Uri): String {

        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor = contentResolver.query(path, proj, null, null, null)!!
        var index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c.moveToFirst()

        return c.getString(index)
    }

    private fun getImg(path: Uri): String {
        var aPath = absolutelyPath(path)
        var file = File(aPath)

        return file.name
    }

}
