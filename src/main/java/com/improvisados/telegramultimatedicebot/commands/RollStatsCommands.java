/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.improvisados.telegramultimatedicebot.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.improvisados.jdiceroller.InvalidRollException;
import com.improvisados.jdiceroller.JDiceRoller;
import com.improvisados.telegramultimatedicebot.TelegramUltimateDiceBot;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 *
 * @author jomartinez
 */
public class RollStatsCommands implements IBotCommand {

    private JDiceRoller dice;

    public RollStatsCommands() {
        dice = new JDiceRoller();
    }

    @Override
    public String getCommandIdentifier() {
        return "rollstats";
    }

    @Override
    public String getDescription() {
        return "Roll stats for D&D";
    }

    @Override
    public void processMessage(AbsSender absSender, Message msg, String[] arguments) {

        int[] stats = new int[6];

        int[] roll;

        for (int i = 0; i < stats.length; i++) {
            try {
                roll=dice.roll(4, 6, 3, JDiceRoller.KEEP_HIGHET);
                
                stats[i]=JDiceRoller.add(roll);  
                
                roll=null;
                
            } catch (InvalidRollException ex) {
                Logger.getLogger(RollStatsCommands.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Gson gson=new GsonBuilder().create();
        
        ((TelegramUltimateDiceBot)absSender).replay(absSender,msg,gson.toJson(stats));

    }

}
