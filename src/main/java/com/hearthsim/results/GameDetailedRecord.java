package com.hearthsim.results;

import com.hearthsim.exception.HSInvalidPlayerIndexException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.HearthActionBoardPair;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GameDetailedRecord implements GameRecord {

    ArrayList<TreeMap<Integer, BoardModel>> boards_;

    public GameDetailedRecord() {
        boards_ = new ArrayList<TreeMap<Integer, BoardModel>>(2);
        boards_.add(new TreeMap<Integer, BoardModel>());
        boards_.add(new TreeMap<Integer, BoardModel>());
    }

    @Override
    public void put(int turn, PlayerSide activePlayerSide, BoardModel board, List<HearthActionBoardPair> plays) {
        int index = GameDetailedRecord.getIndexOfPlayer(activePlayerSide);
        boards_.get(index).put(turn, board);
    }

    @Override
    public int getRecordLength(int playerId) {
        return boards_.get(playerId).size();
    }

    @Deprecated
    @Override
    public int getNumMinions(int playerId, int turn, int currentPlayerId) {
        BoardModel boardModel = boards_.get(currentPlayerId).get(turn);
        PlayerSide playerByIndex = GameDetailedRecord.getPlayerByIndex(playerId);
        PlayerSide otherPlayer = playerByIndex.getOtherPlayer();
        return boardModel.modelForSide(otherPlayer).getNumMinions();
    }

    @Override
    public int getNumCardsInHand(int playerId, int turn, int currentPlayerId) {
        try {
            BoardModel boardModel = boards_.get(currentPlayerId).get(turn);
            PlayerSide playerByIndex = GameDetailedRecord.getPlayerByIndex(playerId);
            PlayerSide otherPlayer = playerByIndex.getOtherPlayer();

            return boardModel.getNumCards_hand(otherPlayer);
        } catch (HSInvalidPlayerIndexException e) {
            return 0;
        }
    }

    @Override
    public int getHeroHealth(int playerId, int turn, int currentPlayerId) {
        BoardModel boardModel = boards_.get(currentPlayerId).get(turn);
        PlayerSide playerByIndex = GameDetailedRecord.getPlayerByIndex(playerId);
        PlayerSide otherPlayer = playerByIndex.getOtherPlayer();
        return boardModel.getHero(otherPlayer).getHealth();
    }

    @Override
    public int getHeroArmor(int playerId, int turn, int currentPlayerId) {
        BoardModel boardModel = boards_.get(currentPlayerId).get(turn);
        PlayerSide playerByIndex = GameDetailedRecord.getPlayerByIndex(playerId);
        PlayerSide otherPlayer = playerByIndex.getOtherPlayer();
        return boardModel.getHero(otherPlayer).getArmor();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        return json;
    }

    public BoardModel get(int turn, int playerID) {
        return boards_.get(playerID).get(turn);
    }

    // TODO: remove asap, simply to aid in refactoring
    public static int getIndexOfPlayer(PlayerSide playerSide) {
        if (playerSide == PlayerSide.CURRENT_PLAYER){
            return 0;
        } else {
            return 1;
        }
    }

    // TODO: remove asap, simply to aid in refactoring
    public static PlayerSide getPlayerByIndex(int index) {
        if (index == 0){
            return PlayerSide.CURRENT_PLAYER;
        } else {
            return PlayerSide.WAITING_PLAYER;
        }
    }
}
