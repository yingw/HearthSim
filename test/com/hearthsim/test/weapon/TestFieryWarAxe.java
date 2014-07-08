package com.hearthsim.test.weapon;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.card.spellcard.weapon.FieryWarAxe;
import com.hearthsim.exception.HSInvalidPlayerIndexException;
import com.hearthsim.util.BoardState;
import com.hearthsim.util.HearthTreeNode;

public class TestFieryWarAxe {

	private HearthTreeNode<BoardState> board;
	private Deck deck;
	private static final byte mana = 2;
	private static final byte attack0 = 2;
	private static final byte health0 = 3;
	private static final byte health1 = 7;

	@Before
	public void setup() {
		board = new HearthTreeNode<BoardState>(new BoardState());

		Minion minion0_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
		Minion minion0_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);
		Minion minion1_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
		Minion minion1_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);
		
		board.data_.placeMinion_p0(minion0_0);
		board.data_.placeMinion_p0(minion0_1);
		
		board.data_.placeMinion_p1(minion1_0);
		board.data_.placeMinion_p1(minion1_1);
		
		Card cards[] = new Card[10];
		for (int index = 0; index < 10; ++index) {
			cards[index] = new TheCoin();
		}
	
		deck = new Deck(cards);

		FieryWarAxe fb = new FieryWarAxe();
		board.data_.placeCard_hand_p0(fb);

	}
	
	@Test
	public void test0() throws HSInvalidPlayerIndexException {
		
		Card theCard = board.data_.getCard_hand_p0(0);
		HearthTreeNode<BoardState> ret = theCard.useOn(0, 1, 0, board, deck);
		
		assertTrue(ret == null);
		assertTrue(board.data_.getNumCards_hand() == 1);
		assertTrue(board.data_.getNumMinions_p0() == 2);
		assertTrue(board.data_.getNumMinions_p1() == 2);
		assertTrue(board.data_.getHero_p0().getHealth() == 30);
		assertTrue(board.data_.getHero_p1().getHealth() == 30);
		assertTrue(board.data_.getMinion_p0(0).getHealth() == health0);
		assertTrue(board.data_.getMinion_p0(1).getHealth() == health1 - 1);
		assertTrue(board.data_.getMinion_p1(0).getHealth() == health0);
		assertTrue(board.data_.getMinion_p1(1).getHealth() == health1 - 1);
	}


	@Test
	public void test1() throws HSInvalidPlayerIndexException {
		
		Card theCard = board.data_.getCard_hand_p0(0);
		HearthTreeNode<BoardState> ret = theCard.useOn(0, 0, 1, board, deck);
		
		assertTrue(ret == null);
		assertTrue(board.data_.getNumCards_hand() == 1);
		assertTrue(board.data_.getNumMinions_p0() == 2);
		assertTrue(board.data_.getNumMinions_p1() == 2);
		assertTrue(board.data_.getHero_p0().getHealth() == 30);
		assertTrue(board.data_.getHero_p1().getHealth() == 30);
		assertTrue(board.data_.getMinion_p0(0).getHealth() == health0);
		assertTrue(board.data_.getMinion_p0(1).getHealth() == health1 - 1);
		assertTrue(board.data_.getMinion_p1(0).getHealth() == health0);
		assertTrue(board.data_.getMinion_p1(1).getHealth() == health1 - 1);
	}

	@Test
	public void test2() throws HSInvalidPlayerIndexException {
		
		Card theCard = board.data_.getCard_hand_p0(0);
		HearthTreeNode<BoardState> ret = theCard.useOn(0, 0, 0, board, deck);
		
		assertFalse(ret == null);
		assertTrue(board.data_.getNumCards_hand() == 0);
		assertTrue(board.data_.getNumMinions_p0() == 2);
		assertTrue(board.data_.getNumMinions_p1() == 2);
		assertTrue(board.data_.getHero_p0().getHealth() == 30);
		assertTrue(board.data_.getHero_p1().getHealth() == 30);
		assertTrue(board.data_.getHero_p0().getAttack() == 3);
		assertTrue(board.data_.getHero_p0().getWeaponCharge() == 2);
		assertTrue(board.data_.getMinion_p0(0).getHealth() == health0);
		assertTrue(board.data_.getMinion_p0(1).getHealth() == health1 - 1);
		assertTrue(board.data_.getMinion_p1(0).getHealth() == health0);
		assertTrue(board.data_.getMinion_p1(1).getHealth() == health1 - 1);
		
		
		
		Minion hero = ret.data_.getHero_p0();
		ret = hero.attack(0, 1, 0, ret, deck);

		assertFalse(ret == null);
		assertTrue(board.data_.getNumCards_hand() == 0);
		assertTrue(board.data_.getNumMinions_p0() == 2);
		assertTrue(board.data_.getNumMinions_p1() == 2);
		assertTrue(board.data_.getHero_p0().getHealth() == 30);
		assertTrue(board.data_.getHero_p1().getHealth() == 27);
		assertTrue(board.data_.getHero_p0().getAttack() == 3);
		assertTrue(board.data_.getHero_p0().getWeaponCharge() == 1);
		assertTrue(board.data_.getMinion_p0(0).getHealth() == health0);
		assertTrue(board.data_.getMinion_p0(1).getHealth() == health1 - 1);
		assertTrue(board.data_.getMinion_p1(0).getHealth() == health0);
		assertTrue(board.data_.getMinion_p1(1).getHealth() == health1 - 1);
		
		ret = hero.attack(0, 1, 1, ret, deck);
		assertTrue(ret == null);
		
		
		hero.hasAttacked(false);
		ret = hero.attack(0, 1, 1, board, deck);
		assertFalse(ret == null);
		assertTrue(board.data_.getNumCards_hand() == 0);
		assertTrue(board.data_.getNumMinions_p0() == 2);
		assertTrue(board.data_.getNumMinions_p1() == 1);
		assertTrue(board.data_.getHero_p0().getHealth() == 30 - attack0);
		assertTrue(board.data_.getHero_p1().getHealth() == 27);
		assertTrue(board.data_.getHero_p0().getAttack() == 0);
		assertTrue(board.data_.getHero_p0().getWeaponCharge() == 0);
		assertTrue(board.data_.getMinion_p0(0).getHealth() == health0);
		assertTrue(board.data_.getMinion_p0(1).getHealth() == health1 - 1);
		assertTrue(board.data_.getMinion_p1(0).getHealth() == health1 - 1);

	}
}