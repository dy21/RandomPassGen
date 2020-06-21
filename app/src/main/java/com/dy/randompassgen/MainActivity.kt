package com.dy.randompassgen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import java.util.*
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var mTextViewLength: TextView
    private lateinit var mTextViewConventionalSymbols: TextView
    private lateinit var mTextViewSpecialSymbols: TextView
    private lateinit var mTextViewPassGen: TextView
    private lateinit var mCheckboxCapLetter:CheckBox
    private lateinit var mCheckboxLowLetter:CheckBox
    private lateinit var mCheckboxDigits:CheckBox
    private lateinit var mCheckboxConventionalSymbols:CheckBox
    private lateinit var mCheckboxSpecialSymbols:CheckBox
    private lateinit var mButtonGen:Button
    private lateinit var mButtonGenCopy:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextViewLength=findViewById(R.id.editTextLength)
        mTextViewConventionalSymbols=findViewById(R.id.editTextConSymbols)
        mTextViewSpecialSymbols=findViewById(R.id.editTextSpecialSymbols)
        mCheckboxCapLetter=findViewById(R.id.checkBoxCap)
        mCheckboxLowLetter=findViewById(R.id.checkBoxLower)
        mCheckboxDigits=findViewById(R.id.checkBoxDigits)
        mCheckboxConventionalSymbols=findViewById(R.id.checkBoxConSymbols)
        mCheckboxSpecialSymbols=findViewById(R.id.checkBoxSepSymbols)
        mButtonGen=findViewById(R.id.buttonGen)
        mButtonGenCopy=findViewById(R.id.buttonGenCopy)
        mTextViewPassGen=findViewById(R.id.editTextPassword)
    }

    fun onGenClick(view: View){
        mTextViewPassGen.text = genPassStr()
    }

    fun onCopyClick(view: View){
        copyPassToClipboard(view)
    }

    fun onGenCopyClick(view: View){
        mTextViewPassGen.text = genPassStr()
        copyPassToClipboard(view)
    }

    private fun copyPassToClipboard(view: View){
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("New Password", mTextViewPassGen.text.toString())
        cm.setPrimaryClip(clipData)
        Snackbar.make(view, R.string.SnackBarCopied, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private val charsCap="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val charsLower="abcdefghijklmnopqrstuvwxyz"
    private val charsDigits="1234567890"
    private fun genPassStr():String{
        val intLength:Int = mTextViewLength.text.toString().toInt()
        val charsConventionalSymbols= mTextViewConventionalSymbols.text.toString()
        val charsSpecialSymbols=mTextViewSpecialSymbols.text.toString()
        var tempDict:String = ""
        var tempPass:String=""

        //Capital Letters is checked
        if(mCheckboxCapLetter.isChecked){
            tempDict+=charsCap
            tempPass+=getRandomCharsFromString(1,charsCap)
        }

        //Lower Letters is checked
        if(mCheckboxLowLetter.isChecked){
            tempDict+=charsLower
            tempPass+=getRandomCharsFromString(1,charsLower)
        }

        //Digits is checked
        if(mCheckboxDigits.isChecked){
            tempDict+=charsDigits
            tempPass+=getRandomCharsFromString(1,charsDigits)
        }

        //Conventional Symbols is checked
        if(mCheckboxConventionalSymbols.isChecked){
            tempDict+=charsConventionalSymbols
            tempPass+=getRandomCharsFromString(1,charsConventionalSymbols)
        }

        //Special Symbols is checked
        if(mCheckboxSpecialSymbols.isChecked){
            tempDict+=charsSpecialSymbols
            tempPass+=getRandomCharsFromString(1,charsSpecialSymbols)
        }

        if(tempPass.length == 0){
            return ""
        }

        //If then requested Length is smaller than type of chars, trim the tempPass to the length
        if(tempPass.length > intLength){
            tempPass=shuffleString(tempPass)
            tempPass =tempPass.substring(0,intLength)
        }
        else{
            tempPass +=getRandomCharsFromString(intLength-tempPass.length,tempDict)
            tempPass=shuffleString(tempPass)
        }

        return tempPass

    }

    private fun shuffleString(inStr:String):String{
        var tempChars:List<Char> = inStr.toList()
        Collections.shuffle(tempChars)
        return tempChars.joinToString("")
    }

    private fun getRandomCharsFromString(num:Int, inStr:String):String{
        var tempStr:String=""
        if(inStr.length==0){
            return ""
        }
        for(i in 1..num){
            tempStr+=inStr.random()
        }
            return tempStr
    }



}