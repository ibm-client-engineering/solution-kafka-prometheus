BROKER=localhost
PORT=9092
TOPIC=purchases
NS=kafka-jmx-monitor
DATA_COUNT=1
DATA_ENTITIES=5000

KAFKA1=$(shell kubectl get pods -n ${NS} | grep kafka1|  cut -d  ' ' -f 1)
KAFKA2=$(shell kubectl get pods -n ${NS} | grep kafka2|  cut -d  ' ' -f 1)
KAFKA3=$(shell kubectl get pods -n ${NS} | grep kafka3|  cut -d  ' ' -f 1)

BROKER1=$(shell kubectl get svc -n  ${NS} | grep kafka1| tr -s ' '| cut -d  ' ' -f 4)
BROKER2=$(shell kubectl get svc -n  ${NS} | grep kafka2| tr -s ' '| cut -d  ' ' -f 4)
BROKER3=$(shell kubectl get svc -n  ${NS} | grep kafka3| tr -s ' '| cut -d  ' ' -f 4)



vars:
	@echo Broker1: ${BROKER1}
	@echo Broker2: ${BROKER2}
	@echo Broker3: ${BROKER3}

## LOGS
log1:
	@kubectl logs  ${KAFKA1} -n ${NS}

log2:
	@kubectl logs  ${KAFKA2} -n ${NS}

log3:
	@kubectl logs  ${KAFKA3} -n ${NS}


## KAFKA BUILD
kafka-service:
	@kubectl apply -f yaml/kafka-service.yaml -n ${NS}

kafka-service-delete:
	@kubectl delete -f yaml/kafka-service.yaml -n ${NS}

delete-kafka:
	@-kubectl delete -f yaml/kafka-cluster.yaml -n ${NS} 

kafka:
	@kubectl apply -f yaml/kafka-cluster.yaml -n ${NS} 

## ZOO BUILD
zoo-service:
	@kubectl apply -f yaml/zookeeper-service.yaml -n ${NS}

zoo-service-delete:
	@kubectl delete -f yaml/zookeeper-service.yaml -n ${NS}

zoo:
	@kubectl apply -f yaml/zookeeper-deployment.yaml -n ${NS} 

delete-zoo:
	@-kubectl delete -f yaml/zookeeper-deployment.yaml -n ${NS} 


## AUTO BUILD
delete-system:  delete-kafka delete-zoo

system:  delete-system zoo kafka

# KUBECTL FUNCTIONS

pods:
	@kubectl get pods -n ${NS} 

watch:
	@kubectl get pods -n ${NS} -w

svc:
	@kubectl get svc -n ${NS} 


kafka1_dns:
	@kubectl get svc -n ${NS} | grep kafka |tr -s " "| cut -d ' ' -f 4| grep kafka1


desc1:
	@kubectl describe pod ${KAFKA1} -n ${NS}

# PORT FORWARDING to containers
pf1:
	@kubectl port-forward  ${KAFKA1} -n ${NS} 9092:9092

pf2:
	@kubectl port-forward  ${KAFKA2} -n ${NS} 9092:9092

pf3:
	@kubectl port-forward  ${KAFKA3} -n $${NS} 9092:9092


# KAFKA Functions
pf-prometheus:
	PROMETHEUS_NS=prometheus;\
	PROMETHEUS_SERVER=$(shell kubectl get pods -n prometheus | grep server| tr -s ' '| cut -d ' ' -f1); \
	kubectl port-forward  $$PROMETHEUS_SERVER -n $$PROMETHEUS_NS 9090

generate-data:
	@echo "Creating data files"
	number=1 ; while [[ $$number -le ${DATA_COUNT} ]] ; do \
		echo "File: $number "; \
        ((number = number + 1)) ; \
		curl "https://api.mockaroo.com/api/d5a195e0?count=${DATA_ENTITIES}&key=ff7856d0" >data/purchases$$number.jsons; \
	done


consumer:
	@echo "Connecting to ${BROKER} and reading  ${TOPIC}" 
	kcat -C -b ${BROKER1} -t ${TOPIC}
#	kcat -C -b ${BROKER}:${PORT} -t ${TOPIC}

producer:
	@echo "This is a loop of ${DATA_COUNT} with ${DATA_ENTITIES} entities per file. Each line is delayed .5 sec. CRTL-C to stop"
	@number=1 ; while [[ $$number -le ${DATA_COUNT} ]] ; do \
        echo "File: $$number "; \
        ((number = number + 1)) ; \
		cat data/purchases$$number.jsons| awk '{print $$0;system("sleep 0.01");}' | kcat -b ${BROKER1} -P  -t ${TOPIC}; \
    done
	
list-brokers:
	@kcat -L -b  ${BROKER1} 
	