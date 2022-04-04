# Image-Hosting-2

Overview  
Built a simple image-hosting API. You could imagine this as being one of the APIs behind a site like Instagram or Imgur. A user is able to call this API to store an image and then call the API again to retrieve the image. The images will be stored in S3.  

Description  
Created a Spring Boot project. In the spring boot project there are 3 endpoints as follows:  

Upload Image:  
  
This endpoint will accept an uploaded image and transfer it over to S3. It will generate a random filename using UUID for the image and return that in the response.
  
Method: POST
URL: /images
Body: An image upload
Response: A JSON response like { "image": "abc-123" } where image holds the filename
  
Fetch Image:  
  
This endpoint will download a previously uploaded image from S3 based on one of those randomly generated filenames.
  
Method: GET
URL: /images/abc-123
Response: Assuming an image was previously uploaded and given a randomly generated filename of abc-123, the response will contain that image.
  
List Images:
  
This endpoint will list the generated filenames of recently uploaded images. For now, either return all image names up to a maximum of 50.
  
Method: GET
URL: /images
Response: A JSON response with the names of files that exist in the S3 bucket.

 
