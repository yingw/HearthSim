package com.hearthsim.test.minion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.ArcaneGolem;
import com.hearthsim.card.minion.concrete.BoulderfistOgre;
import com.hearthsim.card.minion.concrete.RaidLeader;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.exception.HSException;
import com.hearthsim.player.Player;
import com.hearthsim.player.playercontroller.ArtificialPlayer;
import com.hearthsim.util.boardstate.BoardState;
import com.hearthsim.util.tree.HearthTreeNode;

public class TestArcaneGolem {

	private HearthTreeNode board;
	private Deck deck;

	@Before
	public void setup() {
		board = new HearthTreeNode(new BoardState());

		Minion minion0_0 = new BoulderfistOgre();
		Minion minion0_1 = new RaidLeader();
		Minion minion1_0 = new BoulderfistOgre();
		Minion minion1_1 = new RaidLeader();
		
		board.data_.placeCard_hand_p0(minion0_0);
		board.data_.placeCard_hand_p0(minion0_1);
				
		board.data_.placeCard_hand_p1(minion1_0);
		board.data_.placeCard_hand_p1(minion1_1);

		Card cards[] = new Card[10];
		for (int index = 0; index < 10; ++index) {
			cards[index] = new TheCoin();
		}
	
		deck = new Deck(cards);

		Card fb = new ArcaneGolem();
		board.data_.placeCard_hand_p0(fb);

		board.data_.setMana_p0((byte)8);
		board.data_.setMana_p1((byte)8);
		
		board.data_.setMaxMana_p0((byte)8);
		board.data_.setMaxMana_p1((byte)8);
		
		HearthTreeNode tmpBoard = new HearthTreeNode(board.data_.flipPlayers());
		try {
			tmpBoard.data_.getCard_hand_p0(0).useOn(0, tmpBoard.data_.getHero_p0(), tmpBoard, deck, null);
			tmpBoard.data_.getCard_hand_p0(0).useOn(0, tmpBoard.data_.getHero_p0(), tmpBoard, deck, null);
		} catch (HSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board = new HearthTreeNode(tmpBoard.data_.flipPlayers());
		try {
			board.data_.getCard_hand_p0(0).useOn(0, board.data_.getHero_p0(), board, deck, null);
			board.data_.getCard_hand_p0(0).useOn(0, board.data_.getHero_p0(), board, deck, null);
		} catch (HSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.data_.resetMana();
		board.data_.resetMinions();
		
	}
	
	

	@Test
	public void test0() throws HSException {
		
		//null case
		Minion target = board.data_.getCharacter(1, 0);
		Card theCard = board.data_.getCard_hand_p0(0);
		HearthTreeNode ret = theCard.useOn(1, target, board, deck, null);
		
		assertTrue(ret == null);
		assertEquals(board.data_.getNumCards_hand(), 1);
		assertEquals(board.data_.getNumMinions_p0(), 2);
		assertEquals(board.data_.getNumMinions_p1(), 2);
		assertEquals(board.data_.getMana_p0(), 8);
		assertEquals(board.data_.getMana_p1(), 8);
		assertEquals(board.data_.getHero_p0().getHealth(), 30);
		assertEquals(board.data_.getHero_p1().getHealth(), 30);
		assertEquals(board.data_.getMinion_p0(0).getHealth(), 2);
		assertEquals(board.data_.getMinion_p0(1).getHealth(), 7);
		assertEquals(board.data_.getMinion_p1(0).getHealth(), 2);
		assertEquals(board.data_.getMinion_p1(1).getHealth(), 7);

		assertEquals(board.data_.getMinion_p0(0).getTotalAttack(), 2);
		assertEquals(board.data_.getMinion_p0(1).getTotalAttack(), 7);
		assertEquals(board.data_.getMinion_p1(0).getTotalAttack(), 2);
		assertEquals(board.data_.getMinion_p1(1).getTotalAttack(), 7);
	}
	
	@Test
	public void test1() throws HSException {
		
		//null case
		Minion target = board.data_.getCharacter(0, 2);
		Card theCard = board.data_.getCard_hand_p0(0);
		HearthTreeNode ret = theCard.useOn(0, target, board, deck, null);
		
		assertFalse(ret == null);
		assertEquals(board.data_.getNumCards_hand(), 0);
		assertEquals(board.data_.getNumMinions_p0(), 3);
		assertEquals(board.data_.getNumMinions_p1(), 2);
		assertEquals(board.data_.getMana_p0(), 5);
		assertEquals(board.data_.getMana_p1(), 9);
		assertEquals(board.data_.getHero_p0().getHealth(), 30);
		assertEquals(board.data_.getHero_p1().getHealth(), 30);
		assertEquals(board.data_.getMinion_p0(0).getHealth(), 2);
		assertEquals(board.data_.getMinion_p0(1).getHealth(), 7);
		assertEquals(board.data_.getMinion_p0(2).getHealth(), 2);
		assertEquals(board.data_.getMinion_p1(0).getHealth(), 2);
		assertEquals(board.data_.getMinion_p1(1).getHealth(), 7);

		assertEquals(board.data_.getMinion_p0(0).getTotalAttack(), 2);
		assertEquals(board.data_.getMinion_p0(1).getTotalAttack(), 7);
		assertEquals(board.data_.getMinion_p0(2).getTotalAttack(), 5);
		assertEquals(board.data_.getMinion_p1(0).getTotalAttack(), 2);
		assertEquals(board.data_.getMinion_p1(1).getTotalAttack(), 7);
		
		
		
		//----------------------------------------------------------
		
		Minion minion = board.data_.getMinion_p0(2);
		
		minion.hasAttacked(false);
		target = board.data_.getCharacter(1, 2);
		minion.attack(1, target, board, deck, null);

		assertEquals(board.data_.getNumCards_hand(), 0);
		assertEquals(board.data_.getNumMinions_p0(), 2);
		assertEquals(board.data_.getNumMinions_p1(), 2);
		assertEquals(board.data_.getMana_p0(), 5);
		assertEquals(board.data_.getMana_p1(), 9);
		assertEquals(board.data_.getHero_p0().getHealth(), 30);
		assertEquals(board.data_.getHero_p1().getHealth(), 30);
		assertEquals(board.data_.getMinion_p0(0).getHealth(), 2);
		assertEquals(board.data_.getMinion_p0(1).getHealth(), 7);
		assertEquals(board.data_.getMinion_p1(0).getHealth(), 2);
		assertEquals(board.data_.getMinion_p1(1).getHealth(), 7 - 5);

		assertEquals(board.data_.getMinion_p0(0).getTotalAttack(), 2);
		assertEquals(board.data_.getMinion_p0(1).getTotalAttack(), 7);
		assertEquals(board.data_.getMinion_p1(0).getTotalAttack(), 2);
		assertEquals(board.data_.getMinion_p1(1).getTotalAttack(), 7);
		
		
	}


	@Test
	public void test2() throws HSException {
		
		
		ArtificialPlayer ai0 = new ArtificialPlayer(
				0.9,
				0.9,
				1.0,
				1.0,
				1.0,
				0.1,
				0.1,
				0.1,
				0.5,
				0.5,
				0.0,
				0.5,
				0.0,
				0.0
				);
		
		Hero hero = new Hero();
		Player player0 = new Player("player0", hero, deck);
		Player player1 = new Player("player0", hero, deck);
		
		board.data_.setMana_p0((byte)3);
		board.data_.setMana_p1((byte)3);
		
		board.data_.setMaxMana_p0((byte)3);
		board.data_.setMaxMana_p1((byte)3);

		board.data_.getCharacter(0, 1).hasAttacked(true);
		board.data_.getCharacter(0, 2).hasAttacked(true);

		BoardState resBoard = ai0.playTurn(0, board.data_, player0, player1);
		
		assertEquals(resBoard.getNumCards_hand_p0(), 0);
		assertEquals(resBoard.getNumMinions_p0(), 3);
		assertEquals(resBoard.getNumMinions_p1(), 2); //1 minion should have been killed
		assertEquals(resBoard.getMana_p0(), 0); //3 mana used
		assertEquals(resBoard.getMana_p1(), 4); //1 mana given by the Arcane Golem
		assertEquals(resBoard.getHero_p0().getHealth(), 30);
		assertEquals(resBoard.getHero_p1().getHealth(), 25); //5 damage to the face... 4 from Arcane Golem (he has charge!), 1 more from Raid Leader's buff
	}
}
