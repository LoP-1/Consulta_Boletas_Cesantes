package com.boletas.consulta_cesantes.service;

import com.boletas.consulta_cesantes.model.user.Token;
import com.boletas.consulta_cesantes.model.user.Usuario;
import com.boletas.consulta_cesantes.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token guardarToken(String tokenStr, Usuario usuario) {
        Token token = new Token();
        token.setToken(tokenStr);
        token.setUsuario(usuario);
        token.setRevoked(false);
        token.setExpired(false);
        return tokenRepository.save(token);
    }

    public List<Token> obtenerTokensDeUsuario(Long usuarioId) {
        return tokenRepository.findByUsuarioId(usuarioId);
    }

    public Token buscarPorToken(String tokenStr) {
        return tokenRepository.findByToken(tokenStr);
    }

    public void revocarToken(Token token) {
        token.setRevoked(true);
        tokenRepository.save(token);
    }

    public void revocarTokensDeUsuario(Long usuarioId) {
        List<Token> tokens = tokenRepository.findByUsuarioId(usuarioId);
        for (Token token : tokens) {
            if (!token.isRevoked() && !token.isExpired()) {
                token.setRevoked(true);
                tokenRepository.save(token);
            }
        }
    }

}