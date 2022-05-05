package com.beomsu317.use_case.user

import com.beomsu317.use_case.exception.UnSupportedImageTypeException
import com.beomsu317.use_case.exception.UserNotFoundException
import io.ktor.http.*
import io.ktor.http.content.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class UploadImageUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String, part: PartData, serverUrl: String): UploadImageResult {
        val user = repository.getUserById(id) ?: throw UserNotFoundException()
        val uploadPath = "uploads/user/profile/"

        if (!File(uploadPath).isDirectory()) {
            Files.createDirectories(Paths.get(uploadPath))
        }

        var photoUri = "${uploadPath}${user.id}"
        when (part) {
            is PartData.FileItem -> {
                val fileBytes = part.streamProvider().readBytes()
                val type = part.contentType ?: throw Exception("type is unknown")

                if (type.contentType != "image") {
                    throw UnSupportedImageTypeException()
                }

                File(photoUri).writeBytes(fileBytes)
            }
        }
        val url = File(serverUrl, photoUri.replace("uploads", ""))
        repository.updateUser(user = user.copy(photoUrl = url.path))
        return UploadImageResult(url.path)
    }
}

@kotlinx.serialization.Serializable
data class UploadImageResult(
    val photoUrl: String
)

