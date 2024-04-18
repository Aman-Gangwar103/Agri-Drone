package com.example.socialmediaapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class developers  : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developers)

        // Load images using Glide
        loadImage("developer1_image", "https://media.licdn.com/dms/image/D5603AQFbSocoaeHkSA/profile-displayphoto-shrink_800_800/0/1706723879675?e=1714003200&v=beta&t=jrCb85-KpvbFL9oGkg92eGt8lO5qPfS_M2l0fkzpJA8")
        loadImage("developer2_image", "https://media.licdn.com/dms/image/D5603AQFQW-51Mx8cKQ/profile-displayphoto-shrink_800_800/0/1704996379151?e=1714003200&v=beta&t=osaJ1RqXM7y0kigbY34ND9DwztvxbF5KH4qivuwDU8Q")
        loadImage("developer3_image", "https://media.licdn.com/dms/image/D4D03AQERtSR2TfcTsw/profile-displayphoto-shrink_800_800/0/1699382699204?e=1714003200&v=beta&t=xn2_jVV7izxpk57KC_h64pBGvl9TPt2S6Psg7uKQwDE")
        loadImage("developer4_image", "https://media.licdn.com/dms/image/D5603AQFls3D4mAByTw/profile-displayphoto-shrink_800_800/0/1689387887509?e=1714003200&v=beta&t=4Bh7-kfhou0SIdfHTBaRrkTOpB9fjsLEdp9lVvUuf1w")
    }

    private fun loadImage(imageViewId: String, imageUrl: String) {
        val imageView = findViewById<ImageView>(resources.getIdentifier(imageViewId, "id", packageName))
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }
}