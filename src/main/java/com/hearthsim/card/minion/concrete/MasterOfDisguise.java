package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionBattlecryInterface;
import com.hearthsim.card.minion.MinionTargetableBattlecry;
import com.hearthsim.event.CharacterFilter;
import com.hearthsim.event.CharacterFilterTargetedBattlecry;
import com.hearthsim.event.effect.CardEffectCharacter;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class MasterOfDisguise extends Minion implements MinionBattlecryInterface {

    private final static CharacterFilterTargetedBattlecry filter = new CharacterFilterTargetedBattlecry() {
        protected boolean includeOwnMinions() { return true; }
    };

    private final static CardEffectCharacter battlecryAction = new CardEffectCharacter() {
        @Override
        public HearthTreeNode applyEffect(PlayerSide originSide, Card origin, PlayerSide targetSide, int targetCharacterIndex, HearthTreeNode boardState) {
            Minion targetMinion = boardState.data_.modelForSide(targetSide).getCharacter(targetCharacterIndex);
            targetMinion.setStealthed(true);
            return boardState;
        }
    };

    public MasterOfDisguise() {
        super();
    }

    @Override
    public CharacterFilter getBattlecryFilter() {
        return MasterOfDisguise.filter;
    }

    @Override
    public CardEffectCharacter getBattlecryEffect() {
        return MasterOfDisguise.battlecryAction;
    }
}
