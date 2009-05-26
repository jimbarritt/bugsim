/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import junit.framework.TestCase;

/**
 * TestCase for class : ParameterMap
 * Created     : Feb 3, 2007 @ 1:02:01 PM by jim
 */
public class ParameterMapTestCase extends TestCase {

    public void testWildCardSearch() {
        ParameterMap params = new ParameterMap();


        Category topC = new Category(C_TOP);

        StrategyDefinitionParameter secondLevelS = new StrategyDefinitionParameter(S_SECOND_LEVEL, String.class.getName());
        Parameter secondLevelChildP = new Parameter(P_SECOND_LEVEL_CHILD, "DUST!!");
        secondLevelS.addParameter(secondLevelChildP);

        Parameter secondLevelContainer = new Parameter(P_SECOND_LEVEL_CONTAINER, secondLevelS);

        StrategyDefinitionParameter strategyS = new StrategyDefinitionParameter(S_STRATEGY, String.class.getName());
        Parameter strategyChildP = new Parameter(P_STRATEGY_CHILD, "GOLD!!");

        strategyS.addParameter(strategyChildP);
        strategyS.addParameter(secondLevelContainer);

        Parameter strategyContainerP = new Parameter(P_STRATEGY_CONTAINER, strategyS);

        topC.addParameter(strategyContainerP);

        params.addCategory(topC);
                                             

        String expectedWildCardName = C_TOP + "." + P_STRATEGY_CONTAINER + ".*." + P_STRATEGY_CHILD;
        Parameter found = params.findParameter(expectedWildCardName);
        assertNotNull(found);
        assertEquals("GOLD!!", (String)found.getValue());

        String wildCardName = strategyChildP.getWildCardName();
        assertEquals(expectedWildCardName, wildCardName);

        Parameter foundWC = params.findParameter(expectedWildCardName);
        assertNotNull(foundWC);
        assertEquals("GOLD!!", (String)foundWC.getValue());


        expectedWildCardName = C_TOP + "." + P_STRATEGY_CONTAINER + ".*." + P_SECOND_LEVEL_CONTAINER + ".*." + P_SECOND_LEVEL_CHILD;
        found = params.findParameter(expectedWildCardName);
        assertNotNull(found);
        assertEquals("DUST!!", (String)found.getValue());

        wildCardName = secondLevelChildP.getWildCardName();
        assertEquals(expectedWildCardName, wildCardName);

        foundWC = params.findParameter(expectedWildCardName);
        assertNotNull(foundWC);
        assertEquals("DUST!!", (String)foundWC.getValue());


    }

    private static final String C_TOP = "topCategory";
    private static final String S_STRATEGY = "strategy";

    private static final String P_STRATEGY_CHILD = "strategyChildP";
    private static final String P_STRATEGY_CONTAINER = "strategyContainerP";
    private static final String S_SECOND_LEVEL = "strategySecondLevel";
    private static final String P_SECOND_LEVEL_CHILD = "secondLevelChild";
    private static final String P_SECOND_LEVEL_CONTAINER = "secondLevelContainerP";
}
