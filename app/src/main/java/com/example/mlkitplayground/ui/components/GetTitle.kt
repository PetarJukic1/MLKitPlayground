package com.example.mlkitplayground

import androidx.compose.runtime.Composable
import com.example.mlkitplayground.ui.components.MLKitElement

@Composable
fun GetTitle(element: MLKitElement) =
    when(element){
        MLKitElement.MAIN_VIEW -> "ML Kit Features"
        MLKitElement.QR_CODE_SCAN -> "QR Code Scan"
        MLKitElement.FACE_DETECTION -> "Face Detection"
        MLKitElement.PICTURE_SEGMENTATION -> "Picture Segmentation"
        MLKitElement.LANGUAGE_DETECTION -> "Language Detection"
        MLKitElement.TEXT_FEATURE_EXTRACTION -> "Text Feature Extraction"
    }