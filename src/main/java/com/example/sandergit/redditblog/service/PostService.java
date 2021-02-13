package com.example.sandergit.redditblog.service;

import com.example.sandergit.redditblog.dto.PostRequestDto;
import com.example.sandergit.redditblog.dto.PostResponseDto;
import com.example.sandergit.redditblog.exceptions.PostNotFoundException;
import com.example.sandergit.redditblog.exceptions.SubredditNotFoundException;
import com.example.sandergit.redditblog.mapper.PostMapper;
import com.example.sandergit.redditblog.model.Post;
import com.example.sandergit.redditblog.model.Subreddit;
import com.example.sandergit.redditblog.model.User;
import com.example.sandergit.redditblog.repository.PostRepository;
import com.example.sandergit.redditblog.repository.SubredditRepository;
import com.example.sandergit.redditblog.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PostMapper postMapper;

    public void save(PostRequestDto postRequestDto) {
        Subreddit subreddit = subredditRepository.findByName(postRequestDto.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequestDto.getSubredditName()));
        Post post = postMapper.map(postRequestDto, subreddit, userService.getCurrentUser());
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
