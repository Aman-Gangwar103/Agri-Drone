package com.example.socialmediaapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class CHATBOT : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatbot)

        // Find WebView in the layout
        val webView: WebView = findViewById(R.id.webView)

        // Enable JavaScript (if needed)
        webView.settings.javaScriptEnabled = true

        // Set a WebViewClient to handle redirects within the WebView
        webView.webViewClient = WebViewClient()

        // Load the chatbot URL
        webView.loadUrl("https://mind-mate-wellness.vercel.app/message")
    }
}