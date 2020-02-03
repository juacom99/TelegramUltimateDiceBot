/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.improvisados.telegramultimatedicebot.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.improvisados.jdiceroller.JDiceRoller;
import com.improvisados.telegramultimatedicebot.TelegramUltimateDiceBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 *
 * @author jomartinez
 */
public class RollCommand implements IBotCommand
{
    
    private JDiceRoller dice;
    private Gson gson;

    public RollCommand() {
        dice=new JDiceRoller();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
   

    @Override
    public String getCommandIdentifier() {
        return "roll";
    }

    @Override
    public String getDescription() {
        return "Roll polyhedral dices";
    }

    @Override
    public void processMessage(AbsSender as, Message msg, String[] params)
    {
       
         String anwser="";
         if(params.length>0)
         {
            String result = dice.roll(params[0]);
            JsonObject res = gson.fromJson(result, JsonObject.class);
            if (res.get("success").getAsBoolean())
            {
               anwser=res.get("resultText").getAsString() + " = " + res.get("total").getAsString();
            }
            else
            {
                anwser="Error: The expression "+params[0]+" is not valid";
            }  
         }
         else
         {
             anwser="Nothing to roll";
         }
         ((TelegramUltimateDiceBot)as).replay(as,msg,anwser);
    }
    
    
    
}
