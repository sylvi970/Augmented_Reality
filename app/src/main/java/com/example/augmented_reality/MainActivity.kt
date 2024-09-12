package com.example.augmented_reality

import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableException


class MainActivity : AppCompatActivity() {

    private lateinit var arSessionManager: ArSessionManager
    private lateinit var arSurfaceView: SurfaceView
    private lateinit var arSession: Session

    override fun onCreate(savedInstanceState: Bundle?) {

        print("hello")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arSurfaceView = findViewById(R.id.ar_surface_view)

        onResume()


        try {
            arSession = Session(this)
            arSessionManager = ArSessionManager(arSession, arSurfaceView)
            arSessionManager.initSession()
        } catch (e: UnavailableException) {
            Log.e("MainActivity", "ARCore session failed to start", e)
        }

        findViewById<Button>(R.id.btnPlaceObject).setOnClickListener {
            arSessionManager.placeObject()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            arSession.resume()
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to resume AR session", e)
        }
    }

    override fun onPause() {
        super.onPause()
        arSession.pause()
    }
}