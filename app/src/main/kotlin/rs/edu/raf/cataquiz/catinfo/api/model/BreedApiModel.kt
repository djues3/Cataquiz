package rs.edu.raf.cataquiz.catinfo.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The model for a list of breeds.
 * GET /breeds
 *
 * */
@Serializable
data class BreedApiModel (
    val weight: Weight,
    @SerialName("id") val breedId: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val countryCodes: String = "",
    val countryCode: String = "",
    val description: String,
    val lifeSpan: String = "",
    val indoor: Int = 0,
    val lap: Int = 0,
    val altNames: String = "",
    val adaptability: Int = 0,
    val affectionLevel: Int = 0,
    val childFriendly: Int = 0,
    val dogFriendly: Int = 0,
    val energyLevel: Int = 0,
    val grooming: Int = 0,
    val healthIssues: Int = 0,
    val intelligence: Int = 0,
    val sheddingLevel: Int = 0,
    val socialNeeds: Int = 0,
    val strangerFriendly: Int = 0,
    val vocalisation: Int = 0,
    val experimental: Int = 0,
    val hairless: Int = 0,
    val natural: Int = 0,
    val rare: Int = 0,
    val rex: Int = 0,
    val suppressedTail: Int = 0,
    val shortLegs: Int = 0,
    @SerialName("wikipedia_url") val wikipediaUrl: String = "",
    val hypoallergenic: Int = 0,
    @SerialName("reference_image_id") val referenceImageId: String = "",
    val image: Image? = null,

    )
@Serializable
data class Weight(
    val imperial: String,
    val metric: String,
)
@Serializable
data class Image(
    @SerialName("id") val imageId: String,
    val width: Int,
    val height: Int,
    val url: String,
)

