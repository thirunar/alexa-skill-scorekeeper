package com.amazon.springscorekeeper.handlers;

import com.alexaframework.springalexa.intent.IntentHandler;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.springscorekeeper.ScoreKeeperTextUtil;
import com.amazon.springscorekeeper.repository.ScoreKeeperDao;
import com.amazon.springscorekeeper.repository.ScoreKeeperGame;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class AddScoreIntentHandler implements IntentHandler {

    private static final String INTENT_NAME = "AddScoreIntent";

    private static final String SLOT_PLAYER_NAME = "PlayerName";

    private static final String SLOT_SCORE_NUMBER = "ScoreNumber";

    private static final int MAX_PLAYERS_FOR_SPEECH = 3;

    @Autowired
    private ScoreKeeperDao scoreKeeperDao;

    @Override
    public boolean canHandle(String intentName) {
        return INTENT_NAME.equals(intentName);
    }

    @Override
    public SpeechletResponse handle(Session session, Intent intent) {
        String playerName =
                ScoreKeeperTextUtil.getPlayerName(intent.getSlot(SLOT_PLAYER_NAME).getValue());
        if (playerName == null) {
            String speechText = "Sorry, I did not hear the player name. Please say again?";
            return getAskSpeechletResponse(speechText, speechText);
        }

        int score = 0;
        try {
            score = Integer.parseInt(intent.getSlot(SLOT_SCORE_NUMBER).getValue());
        } catch (NumberFormatException e) {
            String speechText = "Sorry, I did not hear the points. Please say again?";
            return getAskSpeechletResponse(speechText, speechText);
        }

        ScoreKeeperGame game = scoreKeeperDao.getScoreKeeperGame(session);
        if (game == null) {
            return getTellSpeechletResponse("A game has not been started. Please say New Game to "
                    + "start a new game before adding scores.");
        }

        if (game.getNumberOfPlayers() == 0) {
            String speechText = "Sorry, no player has joined the game yet. What can I do for you?";
            return getAskSpeechletResponse(speechText, speechText);
        }

        // Update score
        if (!game.addScoreForPlayer(playerName, score)) {
            String speechText = "Sorry, " + playerName + " has not joined the game. What else?";
            return getAskSpeechletResponse(speechText, speechText);
        }

        // Save game
        scoreKeeperDao.saveScoreKeeperGame(game);

        // Prepare speech text. If the game has less than 3 players, skip reading scores for each
        // player for brevity.
        String speechText = score + " for " + playerName + ". ";
        if (game.getNumberOfPlayers() > MAX_PLAYERS_FOR_SPEECH) {
            speechText += playerName + " has " + game.getScoreForPlayer(playerName) + " in total.";
        } else {
            speechText += getAllScoresAsSpeechText(game.getAllScoresInDescndingOrder());
        }

        return getTellSpeechletResponse(speechText);

    }

    private String getAllScoresAsSpeechText(Map<String, Long> scores) {
        StringBuilder speechText = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, Long> entry : scores.entrySet()) {
            if (scores.size() > 1 && index == scores.size() - 1) {
                speechText.append(" and ");
            }
            String singularOrPluralPoints = entry.getValue() == 1 ? " point, " : " points, ";
            speechText
                    .append(entry.getKey())
                    .append(" has ")
                    .append(entry.getValue())
                    .append(singularOrPluralPoints);
            index++;
        }

        return speechText.toString();
    }

}
