# Bank Account ![GitHub Workflow Status](https://img.shields.io/github/workflow/status/nevesgustavo/bankaccount/maven)

This is a project for tests purpose, basically it is a bank account that to manage balance and transactions.

![Solution](./extra/img/bank.drawio.png?raw=true "Solution")

### Used tecnologies
 * Java 11
 * Spring boot 2
 * Postgres
 * MongoDB
 * RabbitMQ
 * Redis (To share session and control shared locks)
 * Logging (ElasticSearch, Kibana, FileBeat)
 * Prometheus / Grafana
 * API Gateway Kong / Konga
 * Github actions

### How does it work
 - When a transaction occurs:
   - Validates if the balance is enough
   - Lock control across multiple instances
   - Sends a message to queue, this queue will be consumed in bank statement micro service.
   
### CI/CD
 - After one commit in the main branch:
  - Run all the images used in the project (postgres, mongo, redis)
  - Run maven tests
  - Create and publish image to docker hub registry
   
### How to run
 - Run the docker-compose below, this will:
  - Postgres, mongo, redis, rabbitmq, bankaccount, bankstatement
  
```yaml
version: "3.7"
services:
  postgres:
    container_name: pg_final
    image: postgres:9.6.24
    volumes:
      - pgdata:/var/lib/postgresql/data
    ports:
      - 5432:5432
    environment:
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_USER=postgres
      - POSTGRES_DB=bankaccount
      - PGDATA=/var/lib/postgresql/data/pgdata
    restart: unless-stopped
    networks:
      - app-network

  mongo:
    image: mongo:5.0.2
    container_name: mongodb_account_final
    restart: always
    volumes:
      - mongo_data_container:/data/db
    ports:
      - 27017:27017
    environment:
      MONGO_INITDB_DATABASE: account
    networks:
      - app-network

  redis:
    container_name: redis_final
    image: redis:6.2.6-bullseye
    ports:
      - "6379:6379"
    networks:
      - app-network

  rabbitmq_container:    
    hostname: rabbitmq_container
    image: rabbitmq:management
    ports:
      - 5672:5672
      - 15672:15672
    networks:
      - app-network

  bankaccount:
    image: gustavon/bankaccount:latest
    container_name: bankaccount_final
    ports:
      - 8081:8081
    restart: unless-stopped
    depends_on:
      - postgres
      - redis
      - rabbitmq_container
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/bankaccount
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - SPRING_JPA_HIBERNATE_DDL_AUTO=create-drop
      - SPRING_RABBITMQ_HOST=rabbitmq_container
      - SPRING_RABBITMQ_PORT=5672
      - SPRING_RABBITMQ_USERNAME=guest
      - SPRING_RABBITMQ_PASSWORD=guest
      - SPRING_SESSION_REDIS_HOST=redis
      - SPRING_REDIS_HOST=redis
      - SPRING_PROFILES_ACTIVE=prod
    networks:
      - app-network

  bankstatement:
    image: gustavon/bankstatement:latest
    container_name: bankstatement_final
    ports:
      - 8082:8082
    restart: unless-stopped
    depends_on:
      - mongo
      - rabbitmq_container
    environment:      
      - SPRING_DATA_MONGODB_URI=mongodb://mongo:27017
      - SPRING_RABBITMQ_HOST=rabbitmq_container
      - SPRING_RABBITMQ_PORT=5672
      - SPRING_RABBITMQ_USERNAME=guest
      - SPRING_RABBITMQ_PASSWORD=guest
      - SPRING_PROFILES_ACTIVE=prod
    networks:
      - app-network

networks:
  app-network:
    driver: bridge

volumes:
  pgdata:
  mongo_data_container:
```


### How to test
 - To see the swagger ui access the http:localhost:8081/ and http:localhost:8082/
 
 - Create a transaction:
 ![Solution](./extra/img/create_transaction.png?raw=true "Solution")
 
 - Show balance and statement:
  ![Solution](./extra/img/statement.png?raw=true "Solution")
 
### How to have logs
 - Run the docker-compose below, this will create elasticsearch, kibana, filebeat
 - To access logs just access http://localhost:5601/
 
```yaml
version: "3.7"
services:  
  elasticsearch:
    image: "docker.elastic.co/elasticsearch/elasticsearch:7.2.0"
    environment:
      - "ES_JAVA_OPTS=-Xms1g -Xmx1g"
      - "discovery.type=single-node"
    ports:
      - "9200:9200"
    volumes:
      - elasticsearch_data:/usr/share/elasticsearch/data

  kibana:
    image: "docker.elastic.co/kibana/kibana:7.2.0"
    ports:
      - "5601:5601"

  filebeat:
    image: "docker.elastic.co/beats/filebeat:7.2.0"
    command: filebeat -e -strict.perms=false
    user: root
    volumes:
      - ./filebeat.yml:/usr/share/filebeat/filebeat.yml:ro
      - /var/lib/docker:/var/lib/docker:ro
      - /var/run/docker.sock:/var/run/docker.sock

volumes:
  elasticsearch_data:
``` 

### How to have metrics
 - Run the docker-compose below, this will run prometheus and grafana
 - How to configure grafana
 - Access http://localhost:3000
  - Create prometheus data source
  - Create dashboard, you can import https://grafana.com/grafana/dashboards/6756
 
```yaml
version: "3.7"
services:
  prometheus:
      image: prom/prometheus:latest
      container_name: prometheus
      volumes:
        - "./prometheus.yml:/etc/prometheus/prometheus.yml"
      command:
        - "--config.file=/etc/prometheus/prometheus.yml"
      ports:
        - "9090:9090"
    
  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
```

### How to have kong
 - Run the docker-compose below, this will run cassandra, kong and konga
 
```yaml
version: "3"
services:
  cassandra:
    image: docker.io/bitnami/cassandra:3-debian-10
    volumes:
      - cassandra_data:/bitnami
    environment:
      - CASSANDRA_CLUSTER_NAME=cassandra-cluster
      - CASSANDRA_SEEDS=cassandra
      - CASSANDRA_USER=kong
      - CASSANDRA_PASSWORD=kong
      - CASSANDRA_PASSWORD_SEEDER=yes
  kong:
    image: docker.io/bitnami/kong:2-debian-10
    ports:
      - 8000:8000
      - 8443:8443
      - 8001:8001
    environment:
      - KONG_MIGRATE=yes
      - KONG_DATABASE=cassandra
      - KONG_CASSANDRA_CONTACT_POINTS=cassandra
      - KONG_CASSANDRA_PASSWORD=kong

  konga:
    image: pantsel/konga:latest
    depends_on:
      - kong
    logging:
      options:
        max-size: "50m"
    environment:
      - TOKEN_SECRET=ahfdjgjgf79JKLFHJKh978953kgdfjkl
      - NODE_ENV=development
    ports:
      - "1337:1337"

volumes:
  cassandra_data:
    driver: local
```
