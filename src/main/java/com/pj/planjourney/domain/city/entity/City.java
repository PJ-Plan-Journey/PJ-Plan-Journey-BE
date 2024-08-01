package com.pj.planjourney.domain.city.entity;

import com.pj.planjourney.domain.plan.entity.Plan;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;

    @OneToMany(mappedBy = "city")
    private List<Plan> plans = new ArrayList<>();
}
