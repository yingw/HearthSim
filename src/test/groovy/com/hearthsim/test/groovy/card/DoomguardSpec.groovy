package com.hearthsim.test.groovy.card

import com.hearthsim.card.Card
import com.hearthsim.card.Deck
import com.hearthsim.card.minion.concrete.Doomguard
import com.hearthsim.card.spellcard.concrete.TheCoin
import com.hearthsim.card.spellcard.concrete.HolySmite
import com.hearthsim.model.BoardModel
import com.hearthsim.Game
import com.hearthsim.test.helpers.BoardModelBuilder
import com.hearthsim.util.tree.HearthTreeNode

import static com.hearthsim.model.PlayerSide.CURRENT_PLAYER
import static com.hearthsim.model.PlayerSide.WAITING_PLAYER
import static org.junit.Assert.*

class DoomguardSpec extends CardSpec {

	HearthTreeNode root
	BoardModel startingBoard

	def "playing Doomguard with no other cards in hand"() {

		startingBoard = new BoardModelBuilder().make {
			currentPlayer {
				hand([Doomguard])
				mana(7)
			}
			waitingPlayer {
				mana(4)
			}
		}

		root = new HearthTreeNode(startingBoard)

		def copiedBoard = startingBoard.deepCopy()
		def target = root.data_.getCharacter(CURRENT_PLAYER, 0)
		def theCard = root.data_.getCurrentPlayerCardHand(0)
		def ret = theCard.useOn(CURRENT_PLAYER, target, root, null, null)

		expect:
		assertFalse(ret == null);

		assertBoardDelta(copiedBoard, ret.data_) {
			currentPlayer {
				playMinionWithCharge(Doomguard)
				mana(2)
			}
		}
	}

	
	def "playing Doomguard with no one other card in hand"() {
		
		startingBoard = new BoardModelBuilder().make {
			currentPlayer {
				hand([Doomguard, TheCoin])
				mana(7)
			}
			waitingPlayer {
				mana(4)
			}
		}

		root = new HearthTreeNode(startingBoard)

		def copiedBoard = startingBoard.deepCopy()
		def target = root.data_.getCharacter(CURRENT_PLAYER, 0)
		def theCard = root.data_.getCurrentPlayerCardHand(0)
		def ret = theCard.useOn(CURRENT_PLAYER, target, root, null, null)

		expect:
		assertFalse(ret == null);

		assertBoardDelta(copiedBoard, ret.data_) {
			currentPlayer {
				playMinionWithCharge(Doomguard)
				mana(2)
			}
		}
		
		assertEquals(ret.numChildren(), 1);
		
		HearthTreeNode child0 = ret.getChildren().get(0);
		assertBoardDelta(copiedBoard, child0.data_) {
			currentPlayer {
				playMinionWithCharge(Doomguard)
				mana(2)
				removeCardFromHand(TheCoin)
			}
		}

	}
		
	
	
	
	def "playing Doomguard with no two other cards in hand"() {
		
		startingBoard = new BoardModelBuilder().make {
			currentPlayer {
				hand([Doomguard, TheCoin, HolySmite])
				mana(7)
			}
			waitingPlayer {
				mana(4)
			}
		}

		root = new HearthTreeNode(startingBoard)

		def copiedBoard = startingBoard.deepCopy()
		def target = root.data_.getCharacter(CURRENT_PLAYER, 0)
		def theCard = root.data_.getCurrentPlayerCardHand(0)
		def ret = theCard.useOn(CURRENT_PLAYER, target, root, null, null)

		expect:
		assertFalse(ret == null);

		assertBoardDelta(copiedBoard, ret.data_) {
			currentPlayer {
				playMinionWithCharge(Doomguard)
				mana(2)
			}
		}
		
		assertEquals(ret.numChildren(), 1);
		
		HearthTreeNode child0 = ret.getChildren().get(0);
		assertBoardDelta(copiedBoard, child0.data_) {
			currentPlayer {
				playMinionWithCharge(Doomguard)
				mana(2)
				removeCardFromHand(TheCoin)
				removeCardFromHand(HolySmite)
			}
		}

	}
}