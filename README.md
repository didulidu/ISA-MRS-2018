# ISA-MRS-2018
Tehnologije: Spring boot, maven, MySQLWorkbench sa propratnim paketima, jQuery na frontendu


Uputstvo za pokretanje aplikacije na localhost-u:
1. Kreirati konekciju ka bazi podataka "springdb".
    username - root
    password - ftn1
2. Klonirati repozitorijum i pristupiti projektu preko nekog IDEA (Intellij)
3. Iz fajla "data.sql" pokrenuti skriptu u MySQL workbench-u. Oznaciti "springdb" bazu pre toga.
NAPOMENA: u tabeli "theater_admin_user" promeniti vrednost e-mail-a iz "vlada@gmail.com" u bilo sta drugo, zato sto greskom postoji isti takav mail u tabeli sa registrovanim korisnicima
4. posetiti localhost:8080
Uputstvo za deploy aplikacije:

-preduslov je instalacija Heroku CLI-a

1. git clone https://github.com/filipbasara0/ISA-MRS-2018.git
2. git cd ISA-MRS-2018
3. heroku login (sa kredencijalima dobijenim nakon registracije na sajtu)
4. heroku create theatres2 --region eu --buildpack heroku/java
5. git push heroku master
6. heroku ps:scale web=1 (za proveru uspesnosti deploy-a)
7. heroku open
