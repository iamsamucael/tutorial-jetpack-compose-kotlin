package com.samuelaraujo.tutorialjetpackcompose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddUserState(val name: String = "", val email: String = "", val role: String = ""): Parcelable