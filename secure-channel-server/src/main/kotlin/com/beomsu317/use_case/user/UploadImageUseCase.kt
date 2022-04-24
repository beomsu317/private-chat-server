package com.beomsu317.use_case.user

import com.beomsu317.use_case.exception.UnSupportedImageTypeException
import com.beomsu317.use_case.exception.UserDoesNotFoundException
import io.ktor.http.*
import io.ktor.http.content.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.io.File

class UploadImageUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String, part: PartData, serverUrl: String): UploadImageResult {
        val user = repository.getUserById(ObjectId(id).toId()) ?: throw UserDoesNotFoundException()
        var photoUri = "uploads/user/profile/${user.id}."
        when (part) {
            is PartData.FileItem -> {
                val fileBytes = part.streamProvider().readBytes()
                val type = part.contentType
                when (type) {
                    ContentType.Image.PNG -> {
                        photoUri = photoUri + ContentType.Image.PNG.contentSubtype
                        File(photoUri).writeBytes(fileBytes)
                    }
                    ContentType.Image.JPEG -> {
                        photoUri = photoUri + ContentType.Image.JPEG.contentSubtype
                        File(photoUri).writeBytes(fileBytes)
                    }
                    ContentType.Image.GIF -> {
                        photoUri = photoUri + ContentType.Image.GIF.contentSubtype
                        File(photoUri).writeBytes(fileBytes)
                    }
                    else -> {
                        throw UnSupportedImageTypeException()
                    }
                }
            }
        }
        photoUri = photoUri.replace("uploads", "")
        return UploadImageResult(serverUrl + photoUri)
    }
}

@kotlinx.serialization.Serializable
data class UploadImageResult(
    val photoUrl: String
)

