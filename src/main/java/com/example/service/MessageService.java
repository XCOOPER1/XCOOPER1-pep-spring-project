package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) {
        // Validate message text
        if (message.getMessageText() == null || message.getMessageText().isBlank() ||
            message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text is invalid");
        }

        // Validate postedBy user exists
        if (message.getPostedBy() == null || accountRepository.findById(message.getPostedBy()).isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public void deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
        }
    }

    public boolean existsById(Integer messageId) {
        return messageRepository.existsById(messageId);
    }

    public Message updateMessage(Integer messageId, String newMessageText) {
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message == null || newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255) {
            throw new IllegalArgumentException("Invalid message data");
        }
        message.setMessageText(newMessageText);
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByUser(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}