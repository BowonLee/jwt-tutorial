package lee.bowon.jwttutorial.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UserDto(
    @NotNull
    @field:Size(min = 3, max = 50)
    var userName: String? = null,

    @NotNull
    @field:Size(min = 3, max = 50)
    var password: String? = null,

    @NotNull
    @field:Size(min = 3, max = 50)
    var nickName: String? = null
)
