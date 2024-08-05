package com.pj.planjourney.domain.user.entity;

import com.pj.planjourney.domain.blacklist.entity.BlackList;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChildComment> childComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private Set<FriendRequest> sentRequests = new HashSet<>();
    @OneToMany(mappedBy = "receiver")
    private Set<FriendRequest> receivedRequests = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private Set<Friend> friends = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<>();

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
    private BlackList blackLists;

    public User(String email, String encode, String nickname) {
        super();
    }


    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
