package rs.edu.raf.cataquiz.catinfo.list.model

import rs.edu.raf.cataquiz.catinfo.api.model.BreedApiModel
import rs.edu.raf.cataquiz.catinfo.db.cat.Cat

data class CatUiModel(
    val id: String,
    val name: String,
    val altNames: List<String>,
    val description: String,
    val temperament: List<String>,
)
fun BreedApiModel.asCatDbModel(): Cat {
    return Cat(
        id = this.breedId,
        weight = this.weight,
        image = this.image,
        name = this.name,
        temperament = this.temperament,
        origin = this.origin,
        countryCodes = this.countryCodes,
        countryCode = this.countryCode,
        description = this.description,
        lifeSpan = this.lifeSpan,
        indoor = this.indoor,
        lap = this.lap,
        altNames = this.altNames,
        adaptability = this.adaptability,
        affectionLevel = this.affectionLevel,
        childFriendly = this.childFriendly,
        dogFriendly = this.dogFriendly,
        energyLevel = this.energyLevel,
        grooming = this.grooming,
        healthIssues = this.healthIssues,
        intelligence = this.intelligence,
        sheddingLevel = this.sheddingLevel,
        socialNeeds = this.socialNeeds,
        strangerFriendly = this.strangerFriendly,
        vocalisation = this.vocalisation,
        experimental = this.experimental,
        hairless = this.hairless,
        natural = this.natural,
        rare = this.rare,
        rex = this.rex,
        suppressedTail = this.suppressedTail,
        shortLegs = this.shortLegs,
        wikipediaUrl = this.wikipediaUrl,
        hypoallergenic = this.hypoallergenic,
        referenceImageId = this.referenceImageId,
    )
}


