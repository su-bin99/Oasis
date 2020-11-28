package com.example.kusitms.activityTab

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.place_write.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WriteActivity_Activity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val GET_GALLERY_IMAGE = 200
    lateinit var selectedImageUri: Uri
    var fileName: String = ""

    val user = Firebase.auth.currentUser
    val userid = user?.uid

    var content = ""
    var subject = ""
    var field = ""
    var maxnum = ""
    var activity_participate : String=""
    var type = ""
    var time = ""
    var writer = ""
    var uid = userid.toString()
    var a_object=""

    var value = ""

    var myRef =
        FirebaseDatabase.getInstance().reference.child("my_page").child(uid).child("privacy")
            .child("name")
    var hisRef =
        FirebaseDatabase.getInstance().reference.child("my_page").child(uid).child("history")
            .child("writing")


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

    fun init() {
        place_send.setOnClickListener {
            content = activity_content.text.toString()
            subject = activity_subject.text.toString()
            val currentDateTime = Calendar.getInstance().time
            time = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)
            writer = value

            insertData(
                content,
                field,
                maxnum,
                a_object,
                activity_participate ,
                fileName,
                subject,
                type
            )

            hisRef.child(subject).setValue(time)

        }

        initSpinner()

        place_pic.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            startActivityForResult(intent, GET_GALLERY_IMAGE)
        }
    }

    fun initSpinner() {
        this.let {
            ArrayAdapter.createFromResource(
                it, R.array.a_searchTargetOption, android.R.layout.simple_spinner_item
            ).also { adapter ->
                activity_object.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                it, R.array.a_searchFieldOption, android.R.layout.simple_spinner_item
            ).also { adapter ->
                activity_field.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                it, R.array.a_searchTypeOption, android.R.layout.simple_spinner_item
            ).also { adapter ->
                activity_type.adapter = adapter
            }
        }
        edit_place_concept.onItemSelectedListener = this
        edit_place_type.onItemSelectedListener = this
        edit_place_maxnum.onItemSelectedListener = this
    }

    fun insertData(
        activity_content: String,
        activity_field: String,
        activity_maxPeoplenum: String,
        activity_object: String,
        activity_participate: String,
        activity_pic_url: String,
        activity_subject: String,
        activity_type: String) {
//        println(place_content + "/" +  place_photo + "/" + place_price + "/" +
//        place_subject + "/" + place_concept + "/" + place_maxnum + "/" + place_type)
//        if( place_content == "" || place_photo == "" ||  place_price == "" || place_subject == "" ||
//            place_concept == "컨셉" || place_maxnum == "명수" || place_type == "유형"){
//            Toast.makeText(this.applicationContext, "빈칸을 모두 입력해주십시오. ", Toast.LENGTH_SHORT).show()
//        }else{
        var dataRef = FirebaseDatabase.getInstance().reference.child("place").push()

        upload()

        dataRef.child("activity_content").setValue(activity_content)
        dataRef.child("activity_field").setValue(activity_field)
        dataRef.child("activity_maxPeoplenum").setValue(activity_maxPeoplenum)
        dataRef.child("activity_object").child("place_review").setValue(activity_object)
        dataRef.child("activity_participate").child("0").setValue(activity_participate)
        dataRef.child("activity_pic_url").setValue(fileName)
        dataRef.child("activity_subject").setValue(activity_subject)
        dataRef.child("activity_type").setValue(activity_type)


        val myToast = Toast.makeText(this.applicationContext, "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT)
        myToast.show()

        this.finish()
//        }
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
            var picView: ImageView =findViewById(R.id.place_picView)
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

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent!!.getItemAtPosition(position).toString()
        when (parent) {
            activity_field -> field = selectedItem
            activity_type -> type = selectedItem
            activity_object -> a_object = selectedItem
        }
    }
}