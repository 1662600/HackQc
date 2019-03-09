package com.example.treasuremap


import android.util.Log
import com.example.treasuremap.loisir.LOISIR_LIBRE
import com.example.treasuremap.loisir.Loisir
import com.google.gson.Gson
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset

class Content{

    fun ReadJSONFile(): String{
        val myFile = File(fileName)
        var ins:InputStream = myFile.inputStream()
        var content= ins.readBytes().toString(Charset.defaultCharset())
        Log.v("json",content)
        return content
    }

    val fileName = "src/main/res/raw/loisir.JSON"

    var gson = Gson()
    var jsonString = gson.toJson(ReadJSONFile(), LOISIR_LIBRE::class.java)
}