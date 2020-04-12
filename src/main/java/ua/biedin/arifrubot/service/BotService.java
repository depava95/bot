package ua.biedin.arifrubot.service;

import org.springframework.stereotype.Service;
import ua.biedin.arifrubot.model.User;
import ua.biedin.arifrubot.repository.StepRepository;
import ua.biedin.arifrubot.repository.UserRepository;

import java.util.List;

@Service
public class BotService {

    private final UserRepository userRepository;

    private final StepRepository stepRepository;

    public BotService(UserRepository userRepository, StepRepository stepRepository) {
        this.userRepository = userRepository;
        this.stepRepository = stepRepository;
    }

    public List<User> getAllByName(String name) {
        return userRepository.findAllByName(name);
    }

    public int getStep(Integer userId, Long chatId) {
        Integer step = stepRepository.findByUserIdAndChatId(userId, chatId);
        return step == null ? 0 : step;
    }

    public void createStep(Integer id, Long chatId, int menu_step) {

    }
}
