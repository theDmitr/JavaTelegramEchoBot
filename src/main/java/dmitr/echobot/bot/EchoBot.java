package dmitr.echobot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class EchoBot extends TelegramLongPollingBot {

    public EchoBot(@Value("${bot.token}") String token) {
        super(token);
    }

    @Override
    public String getBotUsername() {
        return "EchoBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        echo(message);

        try {
            update.wait(2000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private void echo(Message message) {
        String chatIdStr = message.getChatId().toString();
        String replyMessage = message.getText();

        if (replyMessage == null)
            replyMessage = "I can`t receive not-text messages!";

        this.sendMessage(chatIdStr, replyMessage);
    }

    protected void sendMessage(String chatIdStr, String replyMessage) {
        SendMessage sendMessage = new SendMessage(chatIdStr, replyMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println(e.getMessage());
        }
    }

}
