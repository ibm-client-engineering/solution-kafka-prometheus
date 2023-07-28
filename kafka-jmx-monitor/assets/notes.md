


## Enabling JMX on IBM Eventstream Kafka
- https://ibm.github.io/event-automation/es/security/secure-jmx-connections/

apiVersion: core.eventstreams.ibm.com/v1beta2
kind: StrimziPodSet
metadata:
  annotations:
    eventstreams.ibm.com/kafka-version: 3.4.0
    eventstreams.ibm.com/storage: >-
      {"type":"persistent-claim","size":"4Gi","class":"ocs-storagecluster-ceph-rbd"}
  creationTimestamp: '2023-07-18T20:37:24Z'
  generation: 2
  labels:
    app.kubernetes.io/instance: eventstreams
    app.kubernetes.io/managed-by: eventstreams-cluster-operator
    app.kubernetes.io/name: kafka
    app.kubernetes.io/part-of: eventstreams-eventstreams
    eventstreams.ibm.com/cluster: eventstreams
    eventstreams.ibm.com/component-type: kafka
    eventstreams.ibm.com/kind: Kafka
    eventstreams.ibm.com/name: eventstreams-kafka
  managedFields:
    - apiVersion: core.eventstreams.ibm.com/v1beta2
      fieldsType: FieldsV1
      fieldsV1:
        'f:metadata':
          'f:annotations':
            .: {}
            'f:eventstreams.ibm.com/kafka-version': {}
            'f:eventstreams.ibm.com/storage': {}
          'f:labels':
            .: {}
            'f:app.kubernetes.io/instance': {}
            'f:app.kubernetes.io/managed-by': {}
            'f:app.kubernetes.io/name': {}
            'f:app.kubernetes.io/part-of': {}
            'f:eventstreams.ibm.com/cluster': {}
            'f:eventstreams.ibm.com/component-type': {}
            'f:eventstreams.ibm.com/kind': {}
            'f:eventstreams.ibm.com/name': {}
          'f:ownerReferences':
            .: {}
            'k:{"uid":"054445c4-b882-486b-a7a1-f2b4d3e48dc3"}': {}
        'f:spec':
          .: {}
          'f:selector':
            .: {}
            'f:matchLabels':
              .: {}
              'f:eventstreams.ibm.com/cluster': {}
              'f:eventstreams.ibm.com/kind': {}
              'f:eventstreams.ibm.com/name': {}
      manager: fabric8-kubernetes-client
      operation: Update
      time: '2023-07-18T20:37:24Z'
    - apiVersion: core.eventstreams.ibm.com/v1beta2
      fieldsType: FieldsV1
      fieldsV1:
        'f:status':
          .: {}
          'f:currentPods': {}
          'f:observedGeneration': {}
          'f:pods': {}
          'f:readyPods': {}
      manager: fabric8-kubernetes-client
      operation: Update
      subresource: status
      time: '2023-07-19T14:22:27Z'
    - apiVersion: core.eventstreams.ibm.com/v1beta2
      fieldsType: FieldsV1
      fieldsV1:
        'f:spec':
          'f:pods': {}
      manager: Mozilla
      operation: Update
      time: '2023-07-24T15:45:11Z'
  name: eventstreams-kafka
  namespace: cp4i
  ownerReferences:
    - apiVersion: eventstreams.ibm.com/v1beta2
      blockOwnerDeletion: false
      controller: false
      kind: Kafka
      name: eventstreams
      uid: 054445c4-b882-486b-a7a1-f2b4d3e48dc3
  resourceVersion: '8104306'
  uid: 36d20963-0693-4b6f-807f-8456c0fe5b6a
spec:
  pods:
    - apiVersion: v1
      kind: Pod
      metadata:
        annotations:
          cloudpakId: c8b82d189e7545f0892db9ef2731b90d
          cloudpakName: IBM Cloud Pak for Integration
          eventstreams.ibm.com/broker-configuration-hash: b351cff4
          eventstreams.ibm.com/clients-ca-cert-generation: '0'
          eventstreams.ibm.com/cluster-ca-cert-generation: '0'
          eventstreams.ibm.com/inter-broker-protocol-version: '3.2'
          eventstreams.ibm.com/kafka-version: 3.4.0
          eventstreams.ibm.com/log-message-format-version: '3.2'
          eventstreams.ibm.com/logging-appenders-hash: e893ac9f
          eventstreams.ibm.com/revision: 428141f6
          eventstreams.ibm.com/server-cert-hash: f422fd5a639be9fb461fc163fa9eaf5171f0b28d
          productChargedContainers: kafka
          productCloudpakRatio: '2:1'
          productID: 2a79e49111f44ec3acd89608e56138f5
          productMetric: VIRTUAL_PROCESSOR_CORE
          productName: IBM Event Streams for Non Production
          productVersion: 11.2.1
          prometheus.io/port: '9404'
          prometheus.io/scheme: https
          prometheus.io/scrape: 'true'
        labels:
          app.kubernetes.io/instance: eventstreams
          app.kubernetes.io/managed-by: eventstreams-cluster-operator
          app.kubernetes.io/name: kafka
          app.kubernetes.io/part-of: eventstreams-eventstreams
          eventstreams.ibm.com/cluster: eventstreams
          eventstreams.ibm.com/component-type: kafka
          eventstreams.ibm.com/controller: strimzipodset
          eventstreams.ibm.com/controller-name: eventstreams-kafka
          eventstreams.ibm.com/kind: Kafka
          eventstreams.ibm.com/name: eventstreams-kafka
          eventstreams.ibm.com/pod-name: eventstreams-kafka-0
          statefulset.kubernetes.io/pod-name: eventstreams-kafka-0
        name: eventstreams-kafka-0
        namespace: cp4i
      spec:
        affinity:
          podAntiAffinity:
            preferredDuringSchedulingIgnoredDuringExecution:
              - podAffinityTerm:
                  labelSelector:
                    matchExpressions:
                      - key: app.kubernetes.io/instance
                        operator: In
                        values:
                          - eventstreams
                      - key: app.kubernetes.io/name
                        operator: In
                        values:
                          - kafka
                  topologyKey: kubernetes.io/hostname
                weight: 10
              - podAffinityTerm:
                  labelSelector:
                    matchExpressions:
                      - key: app.kubernetes.io/instance
                        operator: In
                        values:
                          - eventstreams
                      - key: app.kubernetes.io/name
                        operator: In
                        values:
                          - zookeeper
                  topologyKey: kubernetes.io/hostname
                weight: 5
        containers:
          - args:
              - /opt/kafka/kafka_run.sh
            env:
              - name: KAFKA_JMX_OPTS
                value: >-
                  -Dcom.sun.management.jmxremote
                  -Dcom.sun.management.jmxremote.authenticate=false
                  -Dcom.sun.management.jmxremote.ssl=false
                  -Djava.rmi.server.hostname=127.0.0.1
                  -Dcom.sun.management.jmxremote.rmi.port=9999
              - name: KAFKA_METRICS_ENABLED
                value: 'true'
              - name: STRIMZI_KAFKA_GC_LOG_ENABLED
                value: 'false'
              - name: STRIMZI_DYNAMIC_HEAP_PERCENTAGE
                value: '50'
              - name: STRIMZI_DYNAMIC_HEAP_MAX
                value: '5368709120'
              - name: COLLECTOR_HOST
                value: eventstreams-ibm-es-metrics-internal.cp4i.svc
              - name: COLLECTOR_TLS_ENABLED
                value: 'true'
              - name: COLLECTOR_HOSTNAME_VERIFICATION
                value: 'true'
              - name: COLLECTOR_CERT_PATH
                value: /opt/kafka/cluster-ca-certs/ca.crt
              - name: KAFKA_HEAP_OPTS
                value: >-
                  -XX:+UseContainerSupport -Xdump:what
                  -Xdump:java+system+snap:events=user
                  -Xdump:directory=/var/lib/kafka/data/dumps
                  -XX:+HeapDumpOnOutOfMemoryError
              - name: COLLECTOR_PORT
                value: '7443'
            image: >-
              cp.icr.io/cp/ibm-eventstreams-kafka@sha256:465acf420a7d019e2660d94f31ba55440c3e314b9b9f23f84d15ec1822ff53f7
            imagePullPolicy: IfNotPresent
            livenessProbe:
              exec:
                command:
                  - /opt/kafka/kafka_liveness.sh
              initialDelaySeconds: 15
              timeoutSeconds: 5
            name: kafka
            ports:
              - containerPort: 9090
                name: tcp-ctrlplane
                protocol: TCP
              - containerPort: 9091
                name: tcp-replication
                protocol: TCP
              - containerPort: 8091
                name: tcp-runas
                protocol: TCP
              - containerPort: 9094
                name: tcp-external
                protocol: TCP
              - containerPort: 9093
                name: tcp-clientstls
                protocol: TCP
              - containerPort: 9404
                name: tcp-prometheus
                protocol: TCP
              - containerPort: 9999
                name: tcp-jmx
                protocol: TCP
            readinessProbe:
              exec:
                command:
                  - /bin/bash
                  - '-c'
                  - >-
                    if [[ -f /opt/kafka/kafka_readiness.sh ]]; then .
                    /opt/kafka/kafka_readiness.sh; else test -f
                    /var/opt/kafka/kafka-ready; fi
              initialDelaySeconds: 15
              timeoutSeconds: 5
            resources:
              limits:
                cpu: 1000m
                memory: 2Gi
              requests:
                cpu: 100m
                memory: 128Mi
            securityContext:
              allowPrivilegeEscalation: false
              capabilities:
                drop:
                  - ALL
              privileged: false
              readOnlyRootFilesystem: false
              runAsNonRoot: true
            volumeMounts:
              - mountPath: /var/lib/kafka/data
                name: data
              - mountPath: /tmp
                name: strimzi-tmp
              - mountPath: /opt/kafka/cluster-ca-certs
                name: cluster-ca
              - mountPath: /opt/kafka/broker-certs
                name: broker-certs
              - mountPath: /opt/kafka/client-ca-certs
                name: client-ca-cert
              - mountPath: /opt/kafka/custom-config/
                name: kafka-metrics-and-logging
              - mountPath: /var/opt/kafka
                name: ready-files
        hostname: eventstreams-kafka-0
        imagePullSecrets:
          - name: ibm-entitlement-key
        restartPolicy: Always
        schedulerName: default-scheduler
        securityContext:
          runAsNonRoot: true
        serviceAccountName: eventstreams-kafka
        subdomain: eventstreams-kafka-brokers
        terminationGracePeriodSeconds: 30
        volumes:
          - name: data
            persistentVolumeClaim:
              claimName: data-eventstreams-kafka-0
          - emptyDir:
              medium: Memory
              sizeLimit: 30Mi
            name: strimzi-tmp
          - name: cluster-ca
            secret:
              defaultMode: 292
              secretName: eventstreams-cluster-ca-cert
          - name: broker-certs
            secret:
              defaultMode: 292
              secretName: eventstreams-kafka-brokers
          - name: client-ca-cert
            secret:
              defaultMode: 292
              secretName: eventstreams-clients-ca-cert
          - configMap:
              name: eventstreams-kafka-0
            name: kafka-metrics-and-logging
          - emptyDir:
              medium: Memory
              sizeLimit: 1Ki
            name: ready-files
    - apiVersion: v1
      kind: Pod
      metadata:
        annotations:
          cloudpakId: c8b82d189e7545f0892db9ef2731b90d
          cloudpakName: IBM Cloud Pak for Integration
          eventstreams.ibm.com/broker-configuration-hash: e1d39f2d
          eventstreams.ibm.com/clients-ca-cert-generation: '0'
          eventstreams.ibm.com/cluster-ca-cert-generation: '0'
          eventstreams.ibm.com/inter-broker-protocol-version: '3.2'
          eventstreams.ibm.com/kafka-version: 3.4.0
          eventstreams.ibm.com/log-message-format-version: '3.2'
          eventstreams.ibm.com/logging-appenders-hash: e893ac9f
          eventstreams.ibm.com/revision: 382d56a4
          eventstreams.ibm.com/server-cert-hash: bd6af2d505174d06f696105b17e55289f7ec882c
          productChargedContainers: kafka
          productCloudpakRatio: '2:1'
          productID: 2a79e49111f44ec3acd89608e56138f5
          productMetric: VIRTUAL_PROCESSOR_CORE
          productName: IBM Event Streams for Non Production
          productVersion: 11.2.1
          prometheus.io/port: '9404'
          prometheus.io/scheme: https
          prometheus.io/scrape: 'true'
        labels:
          app.kubernetes.io/instance: eventstreams
          app.kubernetes.io/managed-by: eventstreams-cluster-operator
          app.kubernetes.io/name: kafka
          app.kubernetes.io/part-of: eventstreams-eventstreams
          eventstreams.ibm.com/cluster: eventstreams
          eventstreams.ibm.com/component-type: kafka
          eventstreams.ibm.com/controller: strimzipodset
          eventstreams.ibm.com/controller-name: eventstreams-kafka
          eventstreams.ibm.com/kind: Kafka
          eventstreams.ibm.com/name: eventstreams-kafka
          eventstreams.ibm.com/pod-name: eventstreams-kafka-1
          statefulset.kubernetes.io/pod-name: eventstreams-kafka-1
        name: eventstreams-kafka-1
        namespace: cp4i
      spec:
        affinity:
          podAntiAffinity:
            preferredDuringSchedulingIgnoredDuringExecution:
              - podAffinityTerm:
                  labelSelector:
                    matchExpressions:
                      - key: app.kubernetes.io/instance
                        operator: In
                        values:
                          - eventstreams
                      - key: app.kubernetes.io/name
                        operator: In
                        values:
                          - kafka
                  topologyKey: kubernetes.io/hostname
                weight: 10
              - podAffinityTerm:
                  labelSelector:
                    matchExpressions:
                      - key: app.kubernetes.io/instance
                        operator: In
                        values:
                          - eventstreams
                      - key: app.kubernetes.io/name
                        operator: In
                        values:
                          - zookeeper
                  topologyKey: kubernetes.io/hostname
                weight: 5
        containers:
          - args:
              - /opt/kafka/kafka_run.sh
            env:
              - name: KAFKA_JMX_OPTS
                value: >-
                  -Dcom.sun.management.jmxremote
                  -Dcom.sun.management.jmxremote.authenticate=false
                  -Dcom.sun.management.jmxremote.ssl=false
                  -Djava.rmi.server.hostname=127.0.0.1
                  -Dcom.sun.management.jmxremote.rmi.port=9999
              - name: KAFKA_METRICS_ENABLED
                value: 'true'
              - name: STRIMZI_KAFKA_GC_LOG_ENABLED
                value: 'false'
              - name: STRIMZI_DYNAMIC_HEAP_PERCENTAGE
                value: '50'
              - name: STRIMZI_DYNAMIC_HEAP_MAX
                value: '5368709120'
              - name: COLLECTOR_HOST
                value: eventstreams-ibm-es-metrics-internal.cp4i.svc
              - name: COLLECTOR_TLS_ENABLED
                value: 'true'
              - name: COLLECTOR_HOSTNAME_VERIFICATION
                value: 'true'
              - name: COLLECTOR_CERT_PATH
                value: /opt/kafka/cluster-ca-certs/ca.crt
              - name: KAFKA_HEAP_OPTS
                value: >-
                  -XX:+UseContainerSupport -Xdump:what
                  -Xdump:java+system+snap:events=user
                  -Xdump:directory=/var/lib/kafka/data/dumps
                  -XX:+HeapDumpOnOutOfMemoryError
              - name: COLLECTOR_PORT
                value: '7443'
            image: >-
              cp.icr.io/cp/ibm-eventstreams-kafka@sha256:465acf420a7d019e2660d94f31ba55440c3e314b9b9f23f84d15ec1822ff53f7
            imagePullPolicy: IfNotPresent
            livenessProbe:
              exec:
                command:
                  - /opt/kafka/kafka_liveness.sh
              initialDelaySeconds: 15
              timeoutSeconds: 5
            name: kafka
            ports:
              - containerPort: 9090
                name: tcp-ctrlplane
                protocol: TCP
              - containerPort: 9091
                name: tcp-replication
                protocol: TCP
              - containerPort: 8091
                name: tcp-runas
                protocol: TCP
              - containerPort: 9094
                name: tcp-external
                protocol: TCP
              - containerPort: 9093
                name: tcp-clientstls
                protocol: TCP
              - containerPort: 9404
                name: tcp-prometheus
                protocol: TCP
              - containerPort: 9999
                name: tcp-jmx
                protocol: TCP
            readinessProbe:
              exec:
                command:
                  - /bin/bash
                  - '-c'
                  - >-
                    if [[ -f /opt/kafka/kafka_readiness.sh ]]; then .
                    /opt/kafka/kafka_readiness.sh; else test -f
                    /var/opt/kafka/kafka-ready; fi
              initialDelaySeconds: 15
              timeoutSeconds: 5
            resources:
              limits:
                cpu: 1000m
                memory: 2Gi
              requests:
                cpu: 100m
                memory: 128Mi
            securityContext:
              allowPrivilegeEscalation: false
              capabilities:
                drop:
                  - ALL
              privileged: false
              readOnlyRootFilesystem: false
              runAsNonRoot: true
            volumeMounts:
              - mountPath: /var/lib/kafka/data
                name: data
              - mountPath: /tmp
                name: strimzi-tmp
              - mountPath: /opt/kafka/cluster-ca-certs
                name: cluster-ca
              - mountPath: /opt/kafka/broker-certs
                name: broker-certs
              - mountPath: /opt/kafka/client-ca-certs
                name: client-ca-cert
              - mountPath: /opt/kafka/custom-config/
                name: kafka-metrics-and-logging
              - mountPath: /var/opt/kafka
                name: ready-files
        hostname: eventstreams-kafka-1
        imagePullSecrets:
          - name: ibm-entitlement-key
        restartPolicy: Always
        schedulerName: default-scheduler
        securityContext:
          runAsNonRoot: true
        serviceAccountName: eventstreams-kafka
        subdomain: eventstreams-kafka-brokers
        terminationGracePeriodSeconds: 30
        volumes:
          - name: data
            persistentVolumeClaim:
              claimName: data-eventstreams-kafka-1
          - emptyDir:
              medium: Memory
              sizeLimit: 30Mi
            name: strimzi-tmp
          - name: cluster-ca
            secret:
              defaultMode: 292
              secretName: eventstreams-cluster-ca-cert
          - name: broker-certs
            secret:
              defaultMode: 292
              secretName: eventstreams-kafka-brokers
          - name: client-ca-cert
            secret:
              defaultMode: 292
              secretName: eventstreams-clients-ca-cert
          - configMap:
              name: eventstreams-kafka-1
            name: kafka-metrics-and-logging
          - emptyDir:
              medium: Memory
              sizeLimit: 1Ki
            name: ready-files
    - apiVersion: v1
      kind: Pod
      metadata:
        annotations:
          cloudpakId: c8b82d189e7545f0892db9ef2731b90d
          cloudpakName: IBM Cloud Pak for Integration
          eventstreams.ibm.com/broker-configuration-hash: 78bc9291
          eventstreams.ibm.com/clients-ca-cert-generation: '0'
          eventstreams.ibm.com/cluster-ca-cert-generation: '0'
          eventstreams.ibm.com/inter-broker-protocol-version: '3.2'
          eventstreams.ibm.com/kafka-version: 3.4.0
          eventstreams.ibm.com/log-message-format-version: '3.2'
          eventstreams.ibm.com/logging-appenders-hash: e893ac9f
          eventstreams.ibm.com/revision: c07f22ac
          eventstreams.ibm.com/server-cert-hash: 4d2bdf5dbd3b6b10296ce42b5fe7d2ef8451e22f
          productChargedContainers: kafka
          productCloudpakRatio: '2:1'
          productID: 2a79e49111f44ec3acd89608e56138f5
          productMetric: VIRTUAL_PROCESSOR_CORE
          productName: IBM Event Streams for Non Production
          productVersion: 11.2.1
          prometheus.io/port: '9404'
          prometheus.io/scheme: https
          prometheus.io/scrape: 'true'
        labels:
          app.kubernetes.io/instance: eventstreams
          app.kubernetes.io/managed-by: eventstreams-cluster-operator
          app.kubernetes.io/name: kafka
          app.kubernetes.io/part-of: eventstreams-eventstreams
          eventstreams.ibm.com/cluster: eventstreams
          eventstreams.ibm.com/component-type: kafka
          eventstreams.ibm.com/controller: strimzipodset
          eventstreams.ibm.com/controller-name: eventstreams-kafka
          eventstreams.ibm.com/kind: Kafka
          eventstreams.ibm.com/name: eventstreams-kafka
          eventstreams.ibm.com/pod-name: eventstreams-kafka-2
          statefulset.kubernetes.io/pod-name: eventstreams-kafka-2
        name: eventstreams-kafka-2
        namespace: cp4i
      spec:
        affinity:
          podAntiAffinity:
            preferredDuringSchedulingIgnoredDuringExecution:
              - podAffinityTerm:
                  labelSelector:
                    matchExpressions:
                      - key: app.kubernetes.io/instance
                        operator: In
                        values:
                          - eventstreams
                      - key: app.kubernetes.io/name
                        operator: In
                        values:
                          - kafka
                  topologyKey: kubernetes.io/hostname
                weight: 10
              - podAffinityTerm:
                  labelSelector:
                    matchExpressions:
                      - key: app.kubernetes.io/instance
                        operator: In
                        values:
                          - eventstreams
                      - key: app.kubernetes.io/name
                        operator: In
                        values:
                          - zookeeper
                  topologyKey: kubernetes.io/hostname
                weight: 5
        containers:
          - args:
              - /opt/kafka/kafka_run.sh
            env:
              - name: KAFKA_JMX_OPTS
                value: >-
                  -Dcom.sun.management.jmxremote
                  -Dcom.sun.management.jmxremote.authenticate=false
                  -Dcom.sun.management.jmxremote.ssl=false
                  -Djava.rmi.server.hostname=127.0.0.1
                  -Dcom.sun.management.jmxremote.rmi.port=9999
              - name: KAFKA_METRICS_ENABLED
                value: 'true'
              - name: STRIMZI_KAFKA_GC_LOG_ENABLED
                value: 'false'
              - name: STRIMZI_DYNAMIC_HEAP_PERCENTAGE
                value: '50'
              - name: STRIMZI_DYNAMIC_HEAP_MAX
                value: '5368709120'
              - name: COLLECTOR_HOST
                value: eventstreams-ibm-es-metrics-internal.cp4i.svc
              - name: COLLECTOR_TLS_ENABLED
                value: 'true'
              - name: COLLECTOR_HOSTNAME_VERIFICATION
                value: 'true'
              - name: COLLECTOR_CERT_PATH
                value: /opt/kafka/cluster-ca-certs/ca.crt
              - name: KAFKA_HEAP_OPTS
                value: >-
                  -XX:+UseContainerSupport -Xdump:what
                  -Xdump:java+system+snap:events=user
                  -Xdump:directory=/var/lib/kafka/data/dumps
                  -XX:+HeapDumpOnOutOfMemoryError
              - name: COLLECTOR_PORT
                value: '7443'
            image: >-
              cp.icr.io/cp/ibm-eventstreams-kafka@sha256:465acf420a7d019e2660d94f31ba55440c3e314b9b9f23f84d15ec1822ff53f7
            imagePullPolicy: IfNotPresent
            livenessProbe:
              exec:
                command:
                  - /opt/kafka/kafka_liveness.sh
              initialDelaySeconds: 15
              timeoutSeconds: 5
            name: kafka
            ports:
              - containerPort: 9090
                name: tcp-ctrlplane
                protocol: TCP
              - containerPort: 9091
                name: tcp-replication
                protocol: TCP
              - containerPort: 8091
                name: tcp-runas
                protocol: TCP
              - containerPort: 9094
                name: tcp-external
                protocol: TCP
              - containerPort: 9093
                name: tcp-clientstls
                protocol: TCP
              - containerPort: 9404
                name: tcp-prometheus
                protocol: TCP
              - containerPort: 9999
                name: tcp-jmx
                protocol: TCP
            readinessProbe:
              exec:
                command:
                  - /bin/bash
                  - '-c'
                  - >-
                    if [[ -f /opt/kafka/kafka_readiness.sh ]]; then .
                    /opt/kafka/kafka_readiness.sh; else test -f
                    /var/opt/kafka/kafka-ready; fi
              initialDelaySeconds: 15
              timeoutSeconds: 5
            resources:
              limits:
                cpu: 1000m
                memory: 2Gi
              requests:
                cpu: 100m
                memory: 128Mi
            securityContext:
              allowPrivilegeEscalation: false
              capabilities:
                drop:
                  - ALL
              privileged: false
              readOnlyRootFilesystem: false
              runAsNonRoot: true
            volumeMounts:
              - mountPath: /var/lib/kafka/data
                name: data
              - mountPath: /tmp
                name: strimzi-tmp
              - mountPath: /opt/kafka/cluster-ca-certs
                name: cluster-ca
              - mountPath: /opt/kafka/broker-certs
                name: broker-certs
              - mountPath: /opt/kafka/client-ca-certs
                name: client-ca-cert
              - mountPath: /opt/kafka/custom-config/
                name: kafka-metrics-and-logging
              - mountPath: /var/opt/kafka
                name: ready-files
        hostname: eventstreams-kafka-2
        imagePullSecrets:
          - name: ibm-entitlement-key
        restartPolicy: Always
        schedulerName: default-scheduler
        securityContext:
          runAsNonRoot: true
        serviceAccountName: eventstreams-kafka
        subdomain: eventstreams-kafka-brokers
        terminationGracePeriodSeconds: 30
        volumes:
          - name: data
            persistentVolumeClaim:
              claimName: data-eventstreams-kafka-2
          - emptyDir:
              medium: Memory
              sizeLimit: 30Mi
            name: strimzi-tmp
          - name: cluster-ca
            secret:
              defaultMode: 292
              secretName: eventstreams-cluster-ca-cert
          - name: broker-certs
            secret:
              defaultMode: 292
              secretName: eventstreams-kafka-brokers
          - name: client-ca-cert
            secret:
              defaultMode: 292
              secretName: eventstreams-clients-ca-cert
          - configMap:
              name: eventstreams-kafka-2
            name: kafka-metrics-and-logging
          - emptyDir:
              medium: Memory
              sizeLimit: 1Ki
            name: ready-files
  selector:
    matchLabels:
      eventstreams.ibm.com/cluster: eventstreams
      eventstreams.ibm.com/kind: Kafka
      eventstreams.ibm.com/name: eventstreams-kafka
status:
  currentPods: 3
  observedGeneration: 1
  pods: 3
  readyPods: 3
