package com.example.kusitms

data class Data_Activity(
    var contents :Data_activity_contents,
    var info : Data_activity_info,
    var participates :Data_activity_participates,
    var tag : Data_activity_tag
) {
}

data class Data_activity_contents(
    var content : String,
    var pic : String
){
}

data class Data_activity_info(
    var activity_id : Int,
    var subject : String,
    var time : String,
    var writer : String
){
}

data class Data_activity_participates(
    var id : String,
    var review : String
    ){
}

data class Data_activity_tag(
    var concept : String,
    var field : String,
    var pattern : String,
    var peoplenum : Int
    ){
}