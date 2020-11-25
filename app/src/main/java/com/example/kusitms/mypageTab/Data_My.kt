package com.example.kusitms.mypageTab


data class Data_My(
    var name : String,
    var photo : String,
    var following : ArrayList<String>,
    var follower : ArrayList<String>,
    var level : String,
    var point : Int,
    var like_activity : ArrayList<String>,
    var like_place : ArrayList<String>,
    var comment : ArrayList<String>,
    var writing : ArrayList<String>
){
    constructor():
            this("","",ArrayList<String>(),ArrayList<String>(),"",0,
            ArrayList<String>(),ArrayList<String>(),ArrayList<String>(),ArrayList<String>())
}