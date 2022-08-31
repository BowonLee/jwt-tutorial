package lee.bowon.jwttutorial.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lee.bowon.jwttutorial.entity.User

data class UserDto(
    @NotNull
    @field:Size(min = 3, max = 50)
    var userName: String? = null,

    @NotNull
    @field:Size(min = 3, max = 50)
    var password: String? = null,

    @NotNull
    @field:Size(min = 3, max = 50)
    var nickName: String? = null,

    var authorityDtoSet: Set<AuthorityDto>? = null


){
    companion object {
        fun from(user: User): UserDto {
            return user.run {
                UserDto(
                    userName = username,
                    nickName = nickname,
                    authorityDtoSet = user.authorities!!
                        .map { authority ->
                            AuthorityDto(authority.authorityName)
                        }
                        .toSet()
                )
            }
        }
    }
}
