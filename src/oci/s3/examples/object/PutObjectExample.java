package oci.s3.examples.object;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import oci.s3.util.ClientUtils;

public class PutObjectExample {
	
	public static void main(String args[]) {
		
		if (args.length < 3) {
			System.out.println("java oci.s3.examples.object.PutObjectExample <bucket> <object_key> <file_to_put>");
			System.exit(1);
		}
		
		AmazonS3 client = null;
		try {
			client = ClientUtils.buildAmazonS3Client();
		} catch (IOException e) {
			System.out.println("ERROR: OCI Config file not found");
		}
		
		PutObjectRequest putObjectRequest = new PutObjectRequest(args[0], args[1], new File(args[2]));
		PutObjectResult result = client.putObject(putObjectRequest);
		
		System.out.println("Put object successful, metadata: "+result.getMetadata().getRawMetadata());
	}
}
