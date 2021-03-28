import cv2
import boto3 #boto3는 aws를 쓰기 위해 사용하는 라이브러리
import pandas as pd
import paho.mqtt.client as mqtt
import time
import tkinter as tk
from tkinter import messagebox

root = tk.Tk
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
cap.set(3, 640) #WIDTH
cap.set(4, 480) #HEIGHT
face_cascade = cv2.CascadeClassifier('haarcascade_frontface.xml')#얼굴 인식 캐스케이드 파일을 읽는다

def closeMsgBox():
    time.sleep(3)
    root.destroy()
def msgBox1():
    tk.messagebox.askretrycancel("회원이 아니거나 인식률이 낮습니다.\n다시 인증해주세요.")
    closeMsgBox()
def msgBox2():
    tk.messagebox.showerror("얼굴이 인식되지 않았습니다.")
    closeMsgBox()

while(True):
    ret, frame = cap.read() #비디오를 한 프레임씩 읽는다
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    if(len(faces) == 1): #인식된 얼굴 갯수가 하나일 경우
        count = count + 1

    if(count > 50): #카운트가 3이 넘어가면 얼굴이 인식된것으로 간주, HW성능 따라 조절 필요
        print('얼굴이 검출되었습니다')
        try:
            count = 0
            for (x,y,w,h) in faces:
                 cv2.rectangle(frame,(x,y),(x+w,y+h),(255,0,0),2) #얼굴이 인식되었다면, 얼굴에 사각형을 출력한다
            winname = 'fabinet'
            cv2.namedWindow(winname)
            cv2.moveWindow(winname, 40, 30)
            cv2.imshow(winname, frame)
            cv2.imwrite('image.jpg', frame)  # 인식된 얼굴 프레임을 image.jpg라는 파일명으로 저장한다.
            img = cv2.imread('image.jpg')  #image.jpg파일을 불러온다
            key = cv2.waitKey(2000) & 0xFF
            is_success, im_buf_arr = cv2.imencode(".jpg", img)  #이미지 서치를 위해 파일을 binary 형태로 이미지를 읽는다
            byte_im = im_buf_arr.tobytes()  #array를 raw data형태로 바꿔준다(이미지 서치를 위해 필요)
            response = client.search_faces_by_image(CollectionId=collectionId,
                                                Image={'Bytes': byte_im},
                                                FaceMatchThreshold=threshold,
                                                MaxFaces=maxFaces)
            faceMatches = response['FaceMatches']
            print('Matching faces')
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
            
            if(len(faceMatches) > 0):
                for match in faceMatches:
                    print('FaceId:' + match['Face']['FaceId']) #컬렉션에 저장된 메타데이터 ID출력(우리한텐 필요없는부분이긴 함)
                    print('Similarity: ' + "{:.2f}".format(match['Similarity']) + "%") #유사도를 출력한다
                    print(match['Face']['ExternalImageId']) #사진명을 출력한다(핵심부분)
                    username = match['Face']['ExternalImageId']
                    username = username.replace(".jpg","")
                    mclient.publish("heum/username", username)
                    cv2.destroyAllWindows()
                    print
            else:
                print("not matched")
                cv2.destroyAllWindows()
                #msgBox1() 얼굴 미등록 인물
        except:
            count = 0
            print("error")
            cv2.destroyAllWindows()
           # msgBox2() 비 인물 인식
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break


cap.release()
cv2.destroyAllWindows()