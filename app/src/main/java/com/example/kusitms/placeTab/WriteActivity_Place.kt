package com.example.kusitms.placeTab

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.place_write.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity_Place : AppCompatActivity(){

    private val GET_GALLERY_IMAGE = 200
    lateinit var selectedImageUri: Uri
    var fileName: String = ""

    val user = Firebase.auth.currentUser
    val userid = user?.uid

    var content = ""
    var photo = ""
    var reserveperson = ""
    var review = ""
    var subject = ""
    var concept = ""
    var maxnum = 0
    var type = ""
    var time = ""
    var writer = ""
    var uid = userid.toString()

    var value = ""

    var myRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid).child("privacy").child("name")
    var hisRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid).child("history").child("writing")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.place_write)
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

    fun init(){
        place_submitBtn.setOnClickListener{
            content = edit_place_content.text.toString()
            photo = edit_place_photo.text.toString()
            subject = edit_place_subject.text.toString()
            concept = edit_place_concept.text.toString()
            var temp = edit_place_maxnum.text.toString()

            try {
                maxnum = temp.toInt()
            } catch (e: NumberFormatException) {
                Log.d(ContentValues.TAG, temp + "는 숫자가 아님 ");
            }
            type = edit_place_type.text.toString()

            val currentDateTime = Calendar.getInstance().time
            time = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)

            writer = value

            insertData(content, photo, reserveperson, review, subject, concept, maxnum, type, time, writer, uid)

            hisRef.child(subject).setValue(time)

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
        place_content : String,
        place_photo : String,
        reserve_person : String,
        place_review :String,
        place_subject : String,
        place_concept : String,
        place_maxnum : Int,
        place_type : String,
        place_time : String,
        place_writer : String,
        place_uid : String
    ){
        var dataRef = FirebaseDatabase.getInstance().reference.child("place").push()

        upload()

        dataRef.child("place_content").setValue(place_content)
        dataRef.child("place_photo").setValue(place_photo)
        dataRef.child("place_reserve").child("place_review").setValue(place_review)
        dataRef.child("place_reserve").child("reserve_person").setValue(reserve_person)
        dataRef.child("place_subject").setValue(place_subject)
        dataRef.child("place_tag").child("place_concept").setValue(place_concept)
        dataRef.child("place_tag").child("place_maxnum").setValue(place_maxnum)
        dataRef.child("place_tag").child("place_type").setValue(place_type)
        dataRef.child("place_time").setValue(place_time)
        dataRef.child("place_writer").setValue(place_writer)
        dataRef.child("place_uid").setValue(place_uid)
        dataRef.child("place_photo").setValue(fileName)

        val myToast = Toast.makeText(this.applicationContext, "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT)
        myToast.show()

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

            selectedImageUri= data.data!!
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
    private fun getImg(path: Uri):String {
        var aPath=absolutelyPath(path)
        var file = File(aPath)

        return file.name
    }

}