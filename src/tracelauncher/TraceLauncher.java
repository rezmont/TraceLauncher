/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tracelauncher;

import automation.*;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author motamedi
 */
public class TraceLauncher {

    boolean makeFolder = true;
    final Map<String, String> launcherPointer;

    public TraceLauncher() {
        this.launcherPointer = new HashMap<>();
        launcherPointer.put("net_he", "automation.HE");
        launcherPointer.put("net_level3", "automation.Level3");
        launcherPointer.put("net_sprint", "automation.Sprint");
        launcherPointer.put("net_nlayer", "automation.NLayer");
        launcherPointer.put("net_fdc", "automation.FDC");
        launcherPointer.put("net_trit", "automation.Trit");
        launcherPointer.put("net_iptp", "automation.IPTP");
        launcherPointer.put("net_ntt", "automation.NTT");
        launcherPointer.put("net_asdasd", "automation.ASDASD");
        launcherPointer.put("net_clearfly", "automation.Clearfly");
        launcherPointer.put("net_appliedops", "automation.AOps");
        launcherPointer.put("net_atlmetro", "automation.AtlMetro");
        launcherPointer.put("net_telia", "automation.Telia");
        launcherPointer.put("edu_aarnet", "automation.AARNet");
        launcherPointer.put("net_tac", "automation.Telus");
        launcherPointer.put("net_tata", "automation.Tata");
        launcherPointer.put("com_pipe", "automation.PIPE");
        launcherPointer.put("net_velia", "automation.Velia");
        launcherPointer.put("com_phyber", "automation.Phyber");
        launcherPointer.put("net_eurorings", "automation.KPN");
        launcherPointer.put("net_bboi", "automation.BBOI");
        launcherPointer.put("net_hwng", "automation.Highwinds");
        launcherPointer.put("net_totiig", "automation.TOTIIG");
        launcherPointer.put("com_virtutel", "automation.VirtuTel");
        launcherPointer.put("com_hostvirtual", "automation.HostVirtual");
        launcherPointer.put("com_towardex", "automation.Towardex");
        launcherPointer.put("net_integra", "automation.Integra");
        launcherPointer.put("net_nexicom", "automation.Nexicom");
        launcherPointer.put("net_infowest", "automation.InfoWest");
        launcherPointer.put("com_webex", "automation.Webex");
        launcherPointer.put("net_iinet", "automation.iiNet");
        launcherPointer.put("net_emix", "automation.Emix");
        launcherPointer.put("net_arpnetworks", "automation.OpenBGPD");
        launcherPointer.put("net_peer1", "automation.Peer1");
        launcherPointer.put("com_vocus", "automation.Vocus");
        launcherPointer.put("co_vibe", "automation.Vibe");
        launcherPointer.put("com_flagtel", "automation.Flagtel");
        launcherPointer.put("mn_gemnet", "automation.Gemnet");
        launcherPointer.put("net_retn", "automation.ReTN");
        launcherPointer.put("gs_sg", "automation.SGGS");
        launcherPointer.put("net_init7", "automation.Init7");
        launcherPointer.put("net_savvis", "automation.Savvis");
        launcherPointer.put("net_i3d", "automation.I3D");
        launcherPointer.put("com_telx", "automation.TelX");
        launcherPointer.put("com_conit", "automation.ConIT");
        launcherPointer.put("net_inerail", "automation.Inerail");
        launcherPointer.put("com_core", "automation.CoreB");
        launcherPointer.put("net_vpls", "automation.VPLS");
        launcherPointer.put("net_pch", "automation.PCH");
        launcherPointer.put("edu_usc", "automation.USC");
        launcherPointer.put("edu_princeton", "automation.Princeton");
        launcherPointer.put("it_garr", "automation.GARR");
        launcherPointer.put("net_hlkomm", "automation.HLKomm");
        launcherPointer.put("com_cogent", "automation.Cogent");
        launcherPointer.put("no_uninett", "automation.UNINETT");
        launcherPointer.put("net_ilan", "automation.ILAN");
        launcherPointer.put("ca_eastlink", "automation.Eastlink");
        launcherPointer.put("de_belwue", "automation.BelWue");
        launcherPointer.put("ch_switch", "automation.SWITCHlan");
        launcherPointer.put("net_bell", "automation.Bell");
        launcherPointer.put("net_silesnet", "automation.SilesNet");
        launcherPointer.put("es_rediris", "automation.RedIRIS");
        launcherPointer.put("net_ja", "automation.JANET");
        launcherPointer.put("co_simbanet", "automation.SimbaNET");
        launcherPointer.put("ch_solnet", "automation.SolNet");
        launcherPointer.put("net_iprimus", "automation.IPrimus");
        launcherPointer.put("net_noris", "automation.Noris");
        launcherPointer.put("ie_heanet", "automation.HEAnet");
        launcherPointer.put("net_telstra", "automation.Telstra");
        launcherPointer.put("se_sunet", "automation.SUNET");
        launcherPointer.put("ch_green", "automation.Green");
        launcherPointer.put("br_rnp", "automation.RNP");
        launcherPointer.put("pt_fccn", "automation.FCCN");
        launcherPointer.put("hu_hbone", "automation.HBONE");
        launcherPointer.put("sk_six", "automation.SIX");
        launcherPointer.put("com_connect", "automation.AAPT");
        launcherPointer.put("com_xo", "automation.XO");
        launcherPointer.put("ru_gin", "automation.Gin");
        launcherPointer.put("net_gldn", "automation.GLDN");
        launcherPointer.put("net_uar", "automation.UARNet");
        launcherPointer.put("ie_magnet", "automation.MagNet");
        launcherPointer.put("ru_rusnet", "automation.RUSnet");
        launcherPointer.put("net_global", "automation.GlobalCrossing");
        launcherPointer.put("edu_stanford", "automation.Stanford");
        launcherPointer.put("net_semaphore", "automation.Semaphore");
        launcherPointer.put("net_supranet", "automation.Supranet");
        launcherPointer.put("net_twtelecom", "automation.TWTelecom");
        launcherPointer.put("com_zayo", "automation.Zayo");

        /* 15-08-29 */
        launcherPointer.put("com_fluidhosting", "automation.FluidHosting");
        launcherPointer.put("com_centurylink", "automation.CenturyLink");

        /* 15-08-31 */
        launcherPointer.put("eu_as8218", "automation.AS8218");
        launcherPointer.put("net_atrato", "automation.Atrato");
        launcherPointer.put("net_bt", "automation.BT");
        launcherPointer.put("com_hgc", "automation.HGC");
        launcherPointer.put("com_liquidweb", "automation.LiquidWeb");
        launcherPointer.put("net_steadfast", "automation.Steadfast");
        launcherPointer.put("com_hgcintl", "automation.HGCIntl");
        launcherPointer.put("org_fifi", "automation.Fifi");
        launcherPointer.put("net_nordu", "automation.Nordu");
        launcherPointer.put("net_seabone", "automation.Seabone");

        /* 15-09-11 */
        launcherPointer.put("net_forethought", "automation.ForeThought");
        launcherPointer.put("net_docler", "automation.DoclerWeb");
        launcherPointer.put("net_interworld", "automation.Interworld");

        /* 15-09-29 */
        launcherPointer.put("net_onvoy", "automation.Zayo");
        launcherPointer.put("net_netins", "automation.INS");
        launcherPointer.put("net_linx", "automation.LINX");
        launcherPointer.put("ru_mipt", "automation.Mipt");
        launcherPointer.put("net_olsson", "automation.Olsson");
        launcherPointer.put("net_space", "automation.Space");
        launcherPointer.put("pl_tpnet", "automation.TPNET");
        launcherPointer.put("com_xmission", "automation.XMission");
        launcherPointer.put("com_opus1", "automation.Opus1");
        launcherPointer.put("net_dts", "automation.DTS");
        launcherPointer.put("bg_acad", "automation.BREN");
        launcherPointer.put("net_ints", "automation.INTS");
        launcherPointer.put("net_nac", "automation.NAC");
        launcherPointer.put("net_evolink", "automation.EvoLink");
        launcherPointer.put("eu_ecritel", "automation.Ecritel");
        launcherPointer.put("net_lonap", "automation.LONAP");
        launcherPointer.put("ru_mtu", "automation.MTC");
        launcherPointer.put("de_manda", "automation.MANDA");
        launcherPointer.put("net_corbina", "automation.Corbina");
        launcherPointer.put("de_netcologne", "automation.NetCologne");
        launcherPointer.put("net_clara", "automation.Clara");
        launcherPointer.put("ru_df", "automation.DF");
        launcherPointer.put("de_kamp", "automation.KAMP");
        launcherPointer.put("net_as8681", "automation.JT");
        launcherPointer.put("ro_zyx", "automation.ZYX");
        launcherPointer.put("ru_comcor", "automation.Comcor");
        launcherPointer.put("ru_start", "automation.Start");
        launcherPointer.put("com_pccwglobal", "automation.PCCWGlobalcom");
        launcherPointer.put("com_hostnetworks", "automation.HostNetworks");
        launcherPointer.put("net_tellurian", "automation.Tellurian");
        launcherPointer.put("com_linxtelecom", "automation.LinxTelecom");
        launcherPointer.put("ro_euroweb", "automation.EuroWeb");
        launcherPointer.put("net_psychz", "automation.Psychz");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//		String line = "ro_euroweb	6663	Bucuresti (AS6663) - BGP International View	Bucharest_2	Bucharest, Romania	154.54.41.37	174";
//		String folder = "test";

//		HE z = new HE();
//		VPLS z = new VPLS();
//		String line = "net_vpls	35908	lax	Los_Angeles_CA_US	Los Angeles CA US	173.1.191.1	45474";
//		DoclerWeb z = new DoclerWeb();
//		String line = "net_docler	34655	80.77.112.247	Budapest_HU	Budapest HU	208.79.72.1	36169";
//		String line = "org_fifi	46375	none	none	Mountain View CA US	124.6.40.1	24482";
//		Fifi z = new Fifi();
//		z.config(line, folder);
//		z.launch();
//		EuroWeb z = new EuroWeb();
//		z.config(line, folder);
//		z.launch();
//		System.exit(-1);
        String fileName = null;
        if (args.length > 0) {
            fileName = args[0];
        } else {
            fileName = "atmp1_100050_coresite_LA1_mission.txt.psychz";
        }
        TraceLauncher tl = new TraceLauncher();
        tl.crawl(fileName);
    }

    private void crawl(String fileName) {
        /*  */
        
        boolean run = false;

        String filePath = "/home/motamedi/Dropbox/workspace/NetBeansProjects/Campaign/data/missions";
        String folderName = "/home/motamedi/Dropbox/workspace/NetBeansProjects/TraceLauncher/targetted_probing";
        String outFolder = (new File(new File(folderName), fileName)).toString();
        System.out.println(outFolder);

        HashMap<String, HashSet> completed = new HashMap<String, HashSet>();
        File folder = new File(outFolder);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    String line = null;
                    try {
                        String key = file.getName().split("-")[0];
                        if (!completed.containsKey(key)) {
                            completed.put(key, new HashSet());
                        }

                        BufferedReader br = new BufferedReader(new FileReader(file));

                        String preamble = "********************* >>>> ";
                        while ((line = br.readLine()) != null) {
                            if (line.startsWith(preamble)) {
                                String lineAux = line.replace("********************* >>>> ", "").replace(" <<<<", "");
                                completed.get(key).add(lineAux);
                            }
                        }
                    } catch (IOException ex) {
                        System.err.println(line);
                        Logger.getLogger(TraceLauncher.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        System.err.println(line);
                        Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, e);
                        System.exit(-1);
                    }
                }
            }
        } else if (makeFolder) {
            File outputDir = new File(outFolder);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
        } else {	//the path refers to a file name
            System.err.println("Outfolder is not created!!!");
            System.exit(2);
        }

        System.out.println("Completed from " + completed.size() + " Websites");
        for (Map.Entry<String, HashSet> entrySet : completed.entrySet()) {
            String key = entrySet.getKey();
            HashSet value = entrySet.getValue();
            System.out.println(key + " " + value.size());
        }

        HashMap<String, ArrayList<String>> campMap = new HashMap<String, ArrayList<String>>();
        File campaingFile = new File(new File(filePath), fileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(campaingFile));
            String line = null;
            while ((line = reader.readLine()) != null) {

                String id = line.split("\t")[0];
                if (completed.containsKey(id)) {
                    if (completed.get(id).contains(line)) {
                        continue;
                    }
                }

                String classNameToBeLoaded = launcherPointer.get(id);
                if (!campMap.containsKey(classNameToBeLoaded)) {
                    campMap.put(classNameToBeLoaded, new ArrayList<String>());
                }

                campMap.get(classNameToBeLoaded).add(line);

                if (run) {
                    try {
                        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

                        Class aClass = classLoader.loadClass(classNameToBeLoaded);
                        Object instance = aClass.newInstance();

                        Method methodConfig = aClass.getDeclaredMethod("config", new Class[]{String.class, String.class});
                        methodConfig.invoke(instance, new Object[]{line, folderName});

                        Method methodLaunch = aClass.getDeclaredMethod("launch", new Class[]{});
                        String returnValue = (String) methodLaunch.invoke(instance, new Object[]{});

                        System.out.println(returnValue);

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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TraceLauncher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TraceLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        int n = 1;
        String special = "automation.Eastlink";
        if (campMap.containsKey(special) && campMap.get(special).size() > 5) {
            n = 5;
        }
        HashMap<String, ExecutorService> ExecutorMap = new HashMap<String, ExecutorService>();
        for (Map.Entry<String, ArrayList<String>> entrySet : campMap.entrySet()) {
            String classToBeLoaded = entrySet.getKey();
            if (classToBeLoaded.equalsIgnoreCase(special) && n > 1) {
                for (int i = 0; i < n; i++) {
                    String key = classToBeLoaded + i;
                    System.out.println(key);
                    ExecutorMap.put(key, Executors.newSingleThreadScheduledExecutor());
                }
            } else {
                ExecutorMap.put(classToBeLoaded, Executors.newSingleThreadScheduledExecutor());
            }
        }

        /* Shuffle list order */
        long seed = System.nanoTime();
        for (Map.Entry<String, ArrayList<String>> entrySet : campMap.entrySet()) {
            ArrayList<String> value = entrySet.getValue();
            Collections.shuffle(value, new Random(seed));
        }

        for (Map.Entry<String, ArrayList<String>> entrySet : campMap.entrySet()) {
            String classToBeLoaded = entrySet.getKey();
            ArrayList<String> value = entrySet.getValue();
            if (classToBeLoaded.equalsIgnoreCase(special) && n > 1) {
                int s = value.size();
                int partSize = s / n;
                for (int i = 0; i < n; i++) {
                    String key = classToBeLoaded + i;
                    ArrayList<String> sublist = null;
                    if (s != n - 1) {
                        sublist = new ArrayList<String>(value.subList(i * partSize, (i + 1) * partSize));
                    } else {
                        sublist = new ArrayList<String>(value.subList(i * partSize, s));
                    }
                    ExecuteManager em = new ExecuteManager(classToBeLoaded, outFolder, sublist);
                    ExecutorMap.get(key).submit(em);
                }
            } else {
                ExecuteManager em = new ExecuteManager(classToBeLoaded, outFolder, value);
                ExecutorMap.get(classToBeLoaded).submit(em);
            }
        }

//		for (Map.Entry<String, ArrayList<String>> entrySet : campMap.entrySet()) {
//			String classToBeLoaded = entrySet.getKey();
//			if (classToBeLoaded.equalsIgnoreCase("automation.eastlink")) {
//				for (int i = 0; i < 10; i++) {
//					String key = classToBeLoaded + i;
//					ExecutorMap.get(key).shutdown();
//				}
//			} else {
//				ExecutorMap.get(classToBeLoaded).shutdown();
//			}
//		}
//		System.out.println("here");
//		ExecutorService s = Executors.newFixedThreadPool(campMap.size());
//		for (Map.Entry<String, ArrayList<String>> entrySet : campMap.entrySet()) {
//
//			String classToBeLoaded = entrySet.getKey();
//			ArrayList<String> value = entrySet.getValue();
//			System.out.println("\t" + classToBeLoaded + " => " + value.size());
//			ExecuteManager em = new ExecuteManager(classToBeLoaded, outFolder, value);
//			s.submit(em);
//		}
    }


    private void dynClassLoaderExample() {
        /* the following piece of code illustrates how to dynamically load the class for a newly created object */
        /* this is just an example */
        try {
            ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();

            // Step 2: Define a class to be loaded.
            String classNameToBeLoaded = "tracelauncher.DemoClass";

            // Step 3: Load the class
            Class myClass = myClassLoader.loadClass(classNameToBeLoaded);

            // Step 4: create a new instance of that class
            Object whatInstance = myClass.newInstance();

            String methodParameter = "a quick brown fox";
            
            // Step 5: get the method, with proper parameter signature.
            // The second parameter is the parameter type.
            // There can be multiple parameters for the method we are trying to call,
            // hence the use of array.
            Method myMethod = myClass.getMethod("demoMethod",
                    new Class[]{String.class});

            // Step 6:
            // Calling the real method. Passing methodParameter as
            // parameter. You can pass multiple parameters based on
            // the signature of the method you are calling. Hence
            // there is an array.
            String returnValue = (String) myMethod.invoke(whatInstance,
                    new Object[]{methodParameter});

            System.out.println("The value returned from the method is:"
                    + returnValue);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
