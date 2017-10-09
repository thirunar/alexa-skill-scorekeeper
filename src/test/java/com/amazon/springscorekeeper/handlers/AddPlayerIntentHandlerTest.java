package com.amazon.springscorekeeper.handlers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddPlayerIntentHandlerTest {

    @InjectMocks
    private AddPlayerIntentHandler addPlayerIntentHandler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldReturnTrueWhenTheIntentNameIsAddPlayerIntent() throws Exception {

        boolean canHandle = addPlayerIntentHandler.canHandle("AddPlayerIntent");

        assertTrue(canHandle);
    }

    @Test
    public void shouldReturnFalseWhenTheIntentNameIsNotAddPlayerIntent() throws Exception {

        boolean canHandle = addPlayerIntentHandler.canHandle("AddPlayerIntent1");

        assertFalse(canHandle);
    }

}