package rs.edu.raf.cataquiz.quiz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rs.edu.raf.cataquiz.catinfo.repository.CatRepository
import rs.edu.raf.cataquiz.db.AppDatabase
import rs.edu.raf.cataquiz.quiz.play.QuestionGenerator
import rs.edu.raf.cataquiz.quiz.play.QuizRepository
import rs.edu.raf.cataquiz.quiz.play.QuizUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizModule {

    @Provides
    @Singleton
    fun provideQuestionGenerator(catRepository: CatRepository): QuestionGenerator {
        return QuestionGenerator(catRepository)
    }


    @Provides
    @Singleton
    fun provideQuizUseCase(questionGenerator: QuestionGenerator): QuizUseCase {
        return QuizUseCase(questionGenerator)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(database: AppDatabase): QuizRepository {
        return QuizRepository(database)
    }
}