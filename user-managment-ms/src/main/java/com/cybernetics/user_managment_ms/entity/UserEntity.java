package com.cybernetics.user_managment_ms.entity;

import com.cybernetics.user_managment_ms.utils.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "NAME", length = 30, nullable = false)
    private String name;

    private String surname;

    @Column(name = "USER_NAME", length = 10, nullable = false, unique = true)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "EMAIL", length = 50, nullable = false)
    private String email;

    private String phoneNumber;

    private Instant birthDate;

    @CurrentTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
