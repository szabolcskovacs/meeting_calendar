# meeting_calendar
compile: mvn package
start: mvn spring-boot:run

In-memory H2 dbvel.

Az átlapolás megoldása:
Mivel a foglalások kötött hosszűak, azaz fél óra többszörösei, ezért minden foglalásnál az adott időtartamot kitöltő félórás időszeletket mentek el egy külön táblába, 
ahol a foglalás kezdete unique constraint.
Pl.: Másfél órás foglalás esetén három slice / unit. 09:30-11:00 foglalás esetén 09:30, 10:00, 10:30 szeletek.
Így ha valaki 10:30ra foglalna később unique constraintbe fut.
Ez nem egy production szintű megoldás, de a próbafeladat igényeit teljesíti.
Tesztet nem készítettem a foglalások 30 perccel oszthatóságának ellenőrzésére, mivel van ellenőrzés arra, hogy a kezdet és a vége időpontok fél vagy egész órában lehetnek csak, ez kiváltja.
Az extra feladatokra nem volt időm, de szóban össze tudom foglalni akár a megoldásokat rájuk.
