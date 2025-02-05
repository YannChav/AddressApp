
# mines2024

# Initialiser le projet 

```bash
gradle wrapper
```

# Build le projet (lancer les tests, générer le code à partir du contrat d'API...)

```bash
./gradlew build
```

## quand le build est passé, la couverture de code est accessible par exemple [ici](address-app/domain/build/reports/jacoco/test/html/index.html)

# lancer l'application avec gradle

```bash
docker-compose -f docker/docker-compose.yml up -d 
./gradlew :address-app:app:bootRun
```

# Dockerizer l'application

```bash
docker build -t address-app -f docker/Dockerfile address-app/application/build/libs 
```

# Lancer l'application avec docker 

```bash
docker-compose -f docker/docker-compose-production.yml up -d
```

## Quand l'application est lancée (avec gradle ou avec docker) : 

### Lister les villes

```bash
curl localhost:8080/town -H 'content-type:application/json'
```

### Créer une ville

```bash
curl localhost:8080/town -d '{"name":"la ville", "postCode":1}' -H 'content-Type:application/json'
```


## Génerer un client (en bash par exemple): 
## (la liste des générateurs est disponible [ici](https://openapi-generator.tech/docs/generators/))

```bash
docker run --rm -v ${PWD}/address-app/api/src/main/resources:/local -v ${PWD}/build/client:/out openapitools/openapi-generator-cli generate \
  -i /local/address.yml \
  -g bash --additional-properties=library=native,dateLibrary=java8 -o /out
```

## utiliser le client généré : 

```bash
./client.sh --host localhost:8080 createTown name=="laville" postCode==123
./client.sh --host localhost:8080 listTowns
```