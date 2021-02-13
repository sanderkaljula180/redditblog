package com.example.sandergit.redditblog.controller;

import com.example.sandergit.redditblog.dto.PostRequestDto;
import com.example.sandergit.redditblog.dto.PostResponseDto;
import com.example.sandergit.redditblog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequestDto postRequestDto) {
        postService.save(postRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponseDto>> getPostsBySubreddit(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponseDto>> getPostsByUsername(@PathVariable String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}
