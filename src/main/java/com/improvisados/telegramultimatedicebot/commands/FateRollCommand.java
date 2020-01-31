/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.improvisados.telegramultimatedicebot.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.improvisados.jdiceroller.JDiceRoller;
import com.improvisados.telegramultimatedicebot.TelegramUltimateDiceBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 *
 * @author jomartinez
 */
public class FateRollCommand implements IBotCommand
{

    private JDiceRoller dice;

    public FateRollCommand()
    {
        this.dice=new JDiceRoller();
    }
    
    
    @Override
    public String getCommandIdentifier()
    {
        return "froll";
    }

    @Override
    public String getDescription() {
        return "roll fudge dices";
    }

    @Override
    public void processMessage(AbsSender absSender, Message msg, String[] params)
    {
       int total=0;
       Gson gson = new GsonBuilder().create();
       int[] result = dice.roll(4, 6);
       String anwser=gson.toJson(result).replaceAll("[1|2]", " - ").replaceAll("[3|4]", "  ").replaceAll("[5|6]", " + ");

       if (params != null && params.length>0) {
            anwser+=params[0];
            total+=Integer.parseInt(params[0]);
        }        

        for (int val : result) {
            total += Math.floor((val - 1) / 2) - 1;
        }

        anwser+=" = "+total;
        
        ((TelegramUltimateDiceBot)absSender).replay(absSender,msg,anwser);
    }
    
}
