package com.amazon.springscorekeeper.handlers;

import com.alexaframework.springalexa.intent.IntentHandler;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.springscorekeeper.SkillContext;
import com.amazon.springscorekeeper.repository.ScoreKeeperDao;
import com.amazon.springscorekeeper.repository.ScoreKeeperGame;
import org.springframework.beans.factory.annotation.Autowired;

public class NewGameIntentHandler implements IntentHandler {

    private static final String INTENT_NAME = "NewGameIntent";

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
        ScoreKeeperGame game = scoreKeeperDao.getScoreKeeperGame(session);

        if (game == null) {
            return getAskSpeechletResponse("New game started. Who's your first player?",
                    "Please tell me who\'s your first player?");
        }

        // Reset current game
        game.resetScores();
        scoreKeeperDao.saveScoreKeeperGame(game);

        String speechText =
                "New game started with " + game.getNumberOfPlayers() + " existing player"
                        + (game.getNumberOfPlayers() != 1 ? "" : "s") + ".";

        if (skillContext.needsMoreHelp()) {
            String repromptText =
                    "You can give a player points, add another player, reset all players or "
                            + "exit. What would you like?";
            speechText += repromptText;
            return getAskSpeechletResponse(speechText, repromptText);
        } else {
            return getTellSpeechletResponse(speechText);
        }
    }

}
