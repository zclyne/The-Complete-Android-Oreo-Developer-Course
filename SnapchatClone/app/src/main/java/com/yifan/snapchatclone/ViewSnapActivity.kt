package com.yifan.snapchatclone

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.net.HttpURLConnection
import java.net.URL

class ViewSnapActivity : AppCompatActivity() {

    var messageTextView: TextView? = null
    var snapImageView: ImageView? = null
    private lateinit var auth: FirebaseAuth

    inner class ImageDownloader : AsyncTask<String, Void, Bitmap>() { // Bitmap is used for downloading images
        override fun doInBackground(vararg urls: String): Bitmap? {
            try {
                val url = URL(urls[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val `in` = connection.inputStream

                return BitmapFactory.decodeStream(`in`)

            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // delete the snap from the database and the image from storage
        FirebaseDatabase.getInstance().reference // delete the snap from the database
            .child("users")
            .child(auth.currentUser!!.uid)
            .child("snaps")
            .child(intent.getStringExtra("snapKey"))
            .removeValue()
        FirebaseStorage.getInstance().reference // delete the image from storage
            .child("images")
            .child(intent.getStringExtra("imageName"))
            .delete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_snap)

        auth = FirebaseAuth.getInstance()

        messageTextView = findViewById(R.id.messageTextView)
        snapImageView = findViewById(R.id.snapImageView)

        // setup message and image
        messageTextView?.text = intent.getStringExtra("message")
        val task = ImageDownloader()
        val myImage: Bitmap

        try {
            myImage = task.execute(intent.getStringExtra("imageURL")).get() // .get() returns a Bitmap
            snapImageView?.setImageBitmap(myImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
