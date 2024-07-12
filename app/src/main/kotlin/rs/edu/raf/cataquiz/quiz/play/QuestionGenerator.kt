package rs.edu.raf.cataquiz.quiz.play

import android.util.Log
import rs.edu.raf.cataquiz.catinfo.repository.CatRepository
import rs.edu.raf.cataquiz.quiz.model.Question
import rs.edu.raf.cataquiz.quiz.model.QuestionType
import javax.inject.Inject

private const val TAG = "QuestionGenerator"

class QuestionGenerator @Inject constructor(
    private val catRepository: CatRepository,
) {

    suspend fun generateQuestion(type: QuestionType): Question {
        return when (type) {
            QuestionType.BREED_IDENTIFICATION -> generateBreedIdQuestion()
            QuestionType.TEMPERAMENT_INTRUDER -> generateTemperamentIntruderQuestion()
            QuestionType.TEMPERAMENT_MATCH -> generateTemperamentMatchQuestion()
        }
    }


    private suspend fun generateBreedIdQuestion(): Question {
        Log.d(TAG, "Generating breed identification question")
        var cats = catRepository.getRandomCatWithImages(4)
        while (true) {
            if (!cats.any { it.images.isEmpty() }) {
                break;
            }
            cats = catRepository.getRandomCatWithImages(4)
        }
        val correct = cats.random()
        return Question(questionType = QuestionType.BREED_IDENTIFICATION,
            imageUrl = correct.images.random().url,
            correctAnswer = correct.cat.name,
            options = cats.map { it.cat.name })

    }

    private suspend fun generateTemperamentIntruderQuestion(): Question {
        Log.d(TAG, "Generating temperament intruder question")
        var correctCat = catRepository.getRandomCatWithImage()
        while (correctCat.images.isEmpty()) {
            val cats = catRepository.getRandomCatWithImages(4)
            correctCat = cats.random()
        }
        val correctTemperaments =
            correctCat.cat.temperament.split(", ").map { it.trim() }.shuffled().take(3)
        val wrongTemperaments =
            catRepository.getAllTemperaments().filter { it !in correctTemperaments }.shuffled()
                .first()
        val options = correctTemperaments + listOf(wrongTemperaments)
        return Question(
            questionType = QuestionType.TEMPERAMENT_INTRUDER,
            imageUrl = correctCat.images.random().url,
            correctAnswer = correctTemperaments.random(),
            options = options
        )
    }

    private suspend fun generateTemperamentMatchQuestion(): Question {
        Log.d(TAG, "Generating temperament match question")
        var correctCat = catRepository.getRandomCatWithImage()
        while (correctCat.images.isEmpty()) {
            val cats = catRepository.getRandomCatWithImages(4)
            correctCat = cats.random()
        }
        val allTemperaments = catRepository.getAllTemperaments()
        val correctTemperament = correctCat.cat.temperament.split(", ").map { it.trim() }.random()
        val wrongTemperaments = (allTemperaments - correctTemperament).take(3)
        val options = listOf(correctTemperament) + wrongTemperaments
        return Question(
            questionType = QuestionType.TEMPERAMENT_MATCH,
            imageUrl = correctCat.images.random().url,
            correctAnswer = correctTemperament,
            options = options
        )
    }
}