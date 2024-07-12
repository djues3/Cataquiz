package rs.edu.raf.cataquiz.catinfo.db.cat

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Upsert
    suspend fun upsertCat(cat: Cat)

    @Upsert
    suspend fun upsertCats(cats: List<Cat>)

    @Query("SELECT * FROM Cat")
    fun observeAllCats(): Flow<List<Cat>>

    @Query("SELECT * FROM Cat WHERE id = :catId")
    suspend fun getCat(catId: String): Cat

}
