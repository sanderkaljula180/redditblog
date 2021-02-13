package com.example.sandergit.redditblog.controller;

import com.example.sandergit.redditblog.dto.AuthenticationResponseDto;
import com.example.sandergit.redditblog.dto.LoginRequestDto;
import com.example.sandergit.redditblog.dto.RefreshTokenRequestDto;
import com.example.sandergit.redditblog.dto.RegisterRequestDto;
import com.example.sandergit.redditblog.service.UserService;
import com.example.sandergit.redditblog.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequestDto registerRequestDto) {
        userService.signup(registerRequestDto);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        userService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponseDto refreshTokens(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return userService.refreshToken(refreshTokenRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequestDto.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
