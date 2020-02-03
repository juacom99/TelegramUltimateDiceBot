/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.improvisados.telegramultimatedicebot;

import com.improvisados.telegramultimatedicebot.commands.FateRollCommand;
import com.improvisados.telegramultimatedicebot.commands.RollCommand;
import com.improvisados.telegramultimatedicebot.commands.RollStatsCommands;
import com.improvisados.telegramultimatedicebot.configuration.Configuration;
import java.io.FileNotFoundException;
import java.net.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 *
 * @author jomartinez
 */
public class Main {

    public static void main(String[] args) {
        try {
            Configuration cfg = Configuration.getInstance();
            TelegramUltimateDiceBot bot = null;

            ApiContextInitializer.init();

            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi();
            
             DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            if (cfg.getProxy() != null) {
                Proxy proxy = cfg.getProxy();

               // Set up Http proxy
               botOptions.setProxyHost("10.1.20.104");
                botOptions.setProxyPort(8080);
                // Select proxy type: [HTTP|SOCKS4|SOCKS5] (default: NO_PROXY)
                botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
            }
                // Register your newly created AbilityBot
                bot = new TelegramUltimateDiceBot(cfg.getToken(), "UDiceBot", botOptions);

            
            botsApi.registerBot(bot);
            bot.register(new RollCommand());
            bot.register(new FateRollCommand());
            bot.register(new RollStatsCommands());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TelegramApiRequestException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
