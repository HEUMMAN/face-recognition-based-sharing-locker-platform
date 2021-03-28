import pandas as pd
import boto3
account = pd.read_csv('new_user_credentials.csv')
accessKeyId = account['Access key ID'][0]
secretAccessKey = account['Secret access key'][0]
def list_faces_in_collection(collection_id):


    maxResults=2
    faces_count=0
    tokens=True

    client = boto3.client('rekognition', region_name='ap-northeast-2', aws_access_key_id=accessKeyId,
                          aws_secret_access_key=secretAccessKey)
    response=client.list_faces(CollectionId=collection_id,
                               MaxResults=maxResults)

    print('Faces in collection ' + collection_id)


    while tokens:

        faces=response['Faces']

        for face in faces:
            print (face)
            faces_count+=1
        if 'NextToken' in response:
            nextToken=response['NextToken']
            response=client.list_faces(CollectionId=collection_id,
                                       NextToken=nextToken,MaxResults=maxResults)
        else:
            tokens=False
    return faces_count
def main():

    collection_id='Collection'

    faces_count=list_faces_in_collection(collection_id)
    print("faces count: " + str(faces_count))
if __name__ == "__main__":
    main()