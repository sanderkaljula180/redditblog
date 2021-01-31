package com.example.sandergit.redditblog.repository;


import com.example.sandergit.redditblog.model.Post;
import com.example.sandergit.redditblog.model.Subreddit;
import com.example.sandergit.redditblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
