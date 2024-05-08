package br.com.fiap.mentormate.data

data class UserData(
    var userId: String? = "",
    var name: String? = "",
    var username: String? = "",
    var imageUrl: String? = "",
    var bio: String? = ""
) {
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to username,
        "username" to username,
        "imageUrl" to imageUrl,
        "bio" to bio
    )
}