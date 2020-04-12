package ua.biedin.arifrubot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Data
@EqualsAndHashCode
@Table(name = "step")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "chat_id")
    private Long chatId;
    private Integer step;
}
