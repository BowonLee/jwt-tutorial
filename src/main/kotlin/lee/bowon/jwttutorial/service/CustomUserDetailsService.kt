package lee.bowon.jwttutorial.service

import jakarta.transaction.Transactional
import lee.bowon.jwttutorial.entity.Authority

import lee.bowon.jwttutorial.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.stream.Collectors

// 로그인 시 유저 정보와 권한 정보를 가져와 리턴해 주도록 한다.
@Component("userDetailsService")
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findOneWithAuthoritiesByUsername(username)
            .map { user: lee.bowon.jwttutorial.entity.User -> createUser(username, user) }
            .orElseThrow { UsernameNotFoundException("$username -> 데이터베이스에서 찾을 수 없습니다.") }
    }

    private fun createUser(username: String, user: lee.bowon.jwttutorial.entity.User): User {
        if (!user.isActivated) {
            throw RuntimeException("$username -> 활성화되어 있지 않습니다.")
        }

        val grantedAuthorities = user.authorities!!.stream()
            .map { authority: Authority -> SimpleGrantedAuthority(authority.authorityName) }
            .collect(Collectors.toList())

        return User(
            user.username,
            user.password,
            grantedAuthorities
        )
    }
}