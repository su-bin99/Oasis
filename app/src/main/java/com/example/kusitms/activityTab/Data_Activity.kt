package com.example.kusitms.activityTab

data class Data_Activity(
    var activity_type : String,
    var activity_field : ArrayList<String>,
    var activity_content : String,
    var activity_maxPeoplenum : String,
    var activity_pic_url : String,
    var activity_object : String,
    var activity_participate : ArrayList<String>,
    var activity_subject : String,
    var activity_time : String,
    var activity_writer : String,
    var activity_uid : String
) {
    constructor():
            this("", ArrayList<String>(),
                 "", "", "","",
                ArrayList<String>(), "", "", "", "")
}