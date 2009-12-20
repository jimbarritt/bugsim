/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.output;

import com.ixcode.framework.simulation.model.Simulation;

import java.io.*;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SimulationSerialiser {

    public void saveSimulation(Simulation sim, File file) throws IOException {
        if (file.exists()) {
            throw new IOException("File :" + file.getAbsolutePath() + " already exists!");
        }

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(sim.getLiveAgents());
        out.writeObject(sim.getLandscape().getGrids());

        out.close();

    }

    public void loadSimulation(Simulation simulation, File file) throws IOException, ClassNotFoundException {

        if (!file.exists()) {
            throw new IOException("File : " + file + " does not exists!");
        }

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        List agents = (List)in.readObject();
        List grids = (List)in.readObject();
        simulation.clean();
        simulation.addAgents(agents);
        
    }
}
