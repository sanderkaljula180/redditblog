package com.example.sandergit.redditblog.mapper;

import com.example.sandergit.redditblog.dto.PostRequestDto;
import com.example.sandergit.redditblog.dto.PostResponseDto;
import com.example.sandergit.redditblog.model.Post;
import com.example.sandergit.redditblog.model.Subreddit;
import com.example.sandergit.redditblog.model.User;
import com.example.sandergit.redditblog.repository.CommentRepository;
import com.example.sandergit.redditblog.repository.VoteRepository;
import com.example.sandergit.redditblog.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.marlonlom.utilities.timeago.TimeAgo;

@Mapper(componentModel = "spring")
public abstract class
PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserService userService;


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequestDto.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequestDto postRequestDto, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "voteCount", source = "post.voteCount")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponseDto mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}