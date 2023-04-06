package com.rikkimikki.mailnews.utils

fun convertDate(date: String): String{
    return date.replace("T"," ").replace("Z","")
}