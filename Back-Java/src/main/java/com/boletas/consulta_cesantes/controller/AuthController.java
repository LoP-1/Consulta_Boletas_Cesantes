package com.boletas.consulta_cesantes.controller;

import com.boletas.consulta_cesantes.model.Token;
import com.boletas.consulta_cesantes.model.Usuario;
import com.boletas.consulta_cesantes.model.Rol;
import com.boletas.consulta_cesantes.service.TokenService;
import com.boletas.consulta_cesantes.service.UsuarioService;
import com.boletas.consulta_cesantes.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenService tokenService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorCorreo(request.getCorreo());
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            boolean passwordMatch = encoder.matches(request.getContrasena(), usuario.getContrasena());

            if (passwordMatch) {
                String token = jwtUtil.generateToken(usuario.getCorreo());
                tokenService.revocarTokensDeUsuario(usuario.getId());
                tokenService.guardarToken(token, usuario);
                return ResponseEntity.ok(new JwtResponse(token));
            }
        } else {
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }

    // REGISTRO
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (usuarioService.buscarPorCorreo(req.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(req.getNombre());
        usuario.setApellido(req.getApellido());
        usuario.setDni(req.getDni());
        usuario.setCorreo(req.getCorreo());
        String hashedPassword = encoder.encode(req.getContrasena());
        usuario.setContrasena(hashedPassword);
        usuario.setRol(req.getRol() != null ? req.getRol() : Rol.USER);
        usuarioService.guardar(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
          if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String tokenStr = authHeader.substring(7);
            Token token = tokenService.buscarPorToken(tokenStr);
            if (token != null) {
                tokenService.revocarToken(token);
                return ResponseEntity.ok("Sesión cerrada correctamente");
            }
        }
        return ResponseEntity.badRequest().body("Token no válido");
    }

    // DTOs
    static class LoginRequest {
        private String correo;
        private String contrasena;
        // getters y setters
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getContrasena() { return contrasena; }
        public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    }
    static class JwtResponse {
        private String token;
        public JwtResponse(String token) { this.token = token; }
        public String getToken() { return token; }
    }
    static class RegisterRequest {
        private String nombre;
        private String apellido;
        private String dni;
        private String correo;
        private String contrasena;
        private Rol rol; // opcional, si se quiere registrar admin

        // getters y setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }
        public String getDni() { return dni; }
        public void setDni(String dni) { this.dni = dni; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getContrasena() { return contrasena; }
        public void setContrasena(String contrasena) { this.contrasena = contrasena; }
        public Rol getRol() { return rol; }
        public void setRol(Rol rol) { this.rol = rol; }
    }
}