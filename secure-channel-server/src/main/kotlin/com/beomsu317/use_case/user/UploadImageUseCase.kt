package com.beomsu317.use_case.user

import com.beomsu317.use_case.exception.UnSupportedImageTypeException
import com.beomsu317.use_case.exception.UserDoesNotExistsException
import io.ktor.http.*
import io.ktor.http.content.*
import java.io.File

class UploadImageUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String, part: PartData, serverUrl: String): UploadImageResult {
        val user = repository.getUserByEmail(email) ?: throw UserDoesNotExistsException()
        val id = user._id
        var photoUri = "uploads/user/profile/${id}."
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

