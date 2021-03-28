import sys
from PyQt5.QtWidgets import QApplication, QLabel
from PyQt5.QtCore import Qt
from PyQt5 import QtGui
from PyQt5.QtGui import QPalette
import time

class App(QLabel):
    def __init__(self):
        super(App, self).__init__()
        self.setWindowTitle('Window!!!')
        self.resize(600,400)
        self.changeFont()
        self.changeFontColor()
        self.setText('레이블 입니다.')
    def changeFont(self):
        font = QtGui.QFont()
        font.setBold(True)
        font.setPointSize(45)
        font.setUnderline(True)
        self.setFont(font)
    def changeFontColor(self):
        pal = QtGui.QPalette()
        pal.setColor(QPalette.WindowText, QtGui.QColor(Qt.red))
        self.setPalette(pal)
if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = App()
    window.show()
    time.sleep(3)
    window.hide()
    sys.exit(app.exec_())