package com.example.augmented_reality

import android.util.Log
import android.view.SurfaceView
import com.google.ar.core.Plane
import com.google.ar.core.Session
import com.google.ar.core.Config
import com.google.ar.core.TrackingState

class ArSessionManager(private val session: Session, private val surfaceView: SurfaceView) {

    fun initSession() {
        try {
            // Configure AR session
            val config = Config(session)
            config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
            session.configure(config)
        } catch (e: Exception) {
            Log.e("ArSessionManager", "Failed to initialize AR session", e)
        }
    }

    fun placeObject() {
        val frame = session.update() // Obtain the latest frame from the AR session
        val hitResults = frame.hitTest(surfaceView.width / 2f, surfaceView.height / 2f)

        for (hitResult in hitResults) {
            if (hitResult.trackable is Plane && hitResult.trackable.trackingState == TrackingState.TRACKING) {
                val anchor = hitResult.createAnchor()
                // Place your 3D object at the anchor position here
                Log.d("ArSessionManager", "Object placed at: ${anchor.pose}")
                break
            }
        }
    }
}
