package rs.edu.raf.cataquiz.catinfo.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rs.edu.raf.cataquiz.catinfo.api.model.BreedApiModel
import rs.edu.raf.cataquiz.catinfo.api.model.Image

interface CatApi {
    @GET("breeds")
    suspend fun getCats(): List<BreedApiModel>

    @GET("breeds/{breedId}")
    suspend fun getCat(
        @Path("breedId") breedId: String,
    ): BreedApiModel
    /**
     * Returns a list of images for a specific breed.
     * */
    @GET("images/search")
    suspend fun getImages(
        @Query("breed_ids") breedIds: String,
        @Query("limit") limit: Int = 10,
    ): List<Image>
}

