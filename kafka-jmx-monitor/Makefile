TRUST=/home/nd/repos/IBM/solution-kafka-prometheus/kafka-jmx-monitor/es-cert.p12
TRUST_PASSWORD=u4MInTk6uaFr
JMX_URL=10.129.3.14
JMX_PORT=9999
JMX_USER=N8wg8qmuOBShXDsE
JMX_PASSWORD=bTioTp9WXJJSHBlo


build:
	@mvn clean package

build-deps:
	@mvn dependency:copy-dependencies

check-classes:
	@jar tf target/kafka-jmx-monitor-1.0-jar-with-dependencies.jar

run: build
	echo java -jar target/kafka-jmx-monitor-1.0-jar-with-dependencies.jar $(JMX_URL) $(JMX_PORT) $(JMX_USER) $(JMX_PASSWORD) $(TRUST) $(TRUST_PASSWORD)
	@java -jar target/kafka-jmx-monitor-1.0-jar-with-dependencies.jar $(JMX_URL) $(JMX_PORT) $(JMX_USER) $(JMX_PASSWORD) $(TRUST) $(TRUST_PASSWORD)


docker-login:
	@docker login registry.watkinslabs.com -u chris
docker: build
	@docker build -f Dockerfile . -t kafka-jmx-monitor:latest
	@docker tag kafka-jmx-monitor:latest registry.watkinslabs.com/kafka-jmx-monitor:latest
	@docker push registry.watkinslabs.com/kafka-jmx-monitor:latest

secret:
	@oc create secret -n cp4i docker-registry chris-registry --docker-server=registry.watkinslabs.com --docker-username=chris --docker-password='p825amwX$$'

delete-configmap:
	@oc delete configmap kafka-jmx-monitor-config -n cp4i

configmap: delete-configmap
	@oc create configmap kafka-jmx-monitor-config --from-literal=jmx_ip=$(JMX_URL) --from-literal=jmx_port=9999 --from-file=metrics.txt -n cp4i 

delete-pod:
	@oc delete -f kafka-jmx-monitor.yaml  

pod: docker delete-pod
	@oc apply -f kafka-jmx-monitor.yaml 

exec-local:
	@docker run -it --entrypoint /bin/bash  kafka-jmx-monitor 