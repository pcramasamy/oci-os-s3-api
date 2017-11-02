package oci.s3.examples.object;
import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import oci.s3.util.ClientUtils;

public class DeleteObjectExample {
	
	public static void main(String args[]) {
		
		if (args.length < 2) {
			System.out.println("java oci.s3.examples.object.DeleteObjectExample <bucket> <object>");
			System.exit(1);
		}
		
		AmazonS3 client = null;
		try {
			client = ClientUtils.buildAmazonS3Client();
		} catch (IOException e) {
			System.out.println("ERROR: OCI Config file not found");
		}
		
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(args[0], args[1]);
		client.deleteObject(deleteObjectRequest);
		
		System.out.println("Deleted object " + args[1] + " from bucket " + args[0]);
	}
}
