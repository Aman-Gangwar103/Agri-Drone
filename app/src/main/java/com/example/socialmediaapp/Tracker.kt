package com.example.socialmediaapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.PNCallback
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.enums.PNStatusCategory
import com.pubnub.api.models.consumer.PNPublishResult
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


class Tracker : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    // Initial coordinates
    private val defaultLat = 16.4971
    private val defaultLng = 80.4992

    private var polyline: Polyline? = null


    // PubNub configuration
    private val pnConfig = PNConfiguration().apply {
        subscribeKey = ""
        publishKey = ""
    }
    private val pubnub = PubNub(pnConfig)
    private val pnChannel = "drone-location"

    private fun startWaypointPublishing() {
        val radius = 0.01

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val newPoint = newPoint(radius)
                pubnub.publish()
                    .channel(pnChannel)
                    .message(newPoint)
                    .async(object : PNCallback<PNPublishResult>() {
                        override fun onResponse(result: PNPublishResult?, status: PNStatus?) {
                            if (status?.isError == true) {
                                println("Publish failed: ${status.errorData}")
                            } else {
                                println("Waypoint published successfully")
                            }
                        }
                    })
            }
        }, 3, 5000)
    }


    private fun newPoint(radius: Double): Map<String, Double> {
        val x = Math.random() * radius
        val y = Math.random() * radius
        return mapOf("lat" to (defaultLat + y), "lng" to (defaultLng + x))
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tracker)

        // Initialize the map
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_tracker) as SupportMapFragment
        mapFragment.getMapAsync(this)
        startWaypointPublishing()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add marker at default coordinates
        val defaultLocation = LatLng(defaultLat, defaultLng)
        mMap?.addMarker(MarkerOptions().position(defaultLocation).title("DRONE LOCATION"))
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))

        // Initialize PubNub subscription
        pubnub.addListener(object : SubscribeCallback() {
            override fun status(pubnub: PubNub, status: PNStatus) {
                if (status.category == PNStatusCategory.PNConnectedCategory || status.category == PNStatusCategory.PNReconnectedCategory) {
                    println("PubNub is connected or reconnected.")
                    // Handle connected or reconnected status
                }
            }

            override fun message(pubnub: PubNub, message: PNMessageResult) {
                val lat = message.message.asJsonObject["lat"].asDouble
                val lng = message.message.asJsonObject["lng"].asDouble

                CoroutineScope(Dispatchers.Main).launch {
                    // Update the map with the received coordinates
                    val newLocation = LatLng(lat, lng)
                    //mMap?.clear()
                    mMap?.addMarker(MarkerOptions().position(newLocation).title("DRONE LOCATION"))

                    /*if (polyline != null) {
                        polyline?.remove()
                    }*/
                    val previousLocation = mMap?.cameraPosition?.target
                    if (previousLocation != null) {
                        polyline = mMap?.addPolyline(PolylineOptions()
                            .add(previousLocation, newLocation)
                            .width(20f)
                            .color(Color.BLUE))
                    }
                    mMap?.moveCamera(CameraUpdateFactory.newLatLng(newLocation))


                   // mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 5f))
                }
            }

            override fun presence(pubnub: PubNub?, presence: PNPresenceEventResult?) {
                TODO("Not yet implemented")
            }
        })

        // Subscribe to the channel
        pubnub.subscribe().channels(listOf(pnChannel)).execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unsubscribe from PubNub to prevent memory leaks
        pubnub.unsubscribeAll()
    }

}
