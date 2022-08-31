package lee.bowon.jwttutorial.repository

import lee.bowon.jwttutorial.entity.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    // eager 조회
    @EntityGraph(attributePaths = ["authorities"])
    fun findOneWithAuthoritiesByUsername(username: String): Optional<User>
    // username 을 기준으로 권한 정보도 한게 가져온다.
}