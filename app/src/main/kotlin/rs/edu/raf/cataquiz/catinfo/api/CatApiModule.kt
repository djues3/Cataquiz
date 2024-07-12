package rs.edu.raf.cataquiz.catinfo.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import rs.edu.raf.cataquiz.networking.CatApiQualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CatApiModule {

    @Provides
    @Singleton
    fun provideCatApi(@CatApiQualifier retrofit: Retrofit): CatApi = retrofit.create()
}