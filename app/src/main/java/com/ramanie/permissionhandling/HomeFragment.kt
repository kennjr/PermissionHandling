package com.ramanie.permissionhandling

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeFragment(){
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf( android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.CAMERA ))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        permissionState.permissions.forEach { perm ->
            when(perm.permission){
                android.Manifest.permission.RECORD_AUDIO -> {

                }
                android.Manifest.permission.CAMERA -> {

                }
            }
        }
        Text(text = "Camera permission accepted", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Record audio permission accepted", textAlign = TextAlign.Center)
    }
}