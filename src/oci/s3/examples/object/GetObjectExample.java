package oci.s3.examples.object;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import oci.s3.util.ClientUtils;

public class GetObjectExample {
	
	public static void main(String args[]) {
		
		if (args.length < 3) {
			System.out.println("java oci.s3.examples.object.GetObjectExample <bucket> <object> <target_file>");
			System.exit(1);
		}
		
		AmazonS3 client = null;
		try {
			client = ClientUtils.buildAmazonS3ClientForOCI();
		} catch (IOException e) {
			System.out.println("ERROR: OCI Config file not found");
		}
		
		GetObjectRequest getObjectRequest = new GetObjectRequest(args[0], args[1]);
		S3Object object = client.getObject(getObjectRequest);
		File targetFile = new File(args[2]);
		if (targetFile.exists()) {
			targetFile.delete();
		}

		try {
			Files.copy(object.getObjectContent(), targetFile.toPath());
			System.out.println("Get Object saved to "+targetFile.toPath());
		} catch (IOException e) {
			System.out.println("ERROR: "+e.getMessage());
		}
	}
}
