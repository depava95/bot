package ua.biedin.arifrubot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.biedin.arifrubot.model.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {

    Integer findByUserIdAndChatId(Integer userId, Long chatId);

}
