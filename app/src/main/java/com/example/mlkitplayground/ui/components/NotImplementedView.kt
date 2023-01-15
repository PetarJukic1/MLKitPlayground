package com.example.mlkitplayground.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mlkitplayground.viewmodel.MainViewModel

@Composable
fun NotImplementedView(viewModel: MainViewModel) {
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
                text = getTitle(viewModel.current_view.value)
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "NOT IMPLEMENTED"
        )
    }
}

@Composable
private fun getTitle(element: MLKitElement) =
    when(element){
        MLKitElement.MAIN_VIEW -> "ML Kit Features"
        MLKitElement.QR_CODE_SCAN -> "QR Code Scan"
        MLKitElement.FACE_DETECTION -> "Face Detection"
        MLKitElement.PICTURE_SEGMENTATION -> "Picture Segmentation"
        MLKitElement.LANGUAGE_DETECTION -> "Language Detection"
        MLKitElement.TEXT_FEATURE_EXTRACTION -> "Text Feature Extraction"
    }
