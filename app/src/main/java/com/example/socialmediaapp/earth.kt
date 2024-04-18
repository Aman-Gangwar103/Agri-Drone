package com.example.socialmediaapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class earth : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earth)

        // Find WebView in the layout
        val webView: WebView = findViewById(R.id.webView)

        // Enable JavaScript (if needed)
        webView.settings.javaScriptEnabled = true

        // Set a WebViewClient to handle redirects within the WebView
        webView.webViewClient = WebViewClient()

        // Load the Earth URL
        webView.loadUrl("https://solarsystem.nasa.gov/gltf_embed/2393/")
    }

}