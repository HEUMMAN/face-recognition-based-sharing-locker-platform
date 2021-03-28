import boto3
import pandas as pd
account = pd.read_csv('new_user_credentials.csv')
accessKeyId = account['Access key ID'][0]
secretAccessKey = account['Secret access key'][0]

def delete_faces_from_collection(collection_id, faces):
    client = boto3.client('rekognition', region_name='ap-northeast-2', aws_access_key_id=accessKeyId,
                          aws_secret_access_key=secretAccessKey)

    response = client.delete_faces(CollectionId=collection_id,
                                   FaceIds=faces)

    print(str(len(response['DeletedFaces'])) + ' faces deleted:')
    for faceId in response['DeletedFaces']:
        print(faceId)
    return len(response['DeletedFaces'])


def main():
    collection_id = 'Collection'
    faces = []
    faces.append("b4235332-f7ab-48e5-9bc3-c451e733bacf")

    faces_count = delete_faces_from_collection(collection_id, faces)
    print("deleted faces count: " + str(faces_count))


if __name__ == "__main__":
    main()