TRUST=
TRUST_PASSWORD=
JMX_URL=k8s-kafkajmx-kafka1-af652f4188-1a664c27aa4d0461.elb.us-east-1.amazonaws.com
JMX_PORT=9999
JMX_USER=
JMX_PASSWORD=


build:
	@mvn clean package

build-deps:
	@mvn dependency:copy-dependencies

check-classes:
	@jar tf target/kafka-jmx-monitor-1.0-jar-with-dependencies.jar

run: build
	echo java -jar target/kafka-jmx-monitor-1.0-jar-with-dependencies.jar 
	export jmx_ip=${JMX_URL};\
	export jmx_user=${JMX_USER};\
	export jmx_pass=${JMX_PASS};\
	export jmx_port=${JMX_PORT};\
	java -jar target/kafka-jmx-monitor-1.0-jar-with-dependencies.jar 


docker-login:
	@docker login dockerhub.io -u chris.watkins@ibm.com

docker: build
	@docker build -f Dockerfile . -t kafka-jmx-monitor:latest
	@docker tag kafka-jmx-monitor:latest chriswatkinsibm/kafka-jmx-monitor:latest
	@docker push chriswatkinsibm/kafka-jmx-monitor:latest

secret:
	@kubectl create secret -n cp4i docker-registry chris-registry --docker-server=registry.watkinslabs.com --docker-username=chris --docker-password='p825amwX$$'

delete-configmap:
	@-kubectl delete configmap kafka-jmx-monitor-config -n kafka-jmx-monitor

configmap: delete-configmap
	@kubectl create configmap kafka-jmx-monitor-config --from-literal=jmx_ip=$(JMX_URL) --from-literal=jmx_port=9999 --from-literal=jmx_user='' --from-literal=jmx_pass='' --from-file=metrics.txt -n kafka-jmx-monitor

delete-pod:
	@-kubectl delete -f kafka-jmx-monitor.yaml -n kafka-jmx-monitor

pod: docker delete-pod
	@kubectl apply -f kafka-jmx-monitor.yaml -n kafka-jmx-monitor

exec-local:
	@docker run -it --entrypoint /bin/bash  kafka-jmx-monitor 
