package com.zerobase.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String storeName;
    @Column
    private String location;
    @Column
    private String explanation;
    @Column
    private boolean deleted;

    public Store(String storeName, String location) {
        this.storeName = storeName;
        this.location = location;
    }
}
