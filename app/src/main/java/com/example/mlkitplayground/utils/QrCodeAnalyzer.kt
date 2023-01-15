package com.example.mlkitplayground.utils

import android.annotation.SuppressLint
import android.graphics.Rect
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrCodeAnalyzer(
    private val onQrCodeScanned: (Int, List<Rect>) -> Unit
) : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {


        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)


            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()

            val scanner = BarcodeScanning.getClient(options)

            val result = scanner.process(image)
                .addOnSuccessListener { qrcodes ->
                    val bounds: MutableList<Rect?> = mutableListOf()
                    qrcodes.forEach { qrcode ->
                        bounds.add(qrcode.boundingBox)
                    }
                    onQrCodeScanned(qrcodes.count(), bounds.filterNotNull())
                }
                .addOnFailureListener {

                    it.printStackTrace()
                }
                .addOnCompleteListener {
                    mediaImage.close()
                }
        }
    }
}