package rs.edu.raf.cataquiz.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.edu.raf.cataquiz.catinfo.db.cat.Cat
import rs.edu.raf.cataquiz.catinfo.db.cat.CatDao
import rs.edu.raf.cataquiz.catinfo.db.image.Image
import rs.edu.raf.cataquiz.catinfo.db.image.ImageDao
import rs.edu.raf.cataquiz.quiz.db.QuizResult
import rs.edu.raf.cataquiz.quiz.db.QuizResultDao


@Database(
    entities = [Cat::class, Image::class, QuizResult::class],
    version = 4,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
    abstract fun imageDao(): ImageDao
    abstract fun quizResultDao(): QuizResultDao
}