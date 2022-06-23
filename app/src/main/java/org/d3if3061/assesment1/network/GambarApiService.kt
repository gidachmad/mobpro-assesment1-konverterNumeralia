package org.d3if3061.assesment1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3061.assesment1.db.Gambar
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "gidachmad/gidachmad.github.io/main/static-api-mobpro/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GambarApiService{
    @GET("static-api-mobpro.json")
    suspend fun getGambar() : List<Gambar>
}

object GambarApi {
    val service: GambarApiService by lazy {
        retrofit.create(GambarApiService::class.java)
    }

    fun getGambarUrl(nama: String): String{
        return "$BASE_URL$nama.jpg"
    }
}

enum class ApiStatus{ LOADING, SUCCESS, FAILED }

enum class GambarStatus{ WAITING, CONVERTED }