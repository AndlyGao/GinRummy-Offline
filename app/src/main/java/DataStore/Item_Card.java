package DataStore;

public class Item_Card implements Comparable<Item_Card>, Cloneable {


    int CardValue, CardColor, cardinpair, cainpaire2;

    int PairNumber;
    boolean ischeck, isValidGroup;
    String CardColorStr;
    int GroupNumber;

    public int getCardValue() {

        return CardValue;

    }

    public void setCardValue(int cardValue) {
        CardValue = cardValue;
    }


    public int getCardColor() {
        return CardColor;
    }

    public void setCardColor(int cardColor) {
        CardColor = cardColor;
    }

    public String getCardColorString() {
        if (CardColor == 0) {
            CardColorStr = "l";
        } else if (CardColor == 1) {
            CardColorStr = "k";
        } else if (CardColor == 2) {
            CardColorStr = "c";
        } else if (CardColor == 3) {
            CardColorStr = "f";
        }
        return CardColorStr;
    }

    public int getGroupNumber() {
        return GroupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        GroupNumber = groupNumber;
    }

    public boolean getIsValidGroup() {
        return isValidGroup;
    }

    public void setIsValidGroup(boolean b) {
        this.isValidGroup = b;
    }

    public boolean getCardInPair() {
        return ischeck;
    }

    public void setCardInPair(boolean b) {
        // TODO Auto-generated method stub
        ischeck = b;
    }

    public int getPairNumber() {
        return PairNumber;
    }


    public void setPairNumber(int pairNumber) {
        PairNumber = pairNumber;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub

        if (CardColor == 0) {
            CardColorStr = "l";
        } else if (CardColor == 1) {
            CardColorStr = "k";
        } else if (CardColor == 2) {
            CardColorStr = "c";
        } else if (CardColor == 3) {
            CardColorStr = "f";
        }
        return CardColorStr + "-" + CardValue + " " + isValidGroup + " " + GroupNumber + " " + ischeck;
    }

    @Override
    public int compareTo(Item_Card another) {
        // TODO Auto-generated method stub

        try {
            int compareResult = Integer.compare(this.CardColor, another.CardColor);
            if (compareResult == 0) {
                compareResult = Integer.compare(this.CardValue, another.CardValue);
            }
            return compareResult;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }

    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        Item_Card iCard = (Item_Card) o;

        return iCard.CardColor == this.CardColor && iCard.CardValue == this.CardValue;
    }

    @Override
    public Item_Card clone() {
        Item_Card clone = null;
        try {
            clone = (Item_Card) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
            // won't happen
        }
        return clone;
    }
}
