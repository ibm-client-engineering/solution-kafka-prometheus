package com.ibm.kafkajmxmonitor;

import java.util.regex.*;
import javax.management.*;
import javax.management.remote.*;

import io.prometheus.client.Collector;
import io.prometheus.client.Collector.MetricFamilySamples;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.GaugeMetricFamily;
import io.prometheus.client.exporter.HTTPServer;


import java.rmi.server.RMISocketFactory;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.nio.file.*;
import java.util.*;

public class KafkaJmxClient {
    public static String             metrics_file="metrics.txt";
    public static List<String>       metrics=load_metrics();
    public static Map<String,String> config;
           static CollectorRegistry  registry = new CollectorRegistry();
    public static List<Object>       collectors = new ArrayList<Object>();


    

    public static void main(String[] args) throws Exception {
        // Start Prometheus HTTP Server on port 8080
        KafkaJmxClient.config=get_env_variables();        
        HTTPServer server = new HTTPServer.Builder().withPort(8080).withRegistry(registry).build();        //server url
        String url = "http://localhost:8080/metrics";
        System.out.println("Prometheus metrics available at: " + url);
        
        // Set a custom RMI socket factory with a connection timeout
        set_metrics_timeout();
        // Create the registry
        

        register_metrics();
        
        // Call grab_metrics() method every 30 seconds
        while (true) {
            KafkaJmxClient.grab_metrics();
            Thread.sleep(3000);
        }        
    }



    public static String getNameFromMetric(String metric) {
        String patternString = "name=(.*?),";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(metric);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            
            return metric.replaceAll("[^a-zA-Z0-9]", "_");
        }
    }

    public static List<String> load_metrics(){
        Path filePath = Paths.get(KafkaJmxClient.metrics_file);
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static Map<String, String> get_env_variables() {

            // Get environment variables
            String jmx_ip = getEnvVar("jmx_ip");
            String jmx_port = getEnvVar("jmx_port");
            String jmx_user = getEnvVar("jmx_user");
            String jmx_pass = getEnvVar("jmx_pass");
            //String trustStore = getEnvVar("trustStore");
            //String trustStorePassword = getEnvVar("trustStorePassword");
    
            // Use variables here
            System.out.println("JMX IP: " + jmx_ip);
            System.out.println("JMX Port: " + jmx_port);
            System.out.println("JMX User: " + jmx_user);
            System.out.println("JMX Pass: " + jmx_pass);

            Map<String, String> config = new HashMap<>();
            config.put("jmx_ip"  , jmx_ip);
            config.put("jmx_port", jmx_port);
            config.put("jmx_user", jmx_user);
            config.put("jmx_pass", jmx_pass);
            return config;
    }
    
    private static String getEnvVar(String varName) {
        String varValue = System.getenv(varName);
        if (varValue == null) {
            throw new IllegalArgumentException("Environment variable '" + varName + "' is not set.");
        }
        return varValue;
    }

    public static void set_metrics_timeout() throws IOException{
        // Set a custom RMI socket factory with a connection timeout
        RMISocketFactory.setSocketFactory(new RMISocketFactory() {
            public Socket createSocket(String host, int port) throws IOException {
                Socket socket = new Socket();
                socket.setSoTimeout(100000);  // Set timeout to 1 second
                socket.connect(new InetSocketAddress(host, port), 100000);  // Set connection timeout to 1 second
                return socket;
            }

            public ServerSocket createServerSocket(int port) throws IOException {
                return new ServerSocket(port);
            }
        });
    }

    public static void register_metrics()  throws Exception  {
                // Create a Gauge for each metric
        for (String metric : KafkaJmxClient.metrics) {
            Gauge gauge = Gauge.build()
                        .name(getNameFromMetric(metric))
                        .help(metric)
                        .register(KafkaJmxClient.registry);
            KafkaJmxClient.collectors.add(gauge);

        }
    }


    public static void grab_metrics()  throws Exception  {
        Map<String,String> config=KafkaJmxClient.config;
        //System.setProperty("javax.net.ssl.trustStore", trustStore);
        //#System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

        String url_str="service:jmx:rmi://" 
                        + config.get("jmx_ip") + ":"+config.get("jmx_port")+
                        "/jndi/rmi://" 
                        + config.get("jmx_ip") + ":" + config.get("jmx_port") + "/jmxrmi";



        Map<String, String[]> env = new HashMap<>();
        String[] credentials = new String[] {config.get("jmx_user"), config.get("jmx_pass")};
        
    

        try {
        
            // Use the broker IP in the JMX service URL
            JMXServiceURL url = new JMXServiceURL(url_str);
            env.put(JMXConnector.CREDENTIALS, credentials);
            JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
            

            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
            
            for( String metric : KafkaJmxClient.metrics){
                //System.out.println("- "+metric);
                // ObjectName should be one of the Kafka MBeans, for example kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec
                ObjectName mbeanName = new ObjectName(metric);
                MBeanInfo mbeanInfo =null;
                try{
                    mbeanInfo = mbsc.getMBeanInfo(mbeanName);
                } catch (Exception e){
                    //System.out.println("  MBean not found: " + mbeanName);
                    continue;
                }
                MBeanAttributeInfo[] attributes = mbeanInfo.getAttributes();
        
                for (MBeanAttributeInfo attribute : attributes) {
                    String attributeName = attribute.getName();
                    Object attributeValue = mbsc.getAttribute(mbeanName, attributeName);
                    //System.out.println("  Attribute Name: " + attributeName + ", Value: " + attributeValue);
                    
                    
                    String new_name=getNameFromMetric(metric);
                    
                    //Create Prometheus Gauge for each JMX attribute and set its value
                    
                    int index=KafkaJmxClient.metrics.indexOf(metric);
                    Gauge gauge= (Gauge) KafkaJmxClient.collectors.get(index);

                     gauge.inc();
                    
                    if (attributeValue instanceof Number) {
                        gauge.set(((Number) attributeValue).doubleValue());
                    } else {
                        // Handle non-numeric attributes as needed
                    }

                }
                //System.out.println("----------------------------------------");
            }

            jmxc.close();
        } catch (Exception e) {
            printNestedException(e);
        }

    }

    private static void printNestedException(Throwable e) {
        if (e != null) {
            e.printStackTrace();
            printNestedException(e.getCause());
        }
    }

}