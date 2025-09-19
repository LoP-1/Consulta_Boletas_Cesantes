package com.boletas.consulta_cesantes.repository;

import com.boletas.consulta_cesantes.model.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUsuarioId(Long usuarioId);
    Token findByToken(String token);
}