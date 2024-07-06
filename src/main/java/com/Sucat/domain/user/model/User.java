package com.Sucat.domain.user.model;

import com.Sucat.domain.friendship.model.FriendShip;
import com.Sucat.domain.image.model.Image;
import com.Sucat.global.common.dao.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@Table(name = "users")// User는 예약어이기에 사용 불가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Email
    private String email;

    private String name;

    @NotNull
    private String password;

    private String department;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    /*연관관계 메서드*/
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FriendShip> mates = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private Image userImage;

    @Builder
    public User(String name, String email, String password, String department, String nickname) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.nickname = nickname;
    }

    /* 연관관계 메서드 */
    public void updateUserImage(Image image) {
        this.userImage = image;
    }

    /* Using Method */
    // 비밀번호 변경 메서드
    public void resetPassword(String newPassword) {
        this.password = newPassword;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateProfile(String nickname) {
        this.nickname = nickname;
    }
}
