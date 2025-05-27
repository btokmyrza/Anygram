package kz.btokmyrza.anygram.feature.chats.domain.model

class ChatPhotoEntity {

    data class FileInfo(
        val id: Int,
        val size: Long,
        val expectedSize: Long,
    )


}