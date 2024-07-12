package rs.edu.raf.cataquiz.catinfo.db.cat

import androidx.room.Embedded
import androidx.room.Relation
import rs.edu.raf.cataquiz.catinfo.db.image.Image

data class CatWithImages(
    @Embedded val cat: Cat,
    @Relation(parentColumn = "id", entityColumn = "catId") val images: List<Image>,
)