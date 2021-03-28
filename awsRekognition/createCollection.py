import boto3


def create_collection(collection_id):
    client = boto3.client('rekognition', region_name='ap-northeast-2', aws_access_key_id='AKIA4EPX72XC3ACFOWVU', aws_secret_access_key='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')

    # Create a collection
    print('Creating collection:' + collection_id)
    response = client.create_collection(CollectionId=collection_id)
    print('Collection ARN: ' + response['CollectionArn'])
    print('Status code: ' + str(response['StatusCode']))
    print('Done...')


def main():
    collection_id = 'Collection'
    create_collection(collection_id)


if __name__ == "__main__":
    main()