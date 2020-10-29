package com.microservices.demo.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class DemoTestExecutionListener implements TestExecutionListener {
	private final static Logger logger = LoggerFactory.getLogger(DemoTestExecutionListener.class);
	
	@Autowired
	private DataSource demo_dataSource;
	
	private IDataSet dataSet;
	
	private IDatabaseConnection iDatabaseConnection;
	
	public DataSource getDatasource() {
		return demo_dataSource;
	}

	public void setDatasource(DataSource demo_dataSource) {
		this.demo_dataSource = demo_dataSource;
	}
	
	public IDataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(IDataSet dataSet) {
		this.dataSet = dataSet;
	}

	public IDatabaseConnection getIDatabaseConnection() throws SQLException {
		iDatabaseConnection = new DatabaseDataSourceConnection(demo_dataSource);
		DatabaseConfig config = iDatabaseConnection.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
		return iDatabaseConnection;
	}
	
	public void setIDatabaseConnection(IDatabaseConnection iDatabaseConnection) {
		this.iDatabaseConnection = iDatabaseConnection;
	}

	public void prepareTestInstance(TestContext context) throws Exception {
		setDatasource ((DataSource) context.getApplicationContext().getBean("demo_dataSource"));
		this.getIDatabaseConnection();
	}

	public void beforeTestMethod(TestContext context) throws Exception {
		String dataSetResourcePath = null;
		DemoDataSet dsLocation = context.getTestClass().getAnnotation(DemoDataSet.class);
		if (dsLocation != null) {
			dataSetResourcePath = dsLocation.value();
			logger.info("Annotated test, using data set: " + dataSetResourcePath);
			if (dataSetResourcePath != null) {
				// Loading the data source
				Resource dataSetResource = context.getApplicationContext().getResource(dataSetResourcePath);
				// Using the data set as FlatXmlDataSet
				dataSet = new FlatXmlDataSetBuilder().build(dataSetResource.getInputStream());
				DatabaseOperation.CLEAN_INSERT.execute(iDatabaseConnection, dataSet);
			}
			else {
				logger.info(context.getClass().getName() + " does not have any data set, no data injection");
			}
		}
	}

	public void afterTestMethod(TestContext context) throws Exception {
		try {
			DatabaseOperation.DELETE_ALL.execute(iDatabaseConnection, dataSet);
		} catch (Exception e) {
			logger.error("CLEAN operation error : " + e.getMessage());
			throw new RuntimeException("CLEAN operation error : " + e.getMessage(), e);
		}
	}

	public void beforeTestClass(TestContext context) throws Exception {
	}

	public void afterTestClass(TestContext context) throws Exception {
	}
}