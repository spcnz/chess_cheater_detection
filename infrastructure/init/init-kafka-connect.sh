#!/bin/bash

echo "Wait for Kafka Connect to be ready"
echo "Waiting for Kafka Connect to start listening on localhost"
while : ; do
    curl -v http://localhost:8083/connectors > /dev/null 2>&1
    if [ $? -eq 0 ]; then
        echo "Kafka Connect is up and running."
        break
    else
        echo -e $(date) " Kafka Connect listener HTTP state: not ready (waiting for 200)"
    fi
    sleep 5
done

echo "Submitting InfluxDB Sink Connector configuration"
# curl -X POST -H "Content-Type: application/json" --data @./game-kpi-sink-config.json http://localhost:8083/connectors
# curl -X POST -H "Content-Type: application/json" --data @./game-result-sink-config.json http://localhost:8083/connectors
curl -X POST -H "Content-Type: application/json" --data @./games-sink-config.json http://localhost:8083/connectors
# curl -X POST -H "Content-Type: application/json" --data @./moves-sink-config.json http://localhost:8083/connectors
# curl -X POST -H "Content-Type: application/json" --data @./player-sink-config.json http://localhost:8083/connectors
