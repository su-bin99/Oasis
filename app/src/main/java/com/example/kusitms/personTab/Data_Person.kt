package com.example.kusitms.personTab

data class Data_Person (
    var person_writer : String,
    var person_content : String,
    var person_time : String,
    var person_subject : String,
    var person_tag : ArrayList<String>
) {
    constructor():
            this("", "","", "", ArrayList<String>())
}