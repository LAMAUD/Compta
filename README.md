Compta

Lancer les commandes suivantes :

	docker image build -t <ID docker hub>/compta:1.0 .
	docker stack deploy -c docker-compose.yml compta

Se connecter à :

	http://localhost:8080/index  
