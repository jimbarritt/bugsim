/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.agent;

import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.model.agent.butterfly.IOlfactionStrategy;
import com.ixcode.bugsim.model.agent.butterfly.SignalSensorOlfactoryStrategy;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.IOlfactoryAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.SignalSensor;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.SensoryRandomWalkStrategy;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;


/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyAgentRenderer extends MotileAgentRenderer {

    protected void drawBody(double x, double r, double y, Graphics2D g, IMotileAgent bug, RenderContext renderContext) {
        super.drawBody(x, r, y, g, bug, renderContext);
        ButterflyAgent butterfly = (ButterflyAgent)bug;

        boolean drawSensors = true;
        IMovementStrategy movement = butterfly.getMovementStrategy();
        if (movement instanceof SensoryRandomWalkStrategy) {
            SensoryRandomWalkStrategy srws = (SensoryRandomWalkStrategy)movement;
            drawSensors = srws.isOlfactionEnabled();
        }

        if (drawSensors && bug instanceof IOlfactoryAgent) {
            IOlfactoryAgent olfactoryA = (IOlfactoryAgent)bug;
                IOlfactionStrategy olfactionS = olfactoryA.getOlfactionStrategy();
                if (olfactionS instanceof SignalSensorOlfactoryStrategy) {
                    SignalSensorOlfactoryStrategy sso = (SignalSensorOlfactoryStrategy)olfactionS;
                    drawSensors(sso.getSensors(), butterfly, g, x, y, renderContext);
                }
        }


    }


    private void drawSensors(List sensors, ButterflyAgent agent, Graphics2D g, double x, double y, RenderContext renderContext) {
        double agentHeading = 180 - agent.getAzimuth();
        for (Iterator itr = sensors.iterator(); itr.hasNext();) {
            SignalSensor sensor = (SignalSensor)itr.next();
            double h = sensor.getHeadingFromAgent();
            double sensorHeading = AzimuthCoordinate.applyAzimuthChange(h, agentHeading);
            Point2D sensorPoint = super.calculatePointAtTheta(sensorHeading, sensor.getDistanceFromAgent(), g, x, y);
            super.drawFilledCircle(g, sensorPoint, getSensorColor(renderContext), 0.5);
        }
    }

    private Color getSensorColor(RenderContext renderContext) {
        return renderContext.isRenderForPrint() ? Color.black : Color.cyan;
    }


}
