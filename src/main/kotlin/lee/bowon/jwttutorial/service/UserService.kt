package lee.bowon.jwttutorial.service;


import lee.bowon.jwttutorial.dto.UserDto.Companion.from
import lee.bowon.jwttutorial.dto.UserDto;
import lee.bowon.jwttutorial.entity.Authority
import lee.bowon.jwttutorial.entity.User;
import lee.bowon.jwttutorial.exception.DuplicateMemberException
import lee.bowon.jwttutorial.exception.NotFoundMemberException
import lee.bowon.jwttutorial.repository.UserRepository;
import lee.bowon.jwttutorial.util.SecurityUtil
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun signup(userDto:UserDto): UserDto {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.userName!!).orElse(null) != null) {
            throw DuplicateMemberException("이미 가입되어 있는 유저입니다.")
        }

        val authority = Authority("ROLE_USER")

        val user = User(
            username = userDto.userName,
            password = passwordEncoder.encode(userDto.password),
            nickname = userDto.nickName,
            authorities = setOf(authority),
            isActivated = true
        )

        return from(userRepository.save(user))
    }

    // 인증정보를 인자로 유저 정보를 가져온다.
    @Transactional(readOnly = true)
    fun getUserWithAuthorities(username: String): UserDto {
        return from(
            userRepository.findOneWithAuthoritiesByUsername(username)
                .orElse(null)
        )
    }

    // 인증정보 없이 가져온다.
    @get:Transactional(readOnly = true)
    val myUserWithAuthorities: UserDto
        get() = from(
            SecurityUtil.currentUsername
                .flatMap {
                        username: String -> userRepository.findOneWithAuthoritiesByUsername(username)
                }
                .orElseThrow {
                    throw NotFoundMemberException("Member not found")
                }
        )
}