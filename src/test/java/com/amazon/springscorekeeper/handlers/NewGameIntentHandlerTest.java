package com.amazon.springscorekeeper.handlers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class NewGameIntentHandlerTest {

    @InjectMocks
    private NewGameIntentHandler newGameIntentHandler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldReturnTrueWhenTheIntentNameIsNewGameIntent() throws Exception {

        boolean canHandle = newGameIntentHandler.canHandle("NewGameIntent");

        assertTrue(canHandle);
    }

    @Test
    public void shouldReturnFalseWhenTheIntentNameIsNotNewGameIntent() throws Exception {

        boolean canHandle = newGameIntentHandler.canHandle("NewGameIntentw");

        assertFalse(canHandle);
    }
}