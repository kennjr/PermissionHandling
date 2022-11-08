package com.ramanie.permissionhandling

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeFragment(){
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf( android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.CAMERA ))

    // this is what we'll need to get the current state of the activity
    val lifecycleOwner = LocalLifecycleOwner.current
    // since we don't have ctrl over how often the code in the block below gets called,
    // we're gonna use the DisposableEffect to call the code whenever the lifecycle of the activity changes
    // we'll need to remove the observer when the composable is killed/destroyed
    DisposableEffect(key1 = lifecycleOwner, effect = {
        // here's where we're gonna write the code that'll dispose of the lifecycleObserver when the composable needs to be disposed
        val observer = LifecycleEventObserver{source, event ->
            // we check whether the new event is the onstart so that we can check whether the permissions have been granted
            if (event == Lifecycle.Event.ON_START){
                permissionState.launchMultiplePermissionRequest()
            }
        }
        // we need to add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)
        // every DisposableEffect handler has to have this fun. this is the code that'll get executed
        // whenever the composable needs to to get cleaned
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

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
                    when{
                        perm.hasPermission -> {
                            Text(text = "Record audio permission accepted", textAlign = TextAlign.Center)
                        }
                        perm.shouldShowRationale -> {
                            Text(text = "Record audio permission is necessary for you to post a photo", textAlign = TextAlign.Center)
                        }
                        perm.isPermanentlyDenied() -> {
                            Text(text = "Record audio permission was denied, go to settings to changes that", textAlign = TextAlign.Center)
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                android.Manifest.permission.CAMERA -> {
                    when{
                        perm.hasPermission -> {
                            Text(text = "Camera permission accepted", textAlign = TextAlign.Center)
                        }
                        perm.shouldShowRationale -> {
                            Text(text = "Camera permission is necessary for you to post a photo", textAlign = TextAlign.Center)
                        }
                        perm.isPermanentlyDenied() -> {
                            Text(text = "Camera permission was denied, go to settings to changes that", textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}