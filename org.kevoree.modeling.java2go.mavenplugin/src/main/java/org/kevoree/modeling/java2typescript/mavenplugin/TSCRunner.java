package org.kevoree.modeling.java2typescript.mavenplugin;

import de.flapdoodle.embed.nodejs.*;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by duke on 08/12/14.
 */
public class TSCRunner {

    public static void run(File src, File target, File[] libraries, boolean copyLibDTs) throws Exception {
        IRuntimeConfig runtimeConfig = (new NodejsRuntimeConfigBuilder()).defaults().build();
        NodejsProcess node = null;
        ArrayList<String> paramsCol = new ArrayList<String>();
        File[] files = src.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().endsWith(".ts")) {
                paramsCol.add(files[i].getAbsolutePath());
            }
        }
        if(libraries != null) {
            for (int i = 0; i < libraries.length; i++) {
                File[] lib = libraries[i].listFiles();
                for (int j = 0; j < lib.length; j++) {
                    if (lib[j].getName().endsWith(".ts")) {
                        paramsCol.add(lib[j].getAbsolutePath());
                    }
                }
            }
        }

        File targetTSCBIN = new File(target, "tsc.js");
        targetTSCBIN.deleteOnExit();
        Files.copy(TSCRunner.class.getClassLoader().getResourceAsStream("tsc.js"), targetTSCBIN.toPath(), StandardCopyOption.REPLACE_EXISTING);

        if(copyLibDTs) {
            File targetLIBD = new File(target, "lib.d.ts");
            targetLIBD.deleteOnExit();
            Files.copy(TSCRunner.class.getClassLoader().getResourceAsStream("lib.d.ts"), targetLIBD.toPath(), StandardCopyOption.REPLACE_EXISTING);
            paramsCol.add(targetLIBD.getAbsolutePath());
        }

        paramsCol.add("--outDir");
        paramsCol.add(target.getAbsolutePath());

        paramsCol.add("-d");

        NodejsConfig nodejsConfig = new NodejsConfig(NodejsVersion.Main.V0_10, targetTSCBIN.getAbsolutePath(), paramsCol, target.getAbsolutePath());
        NodejsStarter runtime = new NodejsStarter(runtimeConfig);
        try {
            NodejsExecutable e = (NodejsExecutable) runtime.prepare(nodejsConfig);
            node = (NodejsProcess) e.start();
            int retVal = node.waitFor();
            if(retVal != 0) {
                throw new Exception("There were TypeScript compilation errors.");
            }
        } catch (InterruptedException var11) {
            var11.printStackTrace();
        } finally {
            if (node != null) {
                node.stop();
            }
        }
    }

}
