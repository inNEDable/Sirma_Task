package com.sirma.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ColleaguesDuoDTO {

    private Long user1Id;

    private Long user2Id;

    private long days;

    public ColleaguesDuoDTO(Long user1Id, Long user2Id, long days) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.days = days;
    }
}
