package org.springframework.data.simpledb.core;

import org.springframework.util.Assert;

public final class SimpleDbConfig {

    private String accessID;
    private String secretKey;
    private String domainManagementPolicy;
    private String consistentRead;

    private static SimpleDbConfig instance;

    public static SimpleDbConfig createInstance(final String accessID, final String secretKey, String domainManagementPolicy, String consistent){
        if(instance == null){
             instance = new SimpleDbConfig(accessID, secretKey, domainManagementPolicy, consistent);
        }
        return instance;
    }


    public static SimpleDbConfig getInstance(){
        return instance;
    }


    private SimpleDbConfig(final String accessID, final String secretKey, String domainManagementPolicy, String consistentRead){
        Assert.notNull(accessID);
        Assert.notNull(secretKey);

        this.accessID = accessID;
        this.secretKey = secretKey;
        this.domainManagementPolicy = domainManagementPolicy;
        this.consistentRead = consistentRead;

    }


    public String getAccessID() {
        return accessID;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getDomainManagementPolicy() {
        return domainManagementPolicy;
    }

    public boolean isConsistentRead() {
        return "true".equalsIgnoreCase(consistentRead);
    }
}
