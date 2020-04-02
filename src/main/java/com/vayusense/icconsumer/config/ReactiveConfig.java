package com.vayusense.icconsumer.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@Data
@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.vayusense.icconsumer.persistence")
@ConfigurationProperties(prefix = "primary.mongodb")
public class ReactiveConfig extends AbstractReactiveMongoConfiguration {

    //@Bean gives a name (primaryMongoTemplate) to the created MongoTemplate instance
    //Mongo DB Properties
    private String userName;
    private String password;
    private String host, database, replicaSet,uri;
    private int port;


    @Override
    public MongoClient reactiveMongoClient() {

//        MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
        ConnectionString connString = new ConnectionString(uri);
        ConnectionPoolSettings connectionPoolSettings = ConnectionPoolSettings.builder().minSize(5).maxSize(10).maxWaitQueueSize(10000).
                maxWaitTime ( 120, TimeUnit.SECONDS ).build();
        ClusterSettings clusterSettings = ClusterSettings.builder().serverSelectionTimeout (3,TimeUnit.SECONDS ).build();
                //.hosts(Arrays.asList(new ServerAddress(host,port))).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .clusterSettings(clusterSettings)
                .connectionPoolSettings (connectionPoolSettings)
                .applyConnectionString(connString).retryWrites(true)
                //    .credentialList(Arrays.asList(credential))
                .build();

        return MongoClients.create(settings);
    }


    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public ReactiveMongoTemplate getMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient() ,getDatabaseName());
    }
}
