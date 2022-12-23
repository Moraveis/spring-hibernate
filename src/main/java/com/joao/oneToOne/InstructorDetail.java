package com.joao.oneToOne;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class InstructorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128)
    private String youTuBeChannel;

    @Column(length = 45)
    private String hobby;

    public InstructorDetail() {
    }

    public InstructorDetail(String youTuBeChannel, String hobby) {
        this.youTuBeChannel = youTuBeChannel;
        this.hobby = hobby;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYouTuBeChannel() {
        return youTuBeChannel;
    }

    public void setYouTuBeChannel(String youTuBeChannel) {
        this.youTuBeChannel = youTuBeChannel;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "InstructorDetail{" +
                "id=" + id +
                ", youTuBeChannel='" + youTuBeChannel + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructorDetail that = (InstructorDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(youTuBeChannel, that.youTuBeChannel) && Objects.equals(hobby, that.hobby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, youTuBeChannel, hobby);
    }
}
