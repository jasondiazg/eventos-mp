#Payara micro snapshot
FROM payara/micro:5-SNAPSHOT

#Adding war file
COPY target/eventos-mp-0.0.1.war $DEPLOY_DIR

