package com.artoon.sort;

import com.artoon.sort.Card.Rank;
import com.artoon.sort.Card.Suit;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import DataStore.Item_Card;

/**
 * Models a hand of 10 cards. The hand is not sorted. Not threadsafe. The hand
 * is a set: adding the same card twice will not add duplicates of the card.
 * <p/>
 * size() > 0 size() <= HAND_SIZE
 */
public class Hand implements Serializable {
    private static final int HAND_MAX_SIZE = 12;
    private static final int MAX_CARD_VALUE = 10;
    public JSONArray MatchedCardsArray = new JSONArray();
    public JSONArray MatchedCardsArray1 = new JSONArray();
    public JSONArray UnMatchedCardsArray = new JSONArray();
    private Set<Card> aCards = new HashSet<>();
    private Set<Card> aUnmatchedCards = new HashSet<>();
    private Set<ICardSet> aMatchedSets = new HashSet<>();

    /**
     * Creates a new, empty hand.
     */
    public Hand() {

    }

    public Set<Card> getCards() {
        return aCards;
    }

    /**
     * Adds pCard to the list of unmatched cards. If the card is already in the
     * hand, it is not added.
     *
     * @param pCard The card to add.
     */
    public void add(Card pCard) {
        if (pCard == null) {
            return;
        }

        if (needToDiscard()) {
            // throw new HandException("The hand is complete");
        } else if (aCards.add(pCard)) {
            aUnmatchedCards.add(pCard);
        } else {
            // throw new HandException("The card is already in the hand");
        }
    }

    /**
     * Remove pCard from the hand and break any matched set that the card is
     * part of. Does nothing if pCard is not in the hand.
     *
     * @param pCard The card to remove.
     */
    public void remove(Card pCard) {
        if (pCard == null) {
            return;
        }
        aCards.remove(pCard);
        aUnmatchedCards.remove(pCard);
    }

    /**
     * @return True if the hand is complete.
     */
    public boolean isComplete() {
        return aCards.size() == HAND_MAX_SIZE - 1;
    }

    /**
     * @return true if the hand contains 1 extra card that need to be discarded
     */
    public boolean needToDiscard() {
        return aCards.size() == HAND_MAX_SIZE;
    }

    /**
     * Removes all the cards from the hand.
     */
    public void clear() {
        aCards.clear();
    }

    /**
     * @return A copy of the set of matched sets
     */
    public Set<ICardSet> getMatchedSets() {
        return new HashSet<ICardSet>(aMatchedSets);
    }

    /**
     * @return A copy of the set of unmatched cards.
     */
    public Set<Card> getUnmatchedCards() {
        return new HashSet<Card>(aUnmatchedCards);
    }

    /**
     * @return The number of cards in the hand.
     */
    public int size() {
        return aCards.size(); // TODO
    }

    /**
     * Determines if pCard is already in the hand, either as an unmatched card
     * or as part of a set.
     *
     * @param pCard The card to check.
     * @return true if the card is already in the hand.
     */
    public boolean contains(Card pCard) {
        return aCards.contains(pCard);
    }

    /**
     * @return The total point value of the unmatched cards in this hand.
     */
    public int score() {
        int score = 0;
        for (Card card : aUnmatchedCards) {
            score += Math.min(card.getRank().ordinal() + 1, MAX_CARD_VALUE);
        }
        return score;
    }

    /**
     * Creates a group of cards of the same rank.
     *
     * @param pCards The cards to groups
     */
    public void createGroup(Set<Card> pCards) {
        for (Card card : pCards) {
            if (!aUnmatchedCards.contains(card)) {
                throw new HandException("Error card is not in unmatched cards");
            }
        }
        ICardSet aSet = new CardSet(pCards);

        if (!aSet.isGroup()) {
            throw new HandException("Not a valid group");
        } else {
            aMatchedSets.add(aSet);
            for (Card card : pCards) {
                aUnmatchedCards.remove(card);
            }
        }
    }

    /**
     * Creates a run of cards of the same suit.
     *
     * @param pCards The cards to group in a run
     */
    public void createRun(Set<Card> pCards) {
        for (Card card : pCards) {
            if (!aUnmatchedCards.contains(card)) {
                throw new HandException("Error card is not in unmatched cards");
            }
        }
        ICardSet aSet = new CardSet(pCards);

        if (!aSet.isRun()) {
            throw new HandException("Not a valid run");
        } else {
            aMatchedSets.add(aSet);
            for (Card card : pCards) {
                aUnmatchedCards.remove(card);
            }
        }
    }

    /**
     * Calculates the matching of cards into groups and runs that results in the
     * lowest amount of points for unmatched cards.
     */
    public void autoMatch() {
        List<Card> sortedCards = new ArrayList<>(aUnmatchedCards);
        try {
            Collections.sort(sortedCards);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<List<Card>> groups = getPotentialGroups();
        List<List<Card>> runs = getPotentialRuns();
        int bestScore = Integer.MAX_VALUE;
        Hand bestHand = this;

        for (List<Card> cards : runs) {
            Hand clone = this.clone();
            clone.createRun(new HashSet<>(cards));
            clone.autoMatch();
            int score = clone.score();
            if (score < bestScore) {
                if (!(clone.needToDiscard() && clone.getUnmatchedCards().size() == 0)) {
                    bestScore = score;
                    bestHand = clone;
                }
            }
        }
        for (List<Card> cards : groups) {
            Hand clone = this.clone();
            clone.createGroup(new HashSet<>(cards));
            clone.autoMatch();
            int score = clone.score();
            if (score < bestScore) {
                if (!(clone.needToDiscard() && clone.getUnmatchedCards().size() == 0)) {
                    bestScore = score;
                    bestHand = clone;
                }
            }
        }

        this.aUnmatchedCards = bestHand.aUnmatchedCards;
        this.aMatchedSets = bestHand.aMatchedSets;
    }

    public Hand clone() {
        Hand clone = new Hand();
        clone.aCards = new HashSet<>(aCards);
        clone.aUnmatchedCards = getUnmatchedCards();
        clone.aMatchedSets = getMatchedSets();

        return clone;
    }

    /**
     * @return hash map of potential groups
     */
    private List<List<Card>> getPotentialGroups() {
        if (getUnmatchedCards().size() < 3) {
            return new ArrayList<>();
        }
        Map<Card.Rank, List<Card>> groups = new HashMap<>();
        for (Card card : getUnmatchedCards()) {
            if (!groups.containsKey(card.getRank())) {
                groups.put(card.getRank(), new ArrayList<Card>());
            }
            groups.get(card.getRank()).add(card);
        }
        List<List<Card>> potentialGroups = new ArrayList<>();
        for (Card.Rank rank : groups.keySet()) {
            if (groups.get(rank).size() >= 3) {
                potentialGroups.add(groups.get(rank));
            }
            if (groups.get(rank).size() == 4) {
                List<Card> cards = groups.get(rank);

                for (Card card : cards) {
                    List<Card> clone = new ArrayList<>(cards);
                    clone.remove(card);
                    potentialGroups.add(clone);
                }
            }
        }
        return potentialGroups;
    }

    /**
     * @return list of potential suits
     */
    private List<List<Card>> getPotentialRuns() {
        if (getUnmatchedCards().size() < 3) {
            return new ArrayList<>();
        }
        List<List<Card>> potentialSeries = new ArrayList<>();
        Map<Card.Suit, List<Card>> suits = new HashMap<>();
        for (Card card : getUnmatchedCards()) {
            if (!suits.containsKey(card.getSuit())) {
                suits.put(card.getSuit(), new ArrayList<Card>());
            }
            suits.get(card.getSuit()).add(card);
        }

        for (Card.Suit suit : suits.keySet()) {
            if (suits.get(suit).size() >= 3) {
                List<Card> cards = suits.get(suit);
                try {
                    Collections.sort(cards);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < cards.size() - 2; i++) {
                    for (int j = i + 2; j < cards.size(); j++) {
                        ICardSet aSet = new CardSet(cards.subList(i, j + 1));
                        if (aSet.isRun()) {
                            potentialSeries.add(cards.subList(i, j + 1));
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return potentialSeries;
    }

    public JSONArray getMatchedArray() {
        return MatchedCardsArray;
    }

    public JSONArray getUnMatchedArray() {
        return UnMatchedCardsArray;
    }

    public String toString() {
        String s = "";
        for (Card c : aCards) {
            s += (c + " ");
        }
        return s;
    }

    public Card getCard(Item_Card mCard) {
        return new Card(getRank(mCard), getSuit(mCard));
    }

    public Rank getRank(Item_Card mCard) {
        try {
            return Rank.values()[mCard.getCardValue() - 1];
        } catch (Exception e) {
            e.printStackTrace();
            return Rank.values()[0];
        }
    }

    public Suit getSuit(Item_Card mCard) {
        switch (mCard.getCardColor()) {
            case 0:
                return Suit.HEARTS;
            case 1:
                return Suit.SPADES;
            case 2:
                return Suit.DIAMONDS;
            case 3:
                return Suit.CLUBS;
        }
        return Suit.CLUBS;
    }

    public String getCardColor(Card mCard) {
        switch (mCard.getSuit()) {
            case CLUBS:
                return "f";
            case DIAMONDS:
                return "c";
            case HEARTS:
                return "l";
            case SPADES:
                return "k";
        }
        return "";
    }

}
