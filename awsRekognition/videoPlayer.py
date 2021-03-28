import imutils
import cv2
import os
videoNum = 0 #광고명보다 1 작은 값으로 설정
path = "D://aws//rekognition//commercialVideos//"
file = os.listdir(path)
for i in range(0, len(file)):
    file[i] = file[i].replace(".mp4", "")
capture = cv2.VideoCapture("D://aws//rekognition//commercialVideos//" + str(file[videoNum]) + '.mp4')

while(True):
    ret, imgColor = capture.read()
    if ret == False: #영상이 끝날 경우
        file = os.listdir(path)
        print(file)
        for i in range(0, len(file)):
            file[i] = file[i].replace(".mp4", "")
        videoNum = videoNum + 1
        if videoNum == len(file):
            videoNum = 0
        capture = cv2.VideoCapture("D://aws//rekognition//commercialVideos//" + str(file[videoNum]) + '.mp4')
        #반드시 영상 파일 절대경로로 설정해야함!
        cv2.destroyAllWindows()
        ret, imgColor = capture.read()
    winname = file[videoNum]
    cv2.namedWindow(winname)
    imgColor = imutils.resize(imgColor, width=640)
    cv2.moveWindow(winname, 40, 30)
    cv2.imshow(winname, imgColor)
    if cv2.waitKey(1)&0xFF == 27: #ESC키로 프로그램 종료, cv2.waitKey 매개변수로 속도 조절
        break
capture.release()
cv2.destroyAllWindows()