import boto3
import cv2
import base64
import pandas as pd
account = pd.read_csv('new_user_credentials.csv')
accessKeyId = account['Access key ID'][0]
secretAccessKey = account['Secret access key'][0]
collection_id = 'Collection'
client = boto3.client('rekognition', region_name='ap-northeast-2', aws_access_key_id=accessKeyId,
                        aws_secret_access_key=secretAccessKey)
number = input("번호를 입력해주세요\n1:얼굴추가\n2:얼굴삭제\n3:리스트출력\n")


def add_faces_to_collection():
    imageName = input("사용자명을 입력해주세요:") + '.jpg'
    print(imageName)
    image = cv2.imread(imageName)
    is_success, im_buf_array = cv2.imencode(".jpg", image)
    byte_im = im_buf_array.tobytes()
    response = client.index_faces(CollectionId=collection_id,
                                  Image={'Bytes': byte_im},
                                  ExternalImageId=imageName,
                                  MaxFaces=1,
                                  QualityFilter="AUTO",
                                  DetectionAttributes=['ALL'])
    print('Faces indexed:')
    for faceRecord in response['FaceRecords']:
        print('  Face ID: ' + faceRecord['Face']['FaceId'])
        print('  Location: {}'.format(faceRecord['Face']['BoundingBox']))

    print('Faces not indexed:')
    for unindexedFace in response['UnindexedFaces']:
        print(' Location: {}'.format(unindexedFace['FaceDetail']['BoundingBox']))
        print(' Reasons:')
        for reason in unindexedFace['Reasons']:
            print('   ' + reason)
    print("Faces indexed count: ")
    print(len(response['FaceRecords']))
    return

def search_faces_list_in_collection():
    userName = input("삭제할 사용자명을 입력해주세요:")
    imageName = userName + '.jpg'
    print(imageName)
    maxResults=1
    tokens=True
    response=client.list_faces(CollectionId=collection_id,
                               MaxResults=maxResults)
    print('Faces in collection ' + collection_id)
    count = 1
    while tokens:
        faces=response['Faces']
        for face in faces:
            print(face)
            for value in face.items():
                if face.get('ExternalImageId') == imageName:
                    faceId = face.get('FaceId')
                    delete_faces_in_collection(faceId)
                    print("\n" + userName + "님이 삭제되었습니다.")
                    break
        if 'NextToken' in response:
            nextToken=response['NextToken']
            response=client.list_faces(CollectionId=collection_id,
                                       NextToken=nextToken,MaxResults=maxResults)
        else:
            tokens=False
    return

def delete_faces_in_collection(faceId):
    faces = []
    faces.append(faceId)
    response = client.delete_faces(CollectionId=collection_id,
                                   FaceIds=faces)
    print(str(len(response['DeletedFaces'])) + ' faces deleted:')
    for faceId in response['DeletedFaces']:
        print(faceId)
    return len(response['DeletedFaces'])

def list_faces_in_collection():
    maxResults = 2
    faces_count = 0
    tokens = True
    response = client.list_faces(CollectionId=collection_id,
                                 MaxResults=maxResults)
    print('Faces in collection ' + collection_id)
    while tokens:
        faces = response['Faces']
        for face in faces:
            print(face)
            faces_count += 1
        if 'NextToken' in response:
            nextToken = response['NextToken']
            response = client.list_faces(CollectionId=collection_id,
                                         NextToken=nextToken, MaxResults=maxResults)
        else:
            tokens = False
    return faces_count

if(number == '1'):
    add_faces_to_collection()
elif(number == '2'):
    search_faces_list_in_collection()
elif(number == '3'):
    list_faces_in_collection()