package com.spatial.spatialbrain.service;

import static org.math.R.Rsession.cast;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.math.R.Logger;
import org.math.R.RserverConf;
import org.math.R.Rsession;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;

public class rutilize {
        PrintStream p = System.err;
        Rsession s;
        int rand = Math.round((float) Math.random() * 10000);

        public static void main(String[] args) {
            org.junit.runner.JUnitCore.main(rutilize.class.getName());
        }

        //@Test
        public void exeRscript(){
            //1.设置working directory
            String workingDir = System.getProperty("user.dir");
            String wd=workingDir+"\\examples";
            System.out.println(wd);

            //原先考虑设置woringdirectory 之后直接source *.R 发现无效， 后采用文件的完整路径
            //s.voidEval("setwd("+wd+")");

            //2.执行命令source脚本，（ 文件要加入单引号）
            s.voidEval("source('"+wd+"/r_st_base.R')");

            //3.得到结果
        }

        @Test
        public void getRoot(){
            String workingDir = System.getProperty("user.dir");
            System.out.println("Current working directory : " + workingDir);
        }

        /**
         * show k line demo
         */
        //@Test
        public void kLine() {
            System.out.println("kLine##############################");
            /**
             * 需要导入的packages
             */
            s.installPackage("quantmod", true);
            /**
             * append R script
             */
            s.voidEval("getSymbols(\"GOOG\",src=\"yahoo\",from=\"2013-01-01\", to='2013-04-24')");
            s.voidEval("chartSeries(GOOG,up.col='red',dn.col='green')");
            s.voidEval("addMACD()");
            s.voidEval("addRSI()");
        }


        //@Test
        public void testCast() throws REXPMismatchException {
            System.out.println("testClass########################");
            assert ((String[]) cast(s.eval("c('abcd','sdfds')"))).length == 2;
            assert ((double[]) cast(s.eval("array(array(0.0,c(4,3)))"))).length == 12;

            String[] str = (String[]) cast(s.eval("c('abcd','sdfds')"));
            for (String s : str) {
                System.out.println(s);
            }

            Boolean b = (Boolean) cast(s.eval("TRUE"));
            System.out.println(b);

            // Rlist
            RList rlist = (RList) cast(s.eval("data.frame(aa=rnorm(10), bb = rnorm(10))"));

            for (String key : rlist.keys()) {
                System.out.println(key + "::::" + rlist.get(key));
                REXP exp = rlist.at(key);
                double[] ds = exp.asDoubles();
                for (int i = 0; i < ds.length; i++) {
                    System.out.println(ds[i]);
                }
            }

        }

        @Before
        public void init() {
            Logger l = new Logger() {
                @Override
                public void println(String message, Level l) {
                    System.out.println(">>>>>" + l + ">#<" + message);
                }

                @Override
                public void close() {
                }
            };

            RserverConf conf = RserverConf.parse("R://localhost");
            s = Rsession.newInstanceTry(l, conf);
            System.err.println("是否成功的链接到R:" + s.connected);
            try {
                boolean isReachable = Inet4Address.getByName("10.1.104.108").isReachable(1000);
                System.out.println("是否可以Reach本地ip:"+isReachable);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.err.println(s.silentlyEval("R.version.string").asString());
            } catch (REXPMismatchException ex) {
                ex.printStackTrace();
            }
            try {
                System.err.println(
                        "Rserve version " + s.silentlyEval("installed.packages()[\"Rserve\",\"Version\"]").asString());
            } catch (REXPMismatchException ex) {
                ex.printStackTrace();
            }

        }

        @After
        public void tearDown() {
            try {
                // uncomment following for sequential call.
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            s.end();
            // A shutdown hook kills all Rserve at the end.
        }

    }
