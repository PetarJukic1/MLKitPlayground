package com.example.mlkitplayground.utils

import android.annotation.SuppressLint
import android.graphics.Rect
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions


@SuppressLint("UnsafeOptInUsageError")
class FaceDetectionAnalyzer(
    private val faceDetected: (Rect) -> Unit,
) : ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val options = FaceDetectorOptions.Builder()
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .build()

            val detector = FaceDetection.getClient(options)

            val result = detector.process(image)
                .addOnSuccessListener { faces ->
                    val bounds = faces.firstOrNull()?.boundingBox ?: Rect(0, 0, 0, 0)
                    faceDetected(bounds)
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
                .addOnCompleteListener {
                    mediaImage.close()
                }
        }
    }
}