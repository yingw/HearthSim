package com.hearthsim.test.minion;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.Abomination;
import com.hearthsim.card.minion.concrete.BoulderfistOgre;
import com.hearthsim.card.minion.concrete.RaidLeader;
import com.hearthsim.card.minion.concrete.ScarletCrusader;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAbomination {

    private HearthTreeNode board;
    private Deck deck;

    @Before
    public void setup() throws HSException {
        board = new HearthTreeNode(new BoardModel());

        Minion minion0_0 = new BoulderfistOgre();
        Minion minion0_1 = new RaidLeader();
        Minion minion1_0 = new BoulderfistOgre();
        Minion minion1_1 = new RaidLeader();
        Minion minion1_2 = new ScarletCrusader();

        board.data_.placeCardHandCurrentPlayer(minion0_0);
        board.data_.placeCardHandCurrentPlayer(minion0_1);

        board.data_.placeCardHandWaitingPlayer(minion1_0);
        board.data_.placeCardHandWaitingPlayer(minion1_1);
        board.data_.placeCardHandWaitingPlayer(minion1_2);

        Card cards[] = new Card[10];
        for (int index = 0; index < 10; ++index) {
            cards[index] = new TheCoin();
        }

        deck = new Deck(cards);

        Card fb = new Abomination();
        board.data_.placeCardHandCurrentPlayer(fb);

        board.data_.getCurrentPlayer().setMana((byte)18);
        board.data_.getWaitingPlayer().setMana((byte)18);

        board.data_.getCurrentPlayer().setMaxMana((byte)8);
        board.data_.getWaitingPlayer().setMaxMana((byte)8);

        HearthTreeNode tmpBoard = new HearthTreeNode(board.data_.flipPlayers());
        tmpBoard.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayer().getHero(), tmpBoard, deck, null);
        tmpBoard.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayer().getHero(), tmpBoard, deck, null);
        tmpBoard.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayer().getHero(), tmpBoard, deck, null);

        board = new HearthTreeNode(tmpBoard.data_.flipPlayers());
        board.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, board.data_.getCurrentPlayer().getHero(), board, deck, null);
        board.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, board.data_.getCurrentPlayer().getHero(), board, deck, null);

        board.data_.resetMana();
        board.data_.resetMinions();

    }

    @Test
    public void test0() throws HSException {
        Card theCard = board.data_.getCurrentPlayerCardHand(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.WAITING_PLAYER, 0, board, deck, null);

        assertNull(ret);
        PlayerModel currentPlayer = board.data_.modelForSide(PlayerSide.CURRENT_PLAYER);
        PlayerModel waitingPlayer = board.data_.modelForSide(PlayerSide.WAITING_PLAYER);

        assertEquals(board.data_.getNumCards_hand(), 1);
        assertEquals(currentPlayer.getNumMinions(), 2);
        assertEquals(waitingPlayer.getNumMinions(), 3);
        assertEquals(board.data_.getCurrentPlayer().getMana(), 8);
        assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
        assertEquals(board.data_.getCurrentPlayer().getHero().getHealth(), 30);
        assertEquals(board.data_.getWaitingPlayer().getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), 1);
        assertEquals(waitingPlayer.getMinions().get(1).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getHealth(), 7);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 4);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getTotalAttack(), 7);

        assertTrue(waitingPlayer.getMinions().get(0).getDivineShield());
    }

    @Test
    public void test1() throws HSException {
        Card theCard = board.data_.getCurrentPlayerCardHand(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 0, board, deck, null);

        assertFalse(ret == null);
        PlayerModel currentPlayer = board.data_.modelForSide(PlayerSide.CURRENT_PLAYER);
        PlayerModel waitingPlayer = board.data_.modelForSide(PlayerSide.WAITING_PLAYER);

        assertEquals(board.data_.getNumCards_hand(), 0);
        assertEquals(currentPlayer.getNumMinions(), 3);
        assertEquals(waitingPlayer.getNumMinions(), 3);
        assertEquals(board.data_.getCurrentPlayer().getMana(), 3);
        assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
        assertEquals(board.data_.getCurrentPlayer().getHero().getHealth(), 30);
        assertEquals(board.data_.getWaitingPlayer().getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), 4);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(2).getHealth(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), 1);
        assertEquals(waitingPlayer.getMinions().get(1).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getHealth(), 7);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 5);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(currentPlayer.getMinions().get(2).getTotalAttack(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 4);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getTotalAttack(), 7);

        assertTrue(waitingPlayer.getMinions().get(0).getDivineShield());
    }

    @Test
    public void test2() throws HSException {
        Card theCard = board.data_.getCurrentPlayerCardHand(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 0, board, deck, null);

        assertFalse(ret == null);
        PlayerModel currentPlayer = board.data_.modelForSide(PlayerSide.CURRENT_PLAYER);
        PlayerModel waitingPlayer = board.data_.modelForSide(PlayerSide.WAITING_PLAYER);

        assertEquals(board.data_.getNumCards_hand(), 0);
        assertEquals(currentPlayer.getNumMinions(), 3);
        assertEquals(waitingPlayer.getNumMinions(), 3);
        assertEquals(board.data_.getCurrentPlayer().getMana(), 3);
        assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
        assertEquals(board.data_.getCurrentPlayer().getHero().getHealth(), 30);
        assertEquals(board.data_.getWaitingPlayer().getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getTotalHealth(), 4);
        assertEquals(currentPlayer.getMinions().get(1).getTotalHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(2).getTotalHealth(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalHealth(), 1);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getTotalHealth(), 7);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 5);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(currentPlayer.getMinions().get(2).getTotalAttack(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 4);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getTotalAttack(), 7);

        assertTrue(waitingPlayer.getMinions().get(0).getDivineShield());

        //attack the Ogre... should kill everything except the Scarlet Crusader
        Minion target = board.data_.getCharacter(PlayerSide.WAITING_PLAYER, 3);
        Minion attacker = board.data_.getCharacter(PlayerSide.CURRENT_PLAYER, 1);
        attacker.hasAttacked(false);
        ret = attacker.attack(PlayerSide.WAITING_PLAYER, target, ret, null, null, false);

        assertFalse(ret == null);
        assertEquals(board.data_.getNumCards_hand(), 0);
        assertEquals(currentPlayer.getNumMinions(), 1);
        assertEquals(waitingPlayer.getNumMinions(), 1);
        assertEquals(board.data_.getCurrentPlayer().getMana(), 3);
        assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
        assertEquals(board.data_.getCurrentPlayer().getHero().getHealth(), 28);
        assertEquals(board.data_.getWaitingPlayer().getHero().getHealth(), 28);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), 5);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), 1);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 6);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 3);

        assertFalse(waitingPlayer.getMinions().get(0).getDivineShield());

    }

    @Test
    public void test3() throws HSException {
        Card theCard = board.data_.getCurrentPlayerCardHand(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 0, board, deck, null);

        assertFalse(ret == null);
        PlayerModel currentPlayer = board.data_.modelForSide(PlayerSide.CURRENT_PLAYER);
        PlayerModel waitingPlayer = board.data_.modelForSide(PlayerSide.WAITING_PLAYER);

        assertEquals(board.data_.getNumCards_hand(), 0);
        assertEquals(currentPlayer.getNumMinions(), 3);
        assertEquals(waitingPlayer.getNumMinions(), 3);
        assertEquals(board.data_.getCurrentPlayer().getMana(), 3);
        assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
        assertEquals(board.data_.getCurrentPlayer().getHero().getHealth(), 30);
        assertEquals(board.data_.getWaitingPlayer().getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), 4);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(2).getHealth(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), 1);
        assertEquals(waitingPlayer.getMinions().get(1).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getHealth(), 7);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 5);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(currentPlayer.getMinions().get(2).getTotalAttack(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 4);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getTotalAttack(), 7);

        assertTrue(waitingPlayer.getMinions().get(0).getDivineShield());

        //Silence the Abomination first, then attack with it
        Minion target = board.data_.getCharacter(PlayerSide.WAITING_PLAYER, 3);
        Minion attacker = board.data_.getCharacter(PlayerSide.CURRENT_PLAYER, 1);
        attacker.silenced(PlayerSide.CURRENT_PLAYER, board);
        attacker.hasAttacked(false);
        ret = attacker.attack(PlayerSide.WAITING_PLAYER, target, ret, null, null, false);

        assertEquals(board.data_.getNumCards_hand(), 0);
        assertEquals(currentPlayer.getNumMinions(), 2);
        assertEquals(waitingPlayer.getNumMinions(), 3);
        assertEquals(board.data_.getCurrentPlayer().getMana(), 3);
        assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
        assertEquals(board.data_.getCurrentPlayer().getHero().getHealth(), 30);
        assertEquals(board.data_.getWaitingPlayer().getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), 1);
        assertEquals(waitingPlayer.getMinions().get(1).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getHealth(), 2);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 4);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getTotalAttack(), 7);

        assertTrue(waitingPlayer.getMinions().get(0).getDivineShield());

    }
}
