import cv2
import boto3 #aws를 쓰기 위해 사용하는 라이브러리
import pandas as pd

account = pd.read_csv('new_user_credentials.csv')
accessKeyId = account['Access key ID'][0]
secretAccessKey = account['Secret access key'][0]
count = 0 #얼굴 인식에 딜레이를 주기 위한 카운트
cap = cv2.VideoCapture(0) #웹캠에서 영상을 읽어온다
cap.set(3, 640) #WIDTH
cap.set(4, 480) #HEIGHT

#얼굴 인식 캐스케이드 파일을 읽는다
face_cascade = cv2.CascadeClassifier('haarcascade_frontface.xml')

while(True):
    ret, frame = cap.read() #비디오를 한 프레임씩 읽는다
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    #인식된 얼굴 갯수를 출력
    #print(len(faces))
    if(len(faces) > 0):
        count = count + 1

    if(count > 50): #카운트가 50이 넘어가면 얼굴이 인식된것으로 간주
        print('얼굴이 검출되었습니다')
        count = 0

        #얼굴이 인식되었다면, 얼굴에 사각형을 출력한다
        for (x,y,w,h) in faces:
             cv2.rectangle(frame,(x,y),(x+w,y+h),(255,0,0),2)
        #인식된 얼굴 프레임을 image.jpg라는 파일명으로 저장한다.
        cv2.imwrite('image.jpg', frame)

        img = cv2.imread('image.jpg')  #image.jpg파일을 불러온다
        key = cv2.waitKey(2000) & 0xFF
        is_success, im_buf_arr = cv2.imencode(".jpg", img)  #이미지 서치를 위해 파일을 binary 형태로 이미지를 읽는다
        byte_im = im_buf_arr.tobytes()  #array를 raw data형태로 바꿔준다(이미지 서치를 위해 필요)
        bucket = 'fabinet' #내 s3버켓명(여기서는 사실 필요 없음)
        collectionId = 'Collection' #내 컬렉션명
        threshold = 80 #인식률 임계값
        maxFaces = 1 #얼굴 하나만 리턴함
        #내 aws 클라이언트 정보다
        client = boto3.client('rekognition', region_name='ap-northeast-2', aws_access_key_id=accessKeyId,
                              aws_secret_access_key=secretAccessKey)
        #위에서 raw data형태로 변환된 사진을 컬렉션에 있는 메타데이터와 비교한다
        response = client.search_faces_by_image(CollectionId=collectionId,
                                                Image={'Bytes': byte_im},
                                                FaceMatchThreshold=threshold,
                                                MaxFaces=maxFaces)

        faceMatches = response['FaceMatches']
        print('Matching faces')
        # 임계값을 넘어서는 유사도가 나올 경우(얼굴이 인식될 경우) 실행한다.
        for match in faceMatches:
            print('FaceId:' + match['Face']['FaceId']) #컬렉션에 저장된 메타데이터 ID출력(우리한텐 필요없는부분이긴 함)
            print('Similarity: ' + "{:.2f}".format(match['Similarity']) + "%") #유사도를 출력한다
            print(match['Face']['ExternalImageId']) #사진명을 출력한다(핵심부분)
            print

    #화면에 출력한다
    cv2.imshow('frame',frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()