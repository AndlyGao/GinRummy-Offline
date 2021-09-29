package com.artoon.sort;

import com.artoon.sort.Card.Rank;
import com.artoon.sort.Card.Suit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * ICardSet implementation.
 */
public class CardSet implements ICardSet, Serializable {
    private Set<Card> aSet = new HashSet<Card>();

    /**
     * Constructor with set.
     *
     * @param pSet Set to initialize the CardSet with
     */
    public CardSet(Set<Card> pSet) {
        if (pSet != null && !pSet.contains(null)) {
            this.aSet = new HashSet<Card>(Collections.unmodifiableSet(pSet));
        }
    }

    /**
     * Constructor with list.
     *
     * @param pSet Set to initialize the CardSet with
     */
    public CardSet(List<Card> pSet) {
        if (pSet != null && !pSet.contains(null)) {
            this.aSet = new HashSet<Card>(Collections.unmodifiableList(pSet));
        }
    }

    @Override
    public Iterator<Card> iterator() {
        return aSet.iterator();
    }

    @Override
    public boolean contains(Card pCard) {
        Iterator<Card> iterator = aSet.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(pCard)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return aSet.size();
    }

    @Override
    public boolean isGroup() {
        if (size() < 3) {
            return false;
        }
        Iterator<Card> iterator = aSet.iterator();
        Rank ordinal = iterator.next().getRank();

        while (iterator.hasNext()) {
            if (ordinal != iterator.next().getRank()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isRun() {
        if (size() < 3) {
            return false;
        }
        List<Card> list = new ArrayList<Card>(aSet);
        try {
            Collections.sort(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator<Card> iterator = list.iterator();

        int lastOrdinal = -1;
        Suit suit = null;
        while (iterator.hasNext()) {
            Card nextCard = iterator.next();
            if (suit == null) {
                suit = nextCard.getSuit();
                lastOrdinal = nextCard.getRank().ordinal();
                continue;
            }
            int nextOrdinal = nextCard.getRank().ordinal();
            if (suit != nextCard.getSuit() || lastOrdinal + 1 != nextOrdinal) {
                return false;
            }

            lastOrdinal = nextOrdinal;
        }

        return true;
    }

}
