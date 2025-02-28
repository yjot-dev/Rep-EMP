package com.yjotdev.empprimaria.ui.view.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.annotation.VisibleForTesting
import java.io.ByteArrayOutputStream

@VisibleForTesting
fun validateText(text: String, case: Int): String{
    val messageRegex = Regex("^[A-Za-z.,\\s]{1,300}$")
    val userOrEmailRegex = Regex("^(?=.{3,50}$)([A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,7}|[A-Za-z0-9._%+-]{3,50})$")
    val userRegex = Regex("^[a-zA-Z]{3,}$")
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    val numberRegex = Regex("^[0-9]{6}$")
    return when(case){
        0 -> if(!messageRegex.matches(text)) "error" else ""
        1 -> if(!userOrEmailRegex.matches(text)) "error" else ""
        2 -> if(!userRegex.matches(text)) "error" else ""
        3 -> if(!emailRegex.matches(text)) "error" else ""
        4 -> if(!numberRegex.matches(text)) "error" else ""
        else -> if(!passwordRegex.matches(text)) "error" else ""
    }
}

fun convertToBitmap(base64: String): Bitmap? {
    return if(base64.isNotEmpty()){
        val byteArray = Base64.decode(base64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }else{
        null
    }
}

fun convertToBase64(bitmap: Bitmap?): String{
    return bitmap?.let {
        val outputStream = ByteArrayOutputStream()
        it.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    } ?: run {
        ""
    }
}