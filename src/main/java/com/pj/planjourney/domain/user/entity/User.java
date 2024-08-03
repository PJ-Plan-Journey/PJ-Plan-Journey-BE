package com.pj.planjourney.domain.user.entity;

import com.pj.planjourney.domain.childcomment.entity.ChildComment;
import com.pj.planjourney.domain.comment.entity.Comment;
import com.pj.planjourney.domain.follow.entity.Follow;
import com.pj.planjourney.domain.like.entity.Like;
import com.pj.planjourney.domain.userPlan.entity.UserPlan;
import com.pj.planjourney.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<UserPlan> userPlans = new ArrayList<>();  // 유저가 삭제되어도 게시글은 남아있다. 이 분분 유저 정보가 없는데 어떻게 처리할지

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChildComment> childComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();


    public User(String email,String password,String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }


    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
