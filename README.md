# TP Bonnes pratiques

## Build le projet (lancer les tests, générer le code à partir du contrat d'API...)

Ce build permet de :
- Lancer les tests
- Générer le code à partir du contrat d'API (Open API)

```bash
./gradlew build
```

Quand le build est passé, la couverture de code est accessible par exemple [ici](address-app/domain/build/reports/jacoco/test/html/index.html)

## Démarrer la base de données

Pour faire fonctionner l'application en production, il est nécessaire qu'une base de données soit démarré.

POur cela, utiliser le `docker-compose.yaml` défini dans le dossier `docker`.

```bash
docker-compose -f docker/docker-compose.yml up -d 
```

## Démarrer l'application

Une fois le conteneur de base de données créé, lancer l'application avec Gradle :
- Via *IntelliJ* : **Task** : `mines` -> `address-app` -> `Tasks` -> `application` -> `bootRun`
- Via ligne de commande : `./gradlew :address-app:app:bootRun`

---

## Informations originales du TP

### Dockerizer l'application

- Créer l'image *Docker* du projet avec `docker build -t address-app -f docker/Dockerfile address-app/application/build/libs`
- Démarrer l'application avec `docker-compose -f docker/docker-compose-production.yml up -d`
- Quand l'application est lancée (avec gradle ou avec docker) : 
    - Lister les utilisateurs `curl localhost:8080/user -H 'content-type:application/json'`
    - Créer un utilisateur `curl localhost:8080/user -d '{"firstname": "John", "lastname": "Doe", "gender": "M", "phone": "123456789", "email": "john@doe.fr", "birthDate": "2003-08-08", "location": {"latitude": 49.1, "longitude": -2.12}}' -H 'content-Type:application/json'`

Il est également possible de générer un client en bash par exemple :
- La liste des générateurs est disponible [ici](https://openapi-generator.tech/docs/generators/))

```bash
docker run --rm -v ${PWD}/address-app/api/src/main/resources:/local -v ${PWD}/build/client:/out openapitools/openapi-generator-cli generate \
  -i /local/address.yml \
  -g bash --additional-properties=library=native,dateLibrary=java8 -o /out
```

Pour utiliser le client généré : 

```bash
./client.sh --host localhost:8080 userSearch
```