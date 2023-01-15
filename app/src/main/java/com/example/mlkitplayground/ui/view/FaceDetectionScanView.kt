package com.example.mlkitplayground.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Paint.Style
import android.util.Size
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_BLOCK_PRODUCER
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.mlkitplayground.GetTitle
import com.example.mlkitplayground.ui.components.MLKitElement
import com.example.mlkitplayground.utils.FaceDetectionAnalyzer
import com.example.mlkitplayground.viewmodel.MainViewModel

@Composable
fun FaceDetectionView(viewModel: MainViewModel) {
    BackHandler() {
        viewModel.onMLKitCardClicked(MLKitElement.MAIN_VIEW)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.TopCenter)
                .background(color = Color(0xFF002171))
                .padding(horizontal = 24.dp),
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { viewModel.onMLKitCardClicked(MLKitElement.MAIN_VIEW) },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back button",
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = GetTitle(viewModel.current_view.value)
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
                .border(width = 2.dp, color = MaterialTheme.colors.primaryVariant)
        ) {

            CameraView(viewModel)
        }
    }
}

@Composable
private fun CameraView(viewModel: MainViewModel) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )
    val cameraProviderFuture = remember(context) { ProcessCameraProvider.getInstance(context) }
    val cameraProvider = remember(cameraProviderFuture) { cameraProviderFuture.get() }
    var camera: Camera? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    Box {
        if (hasCameraPermission) {

            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                previewView.width,
                                previewView.height
                            )
                        )
                        .setBackpressureStrategy(STRATEGY_BLOCK_PRODUCER)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        FaceDetectionAnalyzer { result ->
                            viewModel.onFaceDetected(result)
                        }
                    )

                    try {
                        cameraProvider.unbindAll()
                        camera = cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    previewView
                },
            )
            Canvas(modifier = Modifier) {
                val paint = Paint()
                paint.color = Color.Red
                paint.style = PaintingStyle.Stroke
                paint.strokeWidth = 10f
                val rect = Rect(
                    left = viewModel.faceBounds.value.left.toFloat(),
                    right = viewModel.faceBounds.value.right.toFloat(),
                    top = viewModel.faceBounds.value.top.toFloat(),
                    bottom = viewModel.faceBounds.value.bottom.toFloat()
                )
                this.drawContext.canvas.drawRect(rect, paint)

            }
        } else {
            Text(text = "NO CAMERA PERMISSION")
        }
    }
}