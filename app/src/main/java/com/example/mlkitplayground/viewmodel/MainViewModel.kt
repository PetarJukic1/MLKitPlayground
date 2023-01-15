package com.example.mlkitplayground.viewmodel

import android.graphics.Rect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import com.example.mlkitplayground.ui.components.MLKitElement

class MainViewModel: ViewModel() {
    private val _current_view = mutableStateOf(MLKitElement.MAIN_VIEW)
    val current_view: State<MLKitElement> = _current_view

    private val _qr_code_counter = mutableStateOf(0)
    val qr_counter: State<Int> = _qr_code_counter

    private val _qr_code_rect = mutableStateOf(listOf(Rect()))

    val qrCodeBounds: State<List<Rect>> = _qr_code_rect

    private val _face_detected_rect = mutableStateOf(Rect())
    val faceBounds: State<Rect> = _face_detected_rect

    fun onFaceDetected(bounds: Rect){
        _face_detected_rect.value = bounds
    }

    fun onQrCodeScanned(counter: Int, bounds: List<Rect>){
        _qr_code_counter.value = counter
        _qr_code_rect.value = bounds
    }

    fun onMLKitCardClicked(element: MLKitElement){
        _current_view.value = element
    }
}