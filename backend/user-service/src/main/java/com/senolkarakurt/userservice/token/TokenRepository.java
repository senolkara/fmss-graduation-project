package com.senolkarakurt.userservice.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

  @Query(nativeQuery = true, value = """
      select t.* from token t inner join users u\s
      on t.user_id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(@Param("id") Long id);

  Optional<Token> findByToken(String token);
}
