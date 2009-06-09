/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CardLayoutPanel extends JPanel {

    public CardLayoutPanel() {
        super(new CardLayout());
        _cardLayout = (CardLayout)super.getLayout();
    }

    public void addCard(JComponent component, String name) {
        super.add(component, name);
        _cardMap.put(name, component);
        _cardListNames.add(name);
        _cards.add(component);
    }

    public void add(JComponent comp, Object constraints) {
        addCard(comp, (String)constraints);
    }


    public CardLayout getCardLayout() {
        return _cardLayout;
    }

    public JComponent getCard(String name) {
        if (!_cardMap.containsKey(name)) {
            throw new IllegalArgumentException("No Card for name: " +name + " : names=" + _cardListNames);
        }
        return (JComponent)_cardMap.get(name);
    }

    public void showCard(String name) {
        _cardLayout.show(this, name);
    }

    /**
     * Return all the cards EXCEPT this one....
     * @param excludeName
     * @return
     */
    public List getOtherCards(String excludeName) {
        List cards = new ArrayList();
        for (Iterator itr = _cardListNames.iterator(); itr.hasNext();) {
            String cardName = (String)itr.next();
            if (!cardName.equals(excludeName))  {
                cards.add(getCard(cardName));
            }
        }
        return cards;
    }

    public List getAllCards() {
        return _cards;
    }


    private CardLayout _cardLayout;
    private Map _cardMap = new HashMap();
    private List _cardListNames = new ArrayList();
    private List _cards = new ArrayList();
}
