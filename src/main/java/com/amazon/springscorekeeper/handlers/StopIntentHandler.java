package com.amazon.springscorekeeper.handlers;

import com.alexaframework.springalexa.intent.IntentHandler;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.springscorekeeper.ScoreKeeperTextUtil;
import com.amazon.springscorekeeper.SkillContext;
import org.springframework.beans.factory.annotation.Autowired;

public class StopIntentHandler implements IntentHandler {

    private static final String INTENT_NAME = "AMAZON.HelpIntent";

    @Autowired
    private SkillContext skillContext;

    @Override
    public boolean canHandle(String name) {
        return INTENT_NAME.equals(name);
    }

    @Override
    public SpeechletResponse handle(Session session, Intent intent) {
        return skillContext.needsMoreHelp() ? getTellSpeechletResponse("Okay. Whenever you're "
                + "ready, you can start giving points to the players in your game.")
                : getTellSpeechletResponse("");
    }
}
