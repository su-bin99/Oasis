package com.example.kusitms.placeTab

data class Data_Place (
    var place_content : String,
    var place_photo : String,
    var reserve_person : String,
    var place_review :String,
    var place_subject : String,
    var place_concept : String,
    var place_maxnum : Int,
    var place_type : String,
    var place_time : String,
    var place_writer : String,
    var place_uid : String
    ) {
    constructor():
            this("", "", "", "",
            "", "",0,  "", "","",""
            )
}