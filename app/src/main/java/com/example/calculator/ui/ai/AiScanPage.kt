package com.example.calculator.ui.ai

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner


@Composable
fun AiScanPage() {
    Box(modifier = Modifier.fillMaxSize()) {
        CameraViewer()
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(10.dp)
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(50)
                        )
                        .size(60.dp)
                ) {
                    Icon(
                        Icons.Outlined.Image, contentDescription = "Gallery", tint = Color.White
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                        .border(
                            4.dp,
                            Brush.radialGradient(
                                center = Offset(120f, 50f),
                                radius = 100f,
                                tileMode = TileMode.Mirror,
                                colors = listOf(
                                    Color.Blue, Color.Cyan, Color.Blue
                                )
                            ),
                            shape = RoundedCornerShape(50)
                        )
                        .size(83.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    center = Offset(120f, 50f),
                                    radius = 100f,
                                    tileMode = TileMode.Mirror,
                                    colors = listOf(
                                        Color.Blue, Color.Cyan, Color.Blue
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Icon(
                            Icons.Rounded.PhotoCamera,
                            contentDescription = "Gallery",
                            tint = Color.White,
                            modifier = Modifier
                                .size(34.dp)
                        )
                    }
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(10.dp)
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(50)
                        )
                        .size(60.dp)
                ) {
                    Icon(
                        Icons.Outlined.FlashOn, contentDescription = "Gallery", tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CameraViewer() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
        } catch (exc: Exception) {
            Log.e("CameraPreview", "Use case binding failed", exc)
        }
    }

    PermissionRequest()
}


@Composable
fun PermissionRequest() {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("RequestPermissionExample", "PERMISSION GRANTED")
        } else {
            Log.d("RequestPermissionExample", "PERMISSION DENIED")
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(android.Manifest.permission.CAMERA)
    }
}
