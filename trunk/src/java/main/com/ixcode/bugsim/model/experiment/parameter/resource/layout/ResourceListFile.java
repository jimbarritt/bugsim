/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.resource.layout;


import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 8, 2007 @ 11:20:05 AM by jim
 */
public class ResourceListFile extends File {

    public ResourceListFile(String pathname, String preview) {
        super(pathname);
        _preview = preview;
    }

    public ResourceListFile(String parent, String child, String preview) {
        super(parent, child);
        _preview = preview;
    }

    public ResourceListFile(File parent, String child, String preview) {
        super(parent, child);
        _preview = preview;
    }

    public ResourceListFile(URI uri, String preview) {
        super(uri);
        _preview = preview;
    }

    public String getPreview() {
        return _preview;
    }

    public static List readResourceListFiles(File file) {
        File[] files = file.listFiles(RESOURCE_FILE_FILTER);
        List resourceListFiles = new ArrayList();
        if (files != null) {
            for (int i=0;i<files.length;++i) {
                String preview = readPreview(files[i]);
                resourceListFiles.add(new ResourceListFile(files[i].getAbsolutePath(), preview));
            }
        }
        return resourceListFiles;
    }

    private static String readPreview(File file) {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(file));

            in.readLine(); // assume firs row has column names - maybe parameterise this...
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String _preview;


    private static final FileFilter RESOURCE_FILE_FILTER = new FileFilter() {
            public boolean accept(File f) {
                return f.getName().endsWith(".csv");
            }

            public String getDescription() {
                return "Resource List (*.csv)";
            }
        };

}
