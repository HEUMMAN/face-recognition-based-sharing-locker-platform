import cv2
import dlib
import boto3 #boto3는 aws를 쓰기 위해 사용하는 라이브러리
import numpy as np
import pandas as pd
import paho.mqtt.client as mqtt
from imutils import face_utils
from keras.models import load_model
import time
import pyautogui as msg

IMG_SIZE = (34, 26)
detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor('shape_predictor_68_face_landmarks.dat')
model = load_model('models/2018_12_17_22_58_35.h5')

server = "3.34.255.198"
mclient = mqtt.Client()
mclient.connect(server, 1883)
account = pd.read_csv('new_user_credentials.csv')
accessKeyId = account['Access key ID'][0]
secretAccessKey = account['Secret access key'][0]
collectionId = 'Collection' #내 컬렉션명
threshold = 80 #인식률 임계값
maxFaces = 1 #얼굴 하나만 리턴함
client = boto3.client('rekognition', region_name='ap-northeast-2', aws_access_key_id=accessKeyId,
                        aws_secret_access_key=secretAccessKey) #내 aws 클라이언트 정보다
count = 0 #얼굴 인식에 딜레이를 주기 위한 카운트
cap = cv2.VideoCapture(0) #웹캠에서 영상을 읽어온다
#cap.set(3, 480) #WIDTH
#cap.set(4, 320) #HEIGHT
cap.set(4, 720) #WIDTH
cap.set(3, 480) #HEIGHT
face_cascade = cv2.CascadeClassifier('haarcascade_frontface.xml')#얼굴 인식 캐스케이드 파일을 읽는다
blinkCount = 0
timeOut = 0
def crop_eye(img, eye_points):
    IMG_SIZE = (34, 26)

    x1, y1 = np.amin(eye_points, axis=0)
    x2, y2 = np.amax(eye_points, axis=0)
    cx, cy = (x1 + x2) / 2, (y1 + y2) / 2

    w = (x2 - x1) * 1.2
    h = w * IMG_SIZE[1] / IMG_SIZE[0]
    margin_x, margin_y = w / 2, h / 2

    min_x, min_y = int(cx - margin_x), int(cy - margin_y)
    max_x, max_y = int(cx + margin_x), int(cy + margin_y)

    eye_rect = np.rint([min_x, min_y, max_x, max_y]).astype(np.int)
    eye_img = gray[eye_rect[1]:eye_rect[3], eye_rect[0]:eye_rect[2]]

    return eye_img, eye_rect

def faceDetect():
    print('얼굴이 검출되었습니다')
    ret, frame = cap.read()  # 비디오를 한 프레임씩 읽는다
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    cv2.imshow("fabinet", frame)
    try:
        for (x, y, w, h) in faces:
            cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 0, 0), 2)  # 얼굴이 인식되었다면, 얼굴에 사각형을 출력한다
        winname = 'fabinet'
        cv2.namedWindow(winname)
        cv2.moveWindow(winname, 0, 0)
        #cv2.imshow(winname, frame)
        cv2.imwrite('image.jpg', frame)  # 인식된 얼굴 프레임을 image.jpg라는 파일명으로 저장한다.
        img = cv2.imread('image.jpg')  # image.jpg파일을 불러온다
        #key = cv2.waitKey(2000) & 0xFF
        is_success, im_buf_arr = cv2.imencode(".jpg", img)  # 이미지 서치를 위해 파일을 binary 형태로 이미지를 읽는다
        byte_im = im_buf_arr.tobytes()  # array를 raw data형태로 바꿔준다(이미지 서치를 위해 필요)
        response = client.search_faces_by_image(CollectionId=collectionId,
                                                Image={'Bytes': byte_im},
                                                FaceMatchThreshold=threshold,
                                                MaxFaces=maxFaces)
        faceMatches = response['FaceMatches']
        print('Matching faces')
        #if cv2.waitKey(1) & 0xFF == ord('q'):
        #   break

        if (len(faceMatches) > 0):
            for match in faceMatches:
                print('FaceId:' + match['Face']['FaceId'])  # 컬렉션에 저장된 메타데이터 ID출력(우리한텐 필요없는부분이긴 함)
                print('Similarity: ' + "{:.2f}".format(match['Similarity']) + "%")  # 유사도를 출력한다
                print(match['Face']['ExternalImageId'])  # 사진명을 출력한다(핵심부분)
                username = match['Face']['ExternalImageId']
                username = username.split('.')
                username = username[0]
                #username = username.replace(".jpg", "")
                server = "3.34.255.198"
                mclient = mqtt.Client()
                mclient.connect(server, 1883)
                mclient.publish("heum/username", username)
                print(username)
                msg.m2()
                cv2.destroyAllWindows()
                print
        else:
            print("not matched")
            msg.m3()
            cv2.destroyAllWindows()
            # msgBox1() 얼굴 미등록 인물
    except:
        #count = 0
        print("error")
        msg.m4()
        cv2.destroyAllWindows()

while(True):
    ret, frame = cap.read() #비디오를 한 프레임씩 읽는다
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    cv2.imshow("fabinet", frame)
    winname = 'fabinet'
    cv2.namedWindow(winname)
    cv2.moveWindow(winname, 0, 0)
    if(len(faces) >= 1): #인식된 얼굴 갯수가 하나일 경우
        count = count + 1
        for (x, y, w, h) in faces:
            cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 0, 0), 2)  # 얼굴이 인식되었다면, 얼굴에 사각형을 출력한다
    if(count == 29):
        timeOut = time.time() + 5
        msg.m1()
        count += 1
    if(count >= 30):
        try:
            frame2 = cv2.resize(frame, dsize=(0, 0), fx=0.5, fy=0.5)
            img = frame.copy()
            gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
            faces = detector(gray)
            for face in faces:
                shapes = predictor(gray, face)
                shapes = face_utils.shape_to_np(shapes)

                eye_img_l, eye_rect_l = crop_eye(gray, eye_points=shapes[36:42])
                eye_img_r, eye_rect_r = crop_eye(gray, eye_points=shapes[42:48])

                eye_img_l = cv2.resize(eye_img_l, dsize=IMG_SIZE)
                eye_img_r = cv2.resize(eye_img_r, dsize=IMG_SIZE)
                eye_img_r = cv2.flip(eye_img_r, flipCode=1)

                eye_input_l = eye_img_l.copy().reshape((1, IMG_SIZE[1], IMG_SIZE[0], 1)).astype(np.float32) / 255.
                eye_input_r = eye_img_r.copy().reshape((1, IMG_SIZE[1], IMG_SIZE[0], 1)).astype(np.float32) / 255.

                pred_l = model.predict(eye_input_l)
                pred_r = model.predict(eye_input_r)

                state_l = 'O %.1f' if pred_l > 0.05 else '- %.1f'
                state_r = 'O %.1f' if pred_r > 0.05 else '- %.1f'

                state_l = state_l % pred_l
                state_r = state_r % pred_r
                str = "Blink Your Eyes"
                cv2.rectangle(img, pt1=tuple(eye_rect_l[0:2]), pt2=tuple(eye_rect_l[2:4]), color=(51, 255, 153),
                              thickness=2)
                cv2.rectangle(img, pt1=tuple(eye_rect_r[0:2]), pt2=tuple(eye_rect_r[2:4]), color=(51, 255, 153),
                              thickness=2)
                cv2.putText(img, str, (0, 100), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 255, 0))
                cv2.putText(img, state_l, tuple(eye_rect_l[0:2]), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (51, 0, 255), 2)
                cv2.putText(img, state_r, tuple(eye_rect_r[0:2]), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (51, 0, 255), 2)
                cv2.namedWindow('result')
                cv2.moveWindow('result', 0, 0)
                cv2.imshow('result', img)
                if state_r.startswith("-") or state_l.startswith("-"):
                    blinkCount = blinkCount + 1
                    print(blinkCount)
            if blinkCount >= 3:
                faceDetect()
                count = 0
                blinkCount = 0
                cv2.destroyAllWindows()

            elif time.time() > timeOut:
                count = 0
                blinkCount = 0
                cv2.destroyAllWindows()
        except:
            cv2.destroyAllWindows()
            break
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
