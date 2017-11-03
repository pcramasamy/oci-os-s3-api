package oci.s3.examples.object;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;

import oci.s3.util.ClientUtils;

public class MultipartUploadObjectExample {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("java oci.s3.examples.object.MultipartUploadObjectExample <bucket> <object_key> <file_part1> [<file_part2>] [<file_part3>] ...");
			System.exit(1);
		}
		
		AmazonS3 client = null;
		try {
			client = ClientUtils.buildAmazonS3ClientForOCI();
		} catch (IOException e) {
			System.out.println("ERROR: OCI Config file not found");
		}
		
		InitiateMultipartUploadRequest initMPRequest = new InitiateMultipartUploadRequest(args[0], args[1]);
		InitiateMultipartUploadResult initMPResult = client.initiateMultipartUpload(initMPRequest);
		
		List<PartETag> partETags = new ArrayList<PartETag>();
		UploadPartRequest uplPartRequest = new UploadPartRequest();
		UploadPartResult uplPartResult = null;
		
		for(int i=2; i< args.length; i++) {
		    File file = new File(args[i]);
			uplPartRequest.withBucketName(args[0])
				.withKey(args[1]).withUploadId(initMPResult.getUploadId())
				.withPartNumber(i-1)
				.withPartSize(file.length())
				.setFile(file);
			uplPartResult = client.uploadPart(uplPartRequest);
			partETags.add(uplPartResult.getPartETag());
		}
		
		CompleteMultipartUploadRequest compMPRequest = new CompleteMultipartUploadRequest(args[0], args[1], initMPResult.getUploadId(), partETags);
		CompleteMultipartUploadResult compMPResult = client.completeMultipartUpload(compMPRequest);
		
		System.out.println("Multipart upload successful, E-Tag of the object: "+compMPResult.getETag());
	}

}
