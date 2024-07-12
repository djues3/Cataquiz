package rs.edu.raf.cataquiz.catinfo.repository

import rs.edu.raf.cataquiz.catinfo.api.CatApi
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
    }

    fun observeCats() = database.catDao().observeAllCats()

    suspend fun fetchCat(catId: String) {
        val cat = catApi.getCat(breedId = catId)
        database.catDao().upsertCat(cat = cat.asCatDbModel())
    }

    suspend fun getCat(catId: String) = database.catDao().getCat(catId = catId)
}

