package com.example.kachploy

import android.content.Context
import android.net.Uri
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.util.UUID

class SupabaseStorageUtils(val context: Context) {

    val supabase = createSupabaseClient(
        "https://nlphuhvawpvmpafdzixf.supabase.co",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5scGh1aHZhd3B2bXBhZmR6aXhmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDY1MzA3MDAsImV4cCI6MjA2MjEwNjcwMH0.jSUqZob6t2tg0cyiLbpcxKxSOt6a9YvDGZfxshYdPwc"
    ) {
        install(Storage)
    }

    suspend fun uploadProfilePicture(uri: Uri): String? {
        try {
            val extension = uri.path?.substringAfterLast(".") ?: "jpg"
            val fileName = "${UUID.randomUUID()}.$extension"
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            supabase.storage.from(BUCKET_NAME).upload(fileName, inputStream.readBytes())
            val publicUrl = supabase.storage.from(BUCKET_NAME).publicUrl(fileName)
            return publicUrl
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }

    companion object {
        const val BUCKET_NAME = "kachploy-bucket"
    }
}