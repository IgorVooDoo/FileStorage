package com.ivd.example.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AccessKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "member_id")
    private Long memberId;

    public AccessKey() {
    }

    public AccessKey(Long userId, Long memberId) {
        this.userId = userId;
        this.memberId = memberId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
