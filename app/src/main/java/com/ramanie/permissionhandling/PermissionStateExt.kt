package com.ramanie.permissionhandling

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDenied() :Boolean{
    return !this.hasPermission && !this.shouldShowRationale
}