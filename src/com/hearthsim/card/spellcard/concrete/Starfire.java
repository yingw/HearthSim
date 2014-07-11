package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellDamage;
import com.hearthsim.exception.HSInvalidPlayerIndexException;
import com.hearthsim.util.BoardState;
import com.hearthsim.util.HearthTreeNode;

public class Starfire extends SpellDamage {


	public Starfire() {
		this(false);
	}

	public Starfire(boolean hasBeenUsed) {
		super("Starfire", (byte)6, (byte)5, hasBeenUsed);
	}

	@Override
	public Object deepCopy() {
		return new Starfire(this.hasBeenUsed_);
	}
	
	/**
	 * 
	 * Use the card on the given target
	 * 
	 * Deals 5 damage and draws a card
	 * 
	 * @param thisCardIndex The index (position) of the card in the hand
	 * @param playerIndex The index of the target player.  0 if targeting yourself or your own minions, 1 if targeting the enemy
	 * @param minionIndex The index of the target minion.
	 * @param boardState The BoardState before this card has performed its action.  It will be manipulated and returned.
	 * 
	 * @return The boardState is manipulated and returned
	 */
	@Override
	protected HearthTreeNode<BoardState> use_core(
			int thisCardIndex,
			int playerIndex,
			int minionIndex,
			HearthTreeNode<BoardState> boardState,
			Deck deck)
		throws HSInvalidPlayerIndexException
	{
		HearthTreeNode<BoardState> toRet = super.use_core(thisCardIndex, playerIndex, minionIndex, boardState, deck);
		Card card = deck.drawCard(boardState.data_.getDeckPos_p0());
		if (card == null) {
			byte fatigueDamage = boardState.data_.getFatigueDamage_p0();
			boardState.data_.setFatigueDamage_p0((byte)(fatigueDamage + 1));
			boardState.data_.getHero_p0().setHealth((byte)(boardState.data_.getHero_p0().getHealth() - fatigueDamage));
		} else {
			boardState.data_.placeCard_hand_p0(card);
			boardState.data_.setDeckPos_p0(boardState.data_.getDeckPos_p0() + 1);
		}
		return boardState;
	}
}