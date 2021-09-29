package com.artoon.sort;

import java.io.Serializable;

/**
 * An immutable description of a playing card.
 */
public final class Card implements Comparable<Card>, Serializable {
    private final Rank aRank;
    private final Suit aSuit;

    /**
     * Create a new card object.
     *
     * @param pRank The rank of the card.
     * @param pSuit The suit of the card.
     */
    public Card(Rank pRank, Suit pSuit) {
        aRank = pRank;
        aSuit = pSuit;
    }

    /**
     * Obtain the rank of the card.
     *
     * @return An object representing the rank of the card.
     */
    public Rank getRank() {
        return aRank;
    }

    /**
     * Obtain the suit of the card.
     *
     * @return An object representing the suit of the card
     */
    public Suit getSuit() {
        return aSuit;
    }

    /**
     * @return See above.
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return aRank + " of " + aSuit;
    }

    /**
     * Compares two cards according to gin rules (ace is low, suits run as
     * Spade, Hearts, Diamonds, Clubs (high to low)).
     *
     * @param pCard The card to compare to
     * @return Returns a negative integer, zero, or a positive integer as this
     * object is less than, equal to, or greater than pCard
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Card pCard) {

        try {
            int compareResult = Integer.compare(this.aRank.ordinal(), pCard.aRank.ordinal());
            if (compareResult == 0) {
                compareResult = Integer.compare(this.aSuit.ordinal(), pCard.aSuit.ordinal());
            }
            return compareResult;
            /*if (pCard.aRank.ordinal() > this.aRank.ordinal()) {
                return -1;
            } else if (pCard.aRank.ordinal() < this.aRank.ordinal()) {
                return 1;
            } else {
                if (pCard.aSuit.ordinal() > this.aSuit.ordinal()) {
                    return -1;
                } else if (pCard.aSuit.ordinal() < this.aSuit.ordinal()) {
                    return 1;
                } else {
                    return 0;
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Two cards are equal if they have the same suit and rank.
     *
     * @param pCard The card to test.
     * @return true if the two cards are equal
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object pCard) {
        if (pCard instanceof Card) {
            Card other = (Card) pCard;
            try {
                return this.aRank == other.aRank && this.aSuit == other.aSuit;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * The hashcode for a card is the suit*13 + that of the rank (perfect hash).
     *
     * @return the hashcode
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.aSuit.ordinal() * Rank.values().length
                + this.aRank.ordinal();
    }

    /**
     * Represents the rank of the card.
     */
    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    /**
     * Represents the suit of the card.
     */
    public enum Suit {
        HEARTS, SPADES, DIAMONDS, CLUBS
    }
}
