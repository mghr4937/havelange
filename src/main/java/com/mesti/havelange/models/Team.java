package com.mesti.havelange.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity(name = "teams")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "shortname")
    @Size(max = 3)
    private String shortname;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Email
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "club_colors")
    private String clubColors;
    private boolean enabled = true;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Player> players;

    public void setShortname(String shortname) {
        this.shortname = StringUtils.truncate(shortname, 3);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", city='" + city + "'" +
                '}';
    }
}
