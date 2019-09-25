package com.qa.controllers;


import com.qa.models.Players;
import com.qa.repository.PlayersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayersControllerTest {

    @InjectMocks
    private PlayersController playersController;

    @Mock
    private PlayersRepository repository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllPlayers() {
        List<Players> playersList = new ArrayList<>();
        Players players = new Players();
        players.setPlayerName("name");
        players.setPlayerRating(0);
        players.setPlayerRole("role");
        playersList.add(players);


        when(repository.findAll()).thenReturn(playersList);
        assertEquals(playersController.listAllPlayers().get(0).getPlayerName(), "name");
    }

    @Test
    public void testAddPlayer() {
        Players players = new Players();
        when(!repository.exists(1L)).thenReturn(false);
        playersController.addPlayer(players);
        players.setPlayerName("name");
        when(repository.exists(1L)).thenReturn(true);

    }

    @Test
    public void testGetPlayer() {
        Players players = new Players();
        players.setPlayerName("name");
        when(repository.findOne(1L)).thenReturn(players);
        playersController.getPlayer(1L);
    }

    @Test
    public void testDeletePlayer() {
        Players players = new Players();
        players.setPlayerName("name");
        repository.findOne(1L);
        playersController.deletePlayer(1L);
        when(!repository.exists(1L)).thenReturn(true);
    }

    @Test
    public void testDeleteAllPlayers() {
        List<Players> playersList = new ArrayList<>();
        Players players = new Players();
        players.setPlayerName("name");
        players.setPlayerRating(0);
        players.setPlayerRole("role");
        playersList.add(players);
        when(repository.count() > 0).thenReturn(false);
        when(repository.count() == 0).thenReturn(true);
    }
}
