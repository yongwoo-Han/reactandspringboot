package com.okta.developer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "user_group")
public class Group {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    private String address;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    @ManyToOne(cascade=CascadeType.PERSIST) // 부모 값이 영속화 될때 자식도 영속화 (그룹 영속화시 유저 영속화)
    private User user;

    /** 
     * FetchType은 EAGER/ LAZY 있음
     * EAGER은 관계형 데이터를 미리 읽어오는 것
     * LAZY는 요청된 순간 데이터를 읽어오는 것
     * 둘의 차이는 읽어오는 주기가 다른것
     * ex) 여기서는 해당 Group 정보를 가져올 때 여러 Event 정보를 Set에 담아서 가져온다.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL) 
    private Set<Event> events;
}