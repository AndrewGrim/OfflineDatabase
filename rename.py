import os.path
import os
import shutil

monsterNameList = ["Apceros",	
                    "Aptonoth",
                    "Barnos",
                    "Gajalaka",	
                    "Gajau",
                    "Gastodon",
                    "Girros",
                    "Grimalkyne",
                    "Hornetaur",
                    "Jagras",	
                    "Kelbi",	
                    "Kestodon Male",
                    "Kestodon Female",		
                    "Mernos",	
                    "Mosswine",	
                    "Noios",	
                    "Raphinos",	
                    "Shamos",		
                    "Vespoid",	
                    "Great Jagras",		
                    "Kulu-Ya-Ku",		
                    "Pukei-Pukei",		
                    "Barroth",		
                    "Jyuratodus",		
                    "Tobi-Kadachi",		
                    "Anjanath",		
                    "Rathian",	
                    "Tzitzi-Ya-Ku",		
                    "Paolumu",		
                    "Great Girros",		
                    "Radobaan",		
                    "Legiana",		
                    "Odogaron",		
                    "Rathalos",		
                    "Diablos",		
                    "Kirin",		
                    "Zorah Magdaros",	
                    "Dodogama",		
                    "Pink Rathian",		
                    "Bazelgeuse",		
                    "Deviljho",		
                    "Lavasioth",		
                    "Uragaan",		
                    "Azure Rathalos",	
                    "Black Diablos",		
                    "Nergigante",		
                    "Teostra",		
                    "Kushala Daora",		
                    "Vaal Hazak",		
                    "Xeno'jiva",		
                    "Kulve Taroth",		
                    "Lunastra",		
                    "Behemoth",		
                    "Leshen",	
                    "Ancient Leshen"] 	

localDir = os.path.abspath(os.path.dirname(__file__)) + "\src\images\\50-55"

dirList = os.listdir(localDir)

i = 49 # 0, 9, 19, 29, 39, 49
for item in dirList:
	#print(item)
	#print(monsterNameList[i])
	print(localDir + "\\" + item)
	print(localDir + "\\" + monsterNameList[i] + ".png")
	shutil.move(localDir + "\\" + item, localDir + "\\" + monsterNameList[i] + ".png")
	i += 1
	if i >= 56: #10, 20, 30, 40, 50, 56
		break