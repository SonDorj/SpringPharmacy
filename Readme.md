# Pharmacy Rest Application Using Spring Boot
<img width="815" alt="image" src="https://github.com/SonDorj/SpringPharmacy/assets/94377854/d2456845-4795-409f-b2a6-c77b3a8ba048"><br>
- start zookeeper<br>
 C:/kafka_2.12-3.5.1/bin/windows/zookeeper-server-start.bat  C:/kafka_2.12-3.5.1/config/zookeeper.properties
- start kafka <br>
   C:/kafka_2.12-3.5.1/bin/windows/kafka-server-start.bat  C:/kafka_2.12-3.5.1/config/server.properties
- Creating the kafka topic pharmacy-messages <br>
   C:/kafka_2.12-3.5.1/bin/windows/kafka-topics.bat --bootstrap-server localhost:9092 --create --topic pharmacy-messages --partitions 1
