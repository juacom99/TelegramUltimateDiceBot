/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.improvisados.telegramultimatedicebot;

import com.improvisados.telegramultimatedicebot.commands.RollCommand;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author jomartinez
 */
public class TelegramUltimateDiceBot extends TelegramLongPollingCommandBot {

    private String botUsername;
    private String botToken;

    public TelegramUltimateDiceBot(String botToken, String botUsername, DefaultBotOptions options) {
        super(options,false);        
        this.botUsername = botUsername;
        this.botToken = botToken;
         
    }

    @Override
    protected void processInvalidCommandUpdate(Update update) {
         SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId()).setText("Invalid Command, type /help for mor information");
         
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public TelegramUltimateDiceBot(String botToken, String botUsername) {
        super(null,false);
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        
        
    }
    
    public void replay(AbsSender abs, Message org,String strAnwset)
    {
        User from=org.getFrom();
        String msg="";
         if(org.isGroupMessage())
         {
             if(from.getUserName().trim().equals(""))
             {
                 msg="Rol For @"+from.getFirstName()+" "+from.getLastName();
             }
             else
             {
                 msg="Rol For @"+from.getUserName();
             }
             
             msg+="\n"+strAnwset;
             
         }
         else
         {
             msg=strAnwset;
         }
         
         SendMessage anwser = new SendMessage().setChatId(org.getChatId()).setText(msg);
        
        
         
        try {
            abs.execute(anwser); // Call method to send the message
        } catch (TelegramApiException ex) {
            Logger.getLogger(RollCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
