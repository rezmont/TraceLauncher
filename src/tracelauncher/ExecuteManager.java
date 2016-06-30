/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tracelauncher;

import automation.Launcher;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author motamedi
 */
public class ExecuteManager implements Runnable {

    boolean run = true;
    String classNameToBeLoaded;
    String folder;
    ArrayList<String> measurements;

    public ExecuteManager(String classNameToBeLoaded, String folder, ArrayList<String> m) {
        this.classNameToBeLoaded = classNameToBeLoaded;
        this.folder = folder;
        this.measurements = new ArrayList<>(m);
    }

    @Override
    public void run() {
        Collections.shuffle(measurements, new Random());
        for (String line : measurements) {
            System.out.println(line);
            System.out.println(classNameToBeLoaded);

            if (run) {
                try {
                    ClassLoader classLoader = ClassLoader.getSystemClassLoader();

                    Class aClass = classLoader.loadClass(classNameToBeLoaded);
                    Object instance = aClass.newInstance();

                    Method methodConfig = aClass.getDeclaredMethod("config", new Class[]{String.class, String.class});
                    methodConfig.invoke(instance, new Object[]{line, folder});

                    Method methodLaunch = aClass.getDeclaredMethod("launch", new Class[]{});
                    String returnValue = (String) methodLaunch.invoke(instance, new Object[]{});

                    System.out.println(returnValue);

                    try {
                        int randomNum = 10 + (int) (Math.random() * 50);
                        System.out.println("Waiting for " + randomNum + " seconds");
                        TimeUnit.SECONDS.sleep(randomNum);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(TraceLauncher.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(TraceLauncher.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(TraceLauncher.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(TraceLauncher.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(TraceLauncher.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(TraceLauncher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
