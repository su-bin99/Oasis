package com.example.kusitms.activityTab

data class Data_Activity(
    var activity_concept : ArrayList<String>,
    var activity_field : ArrayList<String>,
    var activity_id : Int,
    var activity_type : String,
    var content : String,
    var maxPeoplenum : Int,
    var participate : ArrayList<String>,
    var pic_url : String,
    var subject : String,
    var time : String,
    var writer : String
) {
    constructor():
            this(ArrayList<String>(), ArrayList<String>(),
                0, "", "", 0,
                ArrayList<String>(), "", "", "", "")
}