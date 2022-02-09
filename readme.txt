1. Sposób uruchomienia programu
    Należy zbudować projekt i będąc w folderze readers_writers/readers_writers-clients/target wpisać w terminalu
    java -jar readers_writers-clients-1.0-jar-with-dependencies.jar

2. Omówienie algorytmu
    SETUP:
    Po stworzeniu klasy zasobu (ReadingRoom), menedżera tego zasobu (ReadingRoomManager) i dodaniu wątków do kolejki (ReadingRoomQueue),
      czytelnicy (Reader) i pisarze (Writer) czekają na pozwolenie na skorzystanie z zasobu.
    ITERACJA:
    Jeśli pierwszy w kolejce jest pisarz, to menedżer daje pozwolenie tylko jemu,
      a jeśli czytelnik, to menedżer wybiera jego i maksymalnie 4 kolejnych czytelników,
      pomijając pisarzy, którzy są w kolejce przed nimi.
    Po wybraniu wątków menedżer powiadamia wszystkie wątki i czeka na powiadomienie.
    Te które, otrzymały pozwolenie, przechodzą do operacji na zasobie, a pozostałe wracają do czekania.
    Pracujące wątki czekają jakiś czas, a następnie logują, co zrobiły.
    Wątek po zakończeniu pracy zabiera sobie pozwolenie na korzystanie z zasobu, wychodzi z czytelni ,powiadamia wszystkie wątki,
      zapisuje się znów do kolejki i wraca do czekania.
    Jako że menedżer nie wybrał jeszcze nowych wątków do wpuszczenia do czytelni, to pomimo powiadomienia wątki klientów
      będą dalej czekać.
    Wątek menedżera zasobu, po otrzymaniu powiadomienia sprawdzi, czy już wszyscy klienci opuścili czytelnię i jeśli tak,
      to nastąpi nowa iteracja algorytmu, lecz jeśli czytelnia nie jest pusta, to wróci do czekania.

