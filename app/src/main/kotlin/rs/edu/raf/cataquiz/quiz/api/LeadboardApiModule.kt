package rs.edu.raf.cataquiz.quiz.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import rs.edu.raf.cataquiz.networking.LeaderboardApiQualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LeaderboardApiModule {
    @Provides
    @Singleton
    fun provideLeaderboardApi(@LeaderboardApiQualifier retrofit: Retrofit): LeaderboardApi = retrofit.create()
}