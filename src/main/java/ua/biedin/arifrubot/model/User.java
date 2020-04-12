package ua.biedin.arifrubot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@Table(name = "user")
@ToString
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String family;
    private String surname;
    private String phone;
    private String email;
    private Integer age;
    private Timestamp birthday;
    private String position;

    public Integer getAge() {
        Period age = Period.between(birthday.toLocalDateTime().toLocalDate(), LocalDate.now());
        return age.getYears();
    }
}
