import os.path
import os
import shutil

localDir = os.path.abspath(os.path.dirname(__file__)) + "\src\images"

dirList = os.listdir(localDir)

i = 150
for item in dirList:
	shutil.move(localDir + "\\" + item, localDir + "\\" + str(i) + ".png")
	i += 1
	
i = 1
for item in dirList:
	shutil.move(localDir + "\\" + item, localDir + "\\" + str(i) + ".png")
	i += 1