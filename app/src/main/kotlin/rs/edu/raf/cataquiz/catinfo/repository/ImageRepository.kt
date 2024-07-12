package rs.edu.raf.cataquiz.catinfo.repository

import rs.edu.raf.cataquiz.catinfo.api.CatApi
import rs.edu.raf.cataquiz.catinfo.api.model.Image as ImageApiModel
import rs.edu.raf.cataquiz.catinfo.db.image.Image as ImageDbModel
import rs.edu.raf.cataquiz.db.AppDatabase
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val catApi: CatApi,
    private val database: AppDatabase,
) {
    suspend fun fetchCatImages(catId: String) {
        val images = catApi.getImages(breedIds = catId)
        database.imageDao().upsertImages(images = images.map { it.asImageDbModel(catId = catId) })
    }

    suspend fun observeCatImages(catId: String) = database.imageDao().observeImages(catId = catId)

    suspend fun getImages(catId: String) = database.imageDao().getImages(catId = catId)
}

fun ImageApiModel.asImageDbModel(catId: String) = ImageDbModel(
    id = this.imageId,
    width = this.width,
    height = this.height,
    url = this.url,
    catId = catId,
)