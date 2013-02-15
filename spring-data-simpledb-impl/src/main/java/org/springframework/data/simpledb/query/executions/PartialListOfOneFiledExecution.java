package org.springframework.data.simpledb.query.executions;

import org.springframework.data.simpledb.core.SimpleDbOperations;
import org.springframework.data.simpledb.query.SimpleDbQueryRunner;
import org.springframework.data.simpledb.query.SimpleDbRepositoryQuery;
import org.springframework.data.simpledb.query.SimpleDbResultConverter;

import java.io.Serializable;
import java.util.List;

public class PartialListOfOneFiledExecution extends AbstractSimpleDbQueryExecution {

    public PartialListOfOneFiledExecution(SimpleDbOperations<?, Serializable> simpleDbOperations) {
        super(simpleDbOperations);
    }

    @Override
    protected Object doExecute(SimpleDbRepositoryQuery repositoryQuery, SimpleDbQueryRunner queryRunner) {
        String attributeName = queryRunner.getSingleQueryFieldName();
        List<?> returnListFromDb = queryRunner.executeQuery();
        return SimpleDbResultConverter.filterAsListAttributesNamed(returnListFromDb, attributeName);
    }
}