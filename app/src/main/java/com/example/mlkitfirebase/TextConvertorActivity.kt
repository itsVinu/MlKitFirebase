package com.example.mlkitfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.android.synthetic.main.activity_text_convertor.*

class TextConvertorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_convertor)

//        val srcLang = sourceLanguage.text.toString()
//        val targetLang = targetLanguage.text.toString()

        // Create an English-German translator:
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
//            .setSourceLanguage(TranslateLanguage.${ R.id.sourceLanguage })
//            .setTargetLanguage(TranslateLanguage.${R.id.targetLanguage})
            .build()

        val englishHindiTranslator = Translation.getClient(options)

        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        englishHindiTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                // (Set a flag, unhide the translation UI, etc.)
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                // ...
            }

//        var text2 = etText2.text.toString()
        btnConvert.setOnClickListener {

             englishHindiTranslator.translate(etText1.toString())
                .addOnSuccessListener {
                    etText2.text = it.toString()
                    // Translation successful.
                }
                .addOnFailureListener { exception ->
                    // Error.
                    // ...
                }
        }

//        val options = ...
        val translator = Translation.getClient(options)
        getLifecycle().addObserver(translator)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if (id == R.id.TextDetection) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        else if(id == R.id.TextConvertor){
            startActivity(Intent(this,TextConvertorActivity::class.java))
        }
        return true
//        return super.onOptionsItemSelected(item)
    }
}
