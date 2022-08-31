package lee.bowon.jwttutorial.dto

import lee.bowon.jwttutorial.entity.Authority

data class AuthorityDto(
    var authorityName: String? = null
){
    companion object {
        fun from(authority: Authority): AuthorityDto {
            return authority.run {
                AuthorityDto(
                    authorityName = authorityName
                )
            }
        }
    }
}