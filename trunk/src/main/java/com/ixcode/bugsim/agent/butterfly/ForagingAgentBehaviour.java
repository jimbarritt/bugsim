/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.framework.simulation.model.agent.physical.AgentBehaviour;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ForagingAgentBehaviour extends AgentBehaviour {

    public static final ForagingAgentBehaviour SEARCHING = new ForagingAgentBehaviour("Searching");
    public static final ForagingAgentBehaviour SEARCHING_ENTER_PATCH = new ForagingAgentBehaviour("Searching.EnterPatch");
    public static final ForagingAgentBehaviour SEARCHING_IN_PATCH = new ForagingAgentBehaviour("Searching.InPatch");
    public static final ForagingAgentBehaviour SEARCHING_EXIT_PATCH = new ForagingAgentBehaviour("Searching.ExitPatch");
    public static final ForagingAgentBehaviour OVIPOSITING = new ForagingAgentBehaviour("Ovipositing");
    public static final ForagingAgentBehaviour BIRTH = new ForagingAgentBehaviour("Birth");
    public static final ForagingAgentBehaviour RELEASE = new ForagingAgentBehaviour("Released");
    public static final ForagingAgentBehaviour ESCAPED = new ForagingAgentBehaviour("Escaped");
    public static final ForagingAgentBehaviour DEAD = new ForagingAgentBehaviour("Dead");

    private ForagingAgentBehaviour(String name) {
        super(name);
    }



}
