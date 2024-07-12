package rs.edu.raf.cataquiz.catinfo.db.image

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Upsert
    suspend fun upsertImages(images: List<Image>)

    @Upsert
    suspend fun upsertImage(image: Image)


    @Query("SELECT * FROM Image WHERE catId = :catId")
    suspend fun getImages(catId: String): List<Image>

    @Query("SELECT * FROM Image WHERE catId = :catId")
    fun observeImages(catId: String): Flow<List<Image>>

}