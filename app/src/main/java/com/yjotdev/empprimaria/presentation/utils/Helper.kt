package com.yjotdev.empprimaria.presentation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

object Helper {
    fun isValidUser(input: String): Boolean{
        return Regex("^[A-Za-z]{3,10}$").matches(input)
    }
    fun isValidEmail(input: String): Boolean{
        return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(input)
    }
    fun isValidUserOrEmail(input: String): Boolean{
        return Regex("^([A-Za-z]{3,10}|[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+)$").matches(input)
    }
    fun isValidPassword(input: String): Boolean{
        return Regex("^[A-Za-z0-9@#_]{8,16}$").matches(input)
    }
    fun isValidCode(input: String): Boolean{
        return Regex("^[0-9]{6}$").matches(input)
    }
    fun isValidMessage(input: String): Boolean{
        return Regex("^[A-Za-z.,\\s]{1,300}$").matches(input)
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
}