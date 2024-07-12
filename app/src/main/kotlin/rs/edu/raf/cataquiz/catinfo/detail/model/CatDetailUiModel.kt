package rs.edu.raf.cataquiz.catinfo.detail.model

data class CatDetailUiModel(
    val id: String,
    val name: String,
    val description: String,
    val temperament: List<String>,
    val countriesOfOrigin: List<String>,
    val lifeSpan: String,
    val averageWeight: String,
    val adaptability: Int,
    val affectionLevel: Int,
    val childFriendly: Int,
    val dogFriendly: Int,
    val energyLevel: Int,
    val grooming: Int,
    val healthIssues: Int,
    val intelligence: Int,
    val sheddingLevel: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val vocalisation: Int,
    val isRare: Boolean,
    val wikipediaUrl: String,
    val images: List<ImageUIModel> = emptyList(),
)

data class ImageUIModel(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
)
