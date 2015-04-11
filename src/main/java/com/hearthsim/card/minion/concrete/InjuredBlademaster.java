package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionBattlecryInterface;
import com.hearthsim.card.minion.MinionUntargetableBattlecry;
import com.hearthsim.event.effect.CardEffectCharacter;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class InjuredBlademaster extends Minion implements MinionBattlecryInterface {

    public InjuredBlademaster() {
        super();
    }

    /**
     * Battlecry: Deal 4 damage to himself
     */
    @Override
    public CardEffectCharacter getBattlecryEffect() {
        return new CardEffectCharacter<Minion>() {

            @Override
            public HearthTreeNode applyEffect(PlayerSide originSide, Minion origin, PlayerSide targetSide, int targetCharacterIndex, HearthTreeNode boardState) {
                HearthTreeNode toRet;
                toRet = origin.takeDamageAndNotify((byte) 4, PlayerSide.CURRENT_PLAYER, PlayerSide.CURRENT_PLAYER, boardState, false, true);
                return toRet;
            }
        };
    }
}
