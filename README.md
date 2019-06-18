Intelligent Grid

Същност на приложението

Intelligent Grid съдържа 4 колони с данни. 
Една за текст, две за числа с плаваща запетая и една за проценти. 
Следват 3 колони за резултати, всяка една отнасяща се за формула.
Приложението позволява добавяне на неопределен брой редове, 
премахване както на един ред, така и на няколко наведнъж.

![image](https://drive.google.com/uc?export=view&id=1C7aA12F6cftToWvDtr43CshE8FB0gF6z)

Бутонът  “Configuration” дава възможност за допълнително персонализирате на таблицата:
•	Добавяне на брой редове.
•	Брой цифри след запетаята.
•	Преименуване на която и да е колона.

![image](https://drive.google.com/uc?export=view&id=1vNKReLlBi-obAKndNRGIYigqlpknejxK)

В дясната част на прозореца се намират няколко полета за въвеждане на формула. 
Формулите могат да бъдат написани по различни начини. Имената на символите задължително
трябва да съвпадат с имената на колоните указани в таблицата. Intelligent Grid притежава аритметчни 
действия със събиране, изваждане, умножение, деление, степенуване както и деление с остатък. 

Пример:
•	(B+C)*D
•	((71/3)- 19 - sin(B) * -.5) ^ 2 
•	B[0] + D[1] - C[0] / sum(B,C,D)

![image](https://drive.google.com/uc?export=view&id=13TcGhN9iHP20-FsOyGdQ3e--1Y60xzw9)

Ръководство за потребителя

Единственото изсикване е потребителят да има Java и Apache Tomcat 9.

Изтегля се приложението Intelligent Grid, 
след което файлът „intelligent.grid.war” се копира от 
Intelligent Grid->target в директорията на Apache Tomcat 9->webapps
![image](https://drive.google.com/uc?export=view&id=1rY_lP7ld62T__k_usu_H3gleYTwTlgsh)

Сървърът се пуска от директорията на сървъра->bin, файлът „startup.bat”. 
![image](https://drive.google.com/uc?export=view&id=1wZH6-kyCJevjIrnrCq8hX1yPuYeaX13U)

След това се отваря браузър по избор и на URL се въвежда “http://localhost:8080/intelligent.grid”. 
![image](https://drive.google.com/uc?export=view&id=123xSH1ZtTKyOFXHyGMKel4s-oDL5N2Cd)

След приключване на работата се затваря браузъра, а сървърът се спира от 
директорията на сървъра->bin, файлът „shutdown.bat”.
![image](https://drive.google.com/uc?export=view&id=1QQcO-L0ZhEOYhW0a6T6V1nTuETU5kPhY)
