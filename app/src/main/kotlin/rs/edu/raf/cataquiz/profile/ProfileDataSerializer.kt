package rs.edu.raf.cataquiz.profile

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import rs.edu.raf.cataquiz.config.AppJson
import java.io.InputStream
import java.io.OutputStream

class ProfileDataSerializer : Serializer<Profile> {
    override val defaultValue: Profile = Profile.EMPTY

    override suspend fun readFrom(input: InputStream): Profile {
           val text = String(input.readBytes(), charset = Charsets.UTF_8)
            return AppJson.decodeFromString<Profile>(text)
    }

    override suspend fun writeTo(t: Profile, output: OutputStream) {
        val text = AppJson.encodeToString(t)
        withContext(Dispatchers.IO) {
            output.write(text.toByteArray(charset = Charsets.UTF_8))
        }
    }

}