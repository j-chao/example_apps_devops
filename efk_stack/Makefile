install:

	helm install efk .

upgrade:

	helm upgrade efk .

purge:

	helm uninstall efk
	kubectl delete secrets \
		elasticsearch-certs \
		elasticsearch-ca
	kubectl delete pvc \
		elasticsearch-data-elasticsearch-data-0 \
		elasticsearch-data-elasticsearch-data-1 \
		elasticsearch-data-elasticsearch-data-2

secrets:

	docker rm -f elastic-certs || true
	rm -f certs/elasticsearch-certs.p12 certs/elasticsearch-ca.pem certs/elastic-stack-ca.p12 || true

	# use elasticsearch-certutil to generate certificates
	docker run --name elastic-certs -i -w /certs -v $(PWD)/certs:/certs \
		elasticsearch:7.6.0 \
	  /bin/sh -c " \
			elasticsearch-certutil ca --pass '' --out /certs/elastic-stack-ca.p12  && \
			elasticsearch-certutil cert --ca /certs/elastic-stack-ca.p12 --name security-master --dns security-master --pass '' --ca-pass '' --out /certs/elasticsearch-certs.p12 \
			"

	# extract CA certificate from pkcs#12 certificate	
	openssl pkcs12 -nodes -passin pass:'' -in certs/elasticsearch-certs.p12 -out certs/elasticsearch-ca.pem

	#openssl pkcs12 -in certs/elasticsearch-certs.p12 -cacerts -nokeys -out certs/elasticsearch-ca.pem
	#openssl pkcs12 -in certs/elasticsearch-certs/elasticsearch-certs.p12 -nocerts -nodes  -out certs/elasticsearch-certs/client.key
	#openssl pkcs12 -in certs/elasticsearch-certs/elasticsearch-certs.p12 -clcerts -nokeys -out certs/elasticsearch-certs/client.cer
	#openssl pkcs12 -in certs/elasticsearch-certs/elasticsearch-certs.p12 -cacerts -nokeys  -chain -out certs/elasticsearch-certs/client-ca.cer


	# create kubernetes secrets from certificates
	kubectl create secret generic elasticsearch-certs --from-file=certs/elasticsearch-certs.p12
	kubectl create secret generic elasticsearch-ca --from-file=certs/elasticsearch-ca.pem 

	# remove docker container
	docker rm elastic-certs



