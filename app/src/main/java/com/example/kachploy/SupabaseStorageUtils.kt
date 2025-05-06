package com.example.kachploy

import android.content.Context
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage

class SupabaseStorageUtils(val context: Context) {

    val supabase = createSupabaseClient(
        "",
        ""
    ) {
        install(Storage)
    }



    companion object {
        const val BUCKET_NAME = ""
    }
}