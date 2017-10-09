package com.amazon.springscorekeeper.handlers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddScoreIntentHandlerTest {

    @InjectMocks
    private AddScoreIntentHandler addScoreIntentHandler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldReturnTrueWhenTheIntentNameIsAddScoreIntent() throws Exception {

        boolean canHandle = addScoreIntentHandler.canHandle("AddScoreIntent");

        assertTrue(canHandle);
    }

    @Test
    public void shouldReturnFalseWhenTheIntentNameIsNotAddScoreIntent() throws Exception {

        boolean canHandle = addScoreIntentHandler.canHandle("AddScoreIntentw");

        assertFalse(canHandle);
    }

}