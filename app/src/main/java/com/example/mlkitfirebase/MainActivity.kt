package com.example.mlkitfirebase

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val TEXT_RECO_REQ_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTextRecog.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,TEXT_RECO_REQ_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TEXT_RECO_REQ_CODE){
            if (resultCode == Activity.RESULT_OK){
//                val photo = attr.data as Bitmap
                val photo = data!!.extras!!.get("data") as Bitmap
                textRecognisation(photo)
//                attr.data.get("data") as Bitmap

            }else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"Task Cancelled By User",Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this,"Failed to capyure image", Toast.LENGTH_SHORT).show()
            }
        }
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
    private fun textRecognisation(photo: Bitmap) {
        val image = InputImage.fromBitmap(photo, 0)
        val recognizer = TextRecognition.getClient()

        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                for (block in visionText.textBlocks) {
                    val boundingBox = block.boundingBox
                    val cornerPoints = block.cornerPoints
                    val text = block.text

                    textView1.text = text

                    for (line in block.lines) {
                        // ...
                        for (element in line.elements) {
                            // ...
//                            textView1.text = element.text.toString()
//                            Toast.makeText(this,"Text Captured is: ", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            .addOnFailureListener { e ->
                Toast.makeText(this,"failed to load text", Toast.LENGTH_SHORT).show()
            }
    }
}
