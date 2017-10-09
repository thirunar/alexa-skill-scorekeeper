package com.amazon.springscorekeeper.speechlet;


import com.alexaframework.springalexa.speechlet.SpringSpeechlet;
import com.amazon.speech.speechlet.*;
import com.amazon.springscorekeeper.SkillContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScoreKeeperSpeechlet extends SpringSpeechlet {
    private static final Logger log = LoggerFactory.getLogger(ScoreKeeperSpeechlet.class);

    private ScoreKeeperManager scoreKeeperManager;

    private SkillContext skillContext;

    @Autowired
    public ScoreKeeperSpeechlet(ScoreKeeperManager scoreKeeperManager,
                                SkillContext skillContext) {
        this.scoreKeeperManager = scoreKeeperManager;
        this.skillContext = skillContext;
    }


    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        // if user said a one shot command that triggered an intent event,
        // it will start a new session, and then we should avoid speaking too many words.
        skillContext.setNeedsMoreHelp(false);
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        skillContext.setNeedsMoreHelp(true);
        return scoreKeeperManager.getLaunchResponse(request, session);
    }


    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any cleanup logic goes here
    }

}
