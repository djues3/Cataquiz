package rs.edu.raf.cataquiz.catinfo.db.image

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import rs.edu.raf.cataquiz.catinfo.db.cat.Cat

@Entity(
    foreignKeys = [ForeignKey(
        entity = Cat::class,
        parentColumns = ["id"],
        childColumns = ["catId"],
        onDelete = ForeignKey.CASCADE,
    )],
)
data class Image(
    @PrimaryKey val id: String,
    val width: Int,
    val height: Int,
    val url: String,
    val catId: String,
)