package com.example.sandergit.redditblog.service;

import com.example.sandergit.redditblog.dto.CommentsDto;
import com.example.sandergit.redditblog.exceptions.PostNotFoundException;
import com.example.sandergit.redditblog.mapper.CommentMapper;
import com.example.sandergit.redditblog.model.Comment;
import com.example.sandergit.redditblog.dto.NotificationEmailDto;
import com.example.sandergit.redditblog.model.Post;
import com.example.sandergit.redditblog.model.User;
import com.example.sandergit.redditblog.repository.CommentRepository;
import com.example.sandergit.redditblog.repository.PostRepository;
import com.example.sandergit.redditblog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentService mailContentService;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, userService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentService.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmailDto(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName.toString()));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}