package rs.edu.raf.cataquiz.catinfo.repository

import rs.edu.raf.cataquiz.catinfo.api.CatApi
import rs.edu.raf.cataquiz.catinfo.db.cat.Cat
import rs.edu.raf.cataquiz.catinfo.db.cat.CatWithImages
import rs.edu.raf.cataquiz.catinfo.list.model.asCatDbModel
import rs.edu.raf.cataquiz.db.AppDatabase
import javax.inject.Inject


class CatRepository @Inject constructor(
    private val catApi: CatApi,
    private val database: AppDatabase,
) {
    suspend fun updateCache() {
        val breeds = catApi.getCats()
        database.catDao().upsertCats(cats = breeds.map { it.asCatDbModel() })
        breeds.forEach { breed ->
            breed.image?.let { image ->
                database.imageDao().upsertImage(image = image.asImageDbModel(catId = breed.breedId))
            }
        }
    }

    fun observeCats() = database.catDao().observeAllCats()

    suspend fun fetchCat(catId: String) {
        val cat = catApi.getCat(breedId = catId)
        database.catDao().upsertCat(cat = cat.asCatDbModel())
    }

    suspend fun getCat(catId: String) = database.catDao().getCat(catId = catId)

    suspend fun getCatWithImages(catId: String) = database.catDao().getCatWithImages(catId = catId)

    suspend fun getAllTemperaments(): Set<String> =
        database.catDao().getAllTemperaments().flatMap { it.split(",") }.map { it.trim() }.toSet()

    suspend fun getRandomCats(limit: Int): List<Cat> = database.catDao().getRandomCats(limit)

    suspend fun getRandomCatWithImages(limit: Int): List<CatWithImages> =
        database.catDao().getRandomCatsWithImages(
            limit = limit
        )

    suspend fun getRandomCatWithImage(): CatWithImages =
        database.catDao().getRandomCatsWithImages(limit = 1).first()

    fun observeCatWithImage(catId: String) = database.catDao().observeCatWithImage(catId = catId)

}

