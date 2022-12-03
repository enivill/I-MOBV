package sk.mobv.androidprojectschool.network

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import sk.mobv.androidprojectschool.BuildConfig

private const val BASE_URL =
    "https://data.mongodb-api.com/app/data-fswjp/endpoint/data/v1/action/"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory())
    .build()
private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

data class BusinessApiServiceBody(
    @SerializedName("collection") val collection: String = "bars",
    @SerializedName("database") val database: String = "mobvapp",
    @SerializedName("dataSource") val dataSource: String = "Cluster0",
)

interface BusinessApiService{
    @Headers(
        "Content-Type: application/json",
        "Access-Control-Request-Headers: *",
        "api-key: ${BuildConfig.API_KEY}"
    )
    @POST("find")
    suspend fun getBusinesses(@Body businessApiServiceBody: BusinessApiServiceBody = BusinessApiServiceBody()): BusinessContainerDto
}

object BusinessApi{
    val retrofitService : BusinessApiService by lazy {
        retrofit.create(BusinessApiService::class.java) }
}