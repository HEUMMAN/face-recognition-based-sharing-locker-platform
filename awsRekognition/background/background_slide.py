import sys
import glob
import cv2
import time
# images에 있는 모든 jpg 파일을 img_files 리스트에 추가
img_files = glob.glob('*.jpg')

# 이미지 없을때 예외처리
if not img_files:
    print("jpg 이미지가 읎어요..")
    sys.exit()

cv2.namedWindow('heum_background', cv2.WINDOW_NORMAL)
cv2.setWindowProperty('heum_background', cv2.WND_PROP_FULLSCREEN, cv2.WINDOW_FULLSCREEN)

# 슬라이드 쇼 반복을 위한 반복문
count = len(img_files)
index = 0

while True:
    img_files = glob.glob('*.jpg')

    img = cv2.imread(img_files[index])


    # 예외처리
    if img is None:
        print("이미지를 불러오는데 실패했습니다.")
        break

    # Q가 입력되면 break
    cv2.imshow('heum_background', img)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

    # index가 이미지 리스트보다 커지거나 같아지면 다시 0으로
    index += 1
    if index >= count:
        index = 0

    time.sleep(7)
cv2.destroyAllWindows()