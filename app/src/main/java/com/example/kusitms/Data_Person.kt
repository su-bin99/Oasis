package com.example.kusitms

data class Data_Person (
    /*
    var person_writer : String,
    var person_content : String,
    var person_time : String,
    var person_subject : String,
    var person_tag : ArrayList<String>
     */
    var person_content : String,
    var person_subject : String,
    var person_tag : ArrayList<String>,
    var person_time : String,
    var person_uid : String,
    var person_work : String,
    var person_writer : String
) {
    constructor():
//            this("", "","", "", ArrayList<String>())
    this("","",ArrayList<String>(),"",
        "","","")
}