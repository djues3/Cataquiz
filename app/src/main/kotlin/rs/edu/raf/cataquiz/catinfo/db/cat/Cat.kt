package rs.edu.raf.cataquiz.catinfo.db.cat

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import rs.edu.raf.cataquiz.catinfo.api.model.Weight

@Entity
data class Cat(
    @PrimaryKey val id: String,
    @Embedded val weight: Weight,
    val name: String,
    val temperament: String,
    val origin: String,
    val countryCodes: String,
    val countryCode: String,
    val description: String,
    val lifeSpan: String,
    val indoor: Int,
    val lap: Int,
    val altNames: String,
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
    val experimental: Int,
    val hairless: Int,
    val natural: Int,
    val rare: Int,
    val rex: Int,
    val suppressedTail: Int,
    val shortLegs: Int,
    val wikipediaUrl: String,
    val hypoallergenic: Int,
    val referenceImageId: String,
)
