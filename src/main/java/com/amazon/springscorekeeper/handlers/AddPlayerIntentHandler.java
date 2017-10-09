package com.amazon.springscorekeeper.handlers;

import com.alexaframework.springalexa.intent.IntentHandler;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.springscorekeeper.ScoreKeeperTextUtil;
import com.amazon.springscorekeeper.SkillContext;
import com.amazon.springscorekeeper.repository.ScoreKeeperDao;
import com.amazon.springscorekeeper.repository.ScoreKeeperGame;
import com.amazon.springscorekeeper.repository.ScoreKeeperGameData;
import org.springframework.beans.factory.annotation.Autowired;

public class AddPlayerIntentHandler implements IntentHandler {

    private static final String INTENT_NAME = "AddPlayerIntent";

    private static final String SLOT_PLAYER_NAME = "PlayerName";

    @Autowired
    private ScoreKeeperDao scoreKeeperDao;

    @Autowired
    private SkillContext skillContext;


    @Override
    public boolean canHandle(String intentName) {
        return INTENT_NAME.equals(intentName);
    }

    @Override
    public SpeechletResponse handle(Session session, Intent intent) {
        String newPlayerName =
                ScoreKeeperTextUtil.getPlayerName(intent.getSlot(SLOT_PLAYER_NAME).getValue());
        if (newPlayerName == null) {
            String speechText = "OK. Who do you want to add?";
            return getAskSpeechletResponse(speechText, speechText);
        }

        ScoreKeeperGame game = scoreKeeperDao.getScoreKeeperGame(session);
        if (game == null) {
            game = ScoreKeeperGame.newInstance(session, ScoreKeeperGameData.newInstance());
        }

        game.addPlayer(newPlayerName);


        scoreKeeperDao.saveScoreKeeperGame(game);
        String speechText = newPlayerName + " has joined your game. ";
        String repromptText = null;

        if (skillContext.needsMoreHelp()) {
            if (game.getNumberOfPlayers() == 1) {
                speechText += "You can say, I am done adding players. Now who's your next player?";

            } else {
                speechText += "Who is your next player?";
            }
            repromptText = ScoreKeeperTextUtil.NEXT_HELP;
        }

        if (repromptText != null) {
            return getAskSpeechletResponse(speechText, repromptText);
        } else {
            return getTellSpeechletResponse(speechText);
        }

    }
}
