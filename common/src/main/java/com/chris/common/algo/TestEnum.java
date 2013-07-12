package com.chris.common.algo;

public class TestEnum 
{
  public static enum Canned
  {
    /**
      * Owner gets FULL_CONTROL.
      */
    PRIVATE("private"),
    
    /**
      * Owner gets FULL_CONTROL and the anonymous principal is granted READ access.
     */
    PUBLIC_READ("public-read"),
    
    /**
      * Owner gets FULL_CONTROL, the anonymous principal is granted READ and WRIT access.
     */
    PUBLIC_READ_WRITE("public-read-write"),
    
    /**
      * Owner gets FULL_CONTROL, and any principal authenticated as a registered,Amazon S3 user is granted READ access.
     */
    AUTHENTICATED_READ("authenticated-read"),
    /**
      * Object Owner gets FULL_CONTROL, Bucket Owner gets READ. 
      * This ACL applies only to objects and is equivalent to private when used with PUT Bucket. You use this
      * ACL to let someone other than the bucket owner write content (get full control) in the bucket but still
      * grant the bucket owner read access to the objects
     */
    BUCKET_OWNER_READ("bucket-owner-read"),
    /**
      * Object Owner gets FULL_CONTROL, Bucket Owner gets FULL_CONTROL.
      * This ACL applies only to objects and is equivalent to private when used with PUT Bucket. You use this
      * ACL to let someone other than the bucket owner write content (get full control) in the bucket but still
      * grant the bucket owner full rights over the objects.
     */
    BUCKET_OWNER_FULL_CONTROL("bucket-owner-full-control");

    private Canned(String canned) { this.canned = canned; }
    private String canned;
    public String canned() { return canned; }
  }

	public static void main(String[] args) 
  {
		for (Canned can : Canned.values() ) {
			System.out.println("--" + can.canned());
		}
		String value = "bucket-owner-full-control";
		if (null != value) {
      for (Canned canned : Canned.values()) {
        if (canned.equals(value)) {
        }
      } 
    } else {
    }
	}
}

