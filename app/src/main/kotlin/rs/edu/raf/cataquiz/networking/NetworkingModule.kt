package rs.edu.raf.cataquiz.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import rs.edu.raf.cataquiz.config.AppJson
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class CatApiQualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class LeaderboardApiQualifier


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    @CatApiQualifier
    fun provideCatApiClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor {
            val updatedRequest = it.request().newBuilder().addHeader(
                "x-api-key", "live_uD7Zbg43O1QGbZ82cKzRuBNAMLIiDU7ieKA5M0NdudabP98rGTxkmo7TH5qyHkJG"
            ).build()
            it.proceed(updatedRequest)
        }.build()
    }

    @Singleton
    @CatApiQualifier
    @Provides
    fun provideCatApiRetrofit(
        @CatApiQualifier okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.thecatapi.com/v1/").client(okHttpClient)
            .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    @LeaderboardApiQualifier
    fun provideLeaderboardApiClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    @LeaderboardApiQualifier
    fun provideLeaderboardApiRetrofit(
        @CatApiQualifier okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://rma.finlab.rs/").client(okHttpClient)
            .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }


}