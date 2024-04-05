package com.bytes.a.half.simplii_android.utils

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FirebaseUtils {
    var database: DatabaseReference = Firebase.database.reference
    var auth  = Firebase.auth
}