package com.fnvls.userservice.data;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_interests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@IdClass(InterestId.class)
public class UserInterest {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "created_at")
    @CreationTimestamp
    protected Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    protected Date updatedAt;
}
