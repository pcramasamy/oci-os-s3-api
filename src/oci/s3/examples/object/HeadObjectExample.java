package oci.s3.examples.object;
import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import oci.s3.util.ClientUtils;

public class HeadObjectExample {
	
	public static void main(String args[]) {
		
		if (args.length < 2) {
			System.out.println("java oci.s3.examples.object.HeadObjectExample <bucket> <object>");
			System.exit(1);
		}
		
		AmazonS3 client = null;
		try {
			client = ClientUtils.buildAmazonS3ClientForOCI();
		} catch (IOException e) {
			System.out.println("ERROR: OCI Config file not found");
		}
		
		GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest(args[0], args[1]);
		ObjectMetadata metadata = client.getObjectMetadata(getObjectMetadataRequest);
		
		System.out.println("Metadata of object " + args[1] + " : " + metadata.getRawMetadata());
	}
}
