package rs.edu.raf.cataquiz.catinfo.db.image

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey val id: String,
    val width: Int,
    val height: Int,
    val url: String,
    val catId: String,
)