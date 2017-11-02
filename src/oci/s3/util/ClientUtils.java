package oci.s3.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class ClientUtils {
	
	public static AmazonS3 buildAmazonS3ClientForOCI() throws IOException {
		// load oci config file
		Properties ociConfigProps = new Properties();
		FileInputStream configStream = new FileInputStream("oci.config");
		ociConfigProps.load(configStream);
		configStream.close();

		String region = ociConfigProps.getProperty("oci.region");
		
		AWSCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials(
				ociConfigProps.getProperty("oci.s3_compat.access_key.id"),
				ociConfigProps.getProperty("oci.s3_compat.access_key")));
		
		String endpoint = String.format("%s.compat.objectstorage.%s.oraclecloud.com", ociConfigProps.getProperty("oci.tenancy"), region);
		AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
		AmazonS3 client = AmazonS3Client.builder().standard()
		 .withCredentials(credentials)
		 .withEndpointConfiguration(endpointConfiguration)
		 .disableChunkedEncoding()
		 .enablePathStyleAccess()
		 .build();	
		
		return client;
	}

}
