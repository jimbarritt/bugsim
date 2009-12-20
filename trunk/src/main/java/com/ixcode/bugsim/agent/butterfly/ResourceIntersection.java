/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly;

import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.agent.resource.IResourceAgent;

import java.util.List;
import java.util.Iterator;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Apr 25, 2007 @ 10:18:18 AM by jim
 */
public class ResourceIntersection {

    public ResourceIntersection(List ixPoints, IResourceAgent resource) {
        _ixPoints = ixPoints;
        _resource = resource;
    }

    public List getIxPoints() {
        return _ixPoints;
    }

    public IResourceAgent getResource() {
        return _resource;
    }

    public double shortestDistanceTo(RectangularCoordinate point) {
        double shortestDistance = Double.MAX_VALUE;

        for (Iterator itr = _ixPoints.iterator(); itr.hasNext();) {
            RectangularCoordinate ix = (RectangularCoordinate)itr.next();
            double d = point.calculateDistanceTo(ix);
            if (d < shortestDistance) {
                shortestDistance = d;
            }
        }

        return shortestDistance;
    }

    private List _ixPoints;
    private IResourceAgent _resource;

}
