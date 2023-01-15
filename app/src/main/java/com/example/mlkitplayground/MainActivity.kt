package com.example.mlkitplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mlkitplayground.GetTitle
import com.example.mlkitplayground.ui.components.NotImplementedView
import com.example.mlkitplayground.ui.view.QRCodeScanView
import com.example.mlkitplayground.R
import com.example.mlkitplayground.ui.components.MLKitElement
import com.example.mlkitplayground.theme.MLKitPlaygroundTheme
import com.example.mlkitplayground.ui.view.FaceDetectionView
import com.example.mlkitplayground.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        val mLKitElements = MLKitElement.values().toList()
        setContent {
            MLKitPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (viewModel.current_view.value) {
                        MLKitElement.MAIN_VIEW -> MainView(viewModel, mLKitElements)
                        MLKitElement.QR_CODE_SCAN -> QRCodeScanView(viewModel)
                        MLKitElement.FACE_DETECTION -> FaceDetectionView(viewModel)
                        MLKitElement.PICTURE_SEGMENTATION -> NotImplementedView(viewModel)
                        MLKitElement.LANGUAGE_DETECTION -> NotImplementedView(viewModel)
                        MLKitElement.TEXT_FEATURE_EXTRACTION -> NotImplementedView(viewModel)
                    }
                }
            }
        }
    }
}


@Composable
private fun MainView(viewModel: MainViewModel, mLKitElements: List<MLKitElement>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp),
    ) {
        items(mLKitElements) { element ->
            if(element != MLKitElement.MAIN_VIEW) {
                MLKitElementView(viewModel, element)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun MLKitElementView(viewModel: MainViewModel, element: MLKitElement) {
    Column(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .clickable {
                viewModel.onMLKitCardClicked(element)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.secondaryVariant,
                    shape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)
                )
                .padding(horizontal = 24.dp, vertical = 12.dp),
            text = GetTitle(element),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Icon(
            modifier = Modifier
                .size(150.dp)
                .padding(12.dp),
            painter = painterResource(id = getPainterId(element)),
            tint = MaterialTheme.colors.secondary,
            contentDescription = "image"
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 2.dp)
                .background(color = Color.Black)
        )
        Text(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp)
                )
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .fillMaxWidth(),
            text = getDescription(element)
        )
    }
}

@Composable
private fun getDescription(element: MLKitElement) =
    when(element){
        MLKitElement.MAIN_VIEW -> "Main view"
        MLKitElement.QR_CODE_SCAN -> "Scan and process barcodes. Supports most standard 1D and 2D formats."
        MLKitElement.FACE_DETECTION -> "Detect faces and facial landmarks."
        MLKitElement.PICTURE_SEGMENTATION -> "Separate the background from users within a scene and focus on what matters."
        MLKitElement.LANGUAGE_DETECTION -> "Determine the language of a string of text with only a few words."
        MLKitElement.TEXT_FEATURE_EXTRACTION -> "Detect and locate entities (such as addresses, date/time, phone numbers, and more) and take action based on those entities. Works in 15 languages."
    }
@Composable
private fun getPainterId(element: MLKitElement) =
    when(element){
        MLKitElement.MAIN_VIEW -> R.drawable.ic_face_detection
        MLKitElement.QR_CODE_SCAN -> R.drawable.ic_qr_code_scan
        MLKitElement.FACE_DETECTION -> R.drawable.ic_face_detection
        MLKitElement.PICTURE_SEGMENTATION -> R.drawable.ic_picture_segmentation
        MLKitElement.LANGUAGE_DETECTION -> R.drawable.ic_language_detextion
        MLKitElement.TEXT_FEATURE_EXTRACTION -> R.drawable.ic_text_feature_extraction
    }