package lee.bowon.jwttutorial.repository

import lee.bowon.jwttutorial.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, String> {
}