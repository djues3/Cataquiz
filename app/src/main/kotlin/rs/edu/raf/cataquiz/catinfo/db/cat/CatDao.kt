package rs.edu.raf.cataquiz.catinfo.db.cat

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    /**
     * Used for learn page
     * */

    @Upsert
    suspend fun upsertCat(cat: Cat)

    @Upsert
    suspend fun upsertCats(cats: List<Cat>)

    @Query("SELECT * FROM Cat")
    fun observeAllCats(): Flow<List<Cat>>

    @Query("SELECT * FROM Cat WHERE id = :catId")
    suspend fun getCat(catId: String): Cat

    @Transaction
    @Query("SELECT * FROM Cat")
    suspend fun getAllCats(): List<CatWithImages>

    @Transaction
    @Query("SELECT * FROM Cat")
    fun observeCatsWithImages(): Flow<List<CatWithImages>>

    @Transaction
    @Query("SELECT * FROM Cat WHERE id = :catId")
    suspend fun getCatWithImages(catId: String): CatWithImages

    @Transaction
    @Query("SELECT * FROM Cat ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomCatsWithImages(limit: Int): List<CatWithImages>

    /**
     * Used for quiz generation
     * */
    @Query("SELECT * FROM Cat ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomCats(limit: Int): List<Cat>

    @Query("SELECT * FROM Cat ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCat(): Cat

    @Query("SELECT temperament FROM Cat")
    suspend fun getAllTemperaments(): List<String>

}
