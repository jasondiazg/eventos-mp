#Payara micro snapshot
FROM payara/micro:5-SNAPSHOT

#Adding war file
COPY target/eventos-mp.war $DEPLOY_DIR

