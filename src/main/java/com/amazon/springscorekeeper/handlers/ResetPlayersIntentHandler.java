package com.amazon.springscorekeeper.handlers;

import com.alexaframework.springalexa.intent.IntentHandler;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.springscorekeeper.SkillContext;
import com.amazon.springscorekeeper.repository.ScoreKeeperDao;
import com.amazon.springscorekeeper.repository.ScoreKeeperGame;
import com.amazon.springscorekeeper.repository.ScoreKeeperGameData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.SortedMap;

public class ResetPlayersIntentHandler implements IntentHandler {

    private static final String INTENT_NAME = "TellScoresIntent";

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
        // Remove all players
        ScoreKeeperGame game =
                ScoreKeeperGame.newInstance(session, ScoreKeeperGameData.newInstance());
        scoreKeeperDao.saveScoreKeeperGame(game);

        String speechText = "New game started without players. Who do you want to add first?";
        return getAskSpeechletResponse(speechText, speechText);

    }
}
