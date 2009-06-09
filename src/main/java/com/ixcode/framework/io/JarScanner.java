/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io;

import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.jar.JarInputStream;
import java.util.jar.JarEntry;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 13-Dec-2004
 * Time: 15:10:01
 * To change this template use File | Settings | File Templates.
 */
public class JarScanner {
    private JFrame _frame;
    private JLabel _label;

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                System.out.println("[JarScanner] Usage : JarScanner rootPath searchClassName");
                System.exit(-1);
            }
            String rootPath = args[0];
            String searchClassName = args[1].replace('.', '/') + ".class";
            JarScanner scanner = new JarScanner();
            scanner.executeSearch(new File(rootPath), searchClassName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public JarScanner() {
    }

    public void executeSearch(File root, String searchClassName) throws IOException {
        _frame = new JFrame();
        _label = new JLabel();
        _frame.getContentPane().add(_label);
        _frame.setSize(300, 100);
        _frame.pack();
        _frame.show();

        System.out.println("Searching jar files under " + root.getAbsolutePath() + " for class " + searchClassName + "...");

        List files = findJarFilesRecursively(root);
        List results = new ArrayList();
        for (Iterator itr = files.iterator(); itr.hasNext();) {
            File file = (File)itr.next();
            if (jarContainsSearchClass(file, searchClassName)) {
                results.add(file);
            }
        }

        System.out.println("Results:");
        System.out.println("--------------------------------------------------------------");
        for (Iterator itr = results.iterator(); itr.hasNext();) {
            File jarFile = (File)itr.next();
            System.out.println(jarFile.getAbsolutePath());
        }
        _frame.dispose();
    }

    private List findJarFilesRecursively(File searchDir) {
//        System.out.println("Searching " + searchDir.getAbsolutePath());
        _label.setText("Searching " + searchDir.getAbsolutePath());
        FileFilter dirFilter = new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        };
        FilenameFilter jarFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        };

        List results = new ArrayList();

        File[] jarFiles = searchDir.listFiles(jarFilter);
        if (jarFiles != null && jarFiles.length > 0) {
            results.addAll(Arrays.asList(jarFiles));
        }

        File[] subDirs = searchDir.listFiles(dirFilter);
        if ((subDirs != null) && (subDirs.length > 0)) {
            for (int i = 0; i < subDirs.length; ++i) {
                results.addAll(findJarFilesRecursively(subDirs[i]));
            }
        }

        return results;
    }

    private boolean jarContainsSearchClass(File jarFile, String searchClassName) throws IOException {
        JarInputStream jis = null;
        boolean containsClass = false;
        try {
            jis = new JarInputStream(new FileInputStream(jarFile));
            JarEntry entry = jis.getNextJarEntry();
            while (entry != null) {
                if (searchClassName.endsWith(entry.getName())) {
                    containsClass = true;
                    debugSerialVer(jarFile, searchClassName);
                    break;
                }
                entry = jis.getNextJarEntry();
            }

            return containsClass;
        } catch (IOException e) {
            System.out.println("Caught IOException processing jar file " + jarFile.getAbsolutePath() + " : " + e.getClass().getName() + ", " + e.getMessage());
            return false;
        } finally {
            if (jis != null) jis.close();
        }

    }

    private void debugSerialVer(File jarFile, String searchClassName) throws IOException {
        String searchClassNameToLoad = searchClassName.replace('/', '.').substring(0, searchClassName.length() - 6);
        try {
            URL[] urls = new URL[]{new URL("file", "localhost", jarFile.getCanonicalPath())};
            URLClassLoader cl = new URLClassLoader(urls);

            Class searchClass = cl.loadClass(searchClassNameToLoad);

            Long id = JarScanner.getDeclaredSUID(searchClass);
            System.out.println("serialVersionUID for class " + searchClass.getName() + " in " + jarFile.getAbsolutePath() + " is : ");

            CommandExec.execute("serialver -classpath " + jarFile + " " + searchClassNameToLoad);


//            ObjectStreamClass osc = new ObjectStreamClass.lookup();
//
//            System.out.println("SerialVer of class " + searchClass + " in " + jarFile.getAbsolutePath() + " is " + osc.getSerialVersionUID());
        } catch (ClassNotFoundException e) {
            throw new IOException("Could not find class " + searchClassNameToLoad + " in jarfile " + jarFile.getAbsolutePath());
        }
    }

    private static Long getDeclaredSUID(Class cl) {
        try {
            Field f = cl.getDeclaredField("serialVersionUID");
            int mask = Modifier.STATIC | Modifier.FINAL;
            if ((f.getModifiers() & mask) == mask) {
                f.setAccessible(true);
                return new Long(f.getLong(null));
            }
        } catch (Exception ex) {
        }
        return null;
    }


}
