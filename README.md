# TetrisGame
Temat projektu - tetris.

W klasie GameArea w linii 196 w funkcji checklines jest napisane:

 if (linesCounter > 1) scoreCounter.linesRemoved(linesCounter);
 
 przez co program nie nalicza pojedynczych usuniętych linii. 
 Powinno tam być linesCounter > 0 i początkowo tak było - 
 nieopatrznie zmieniłem tę linię ostatniego dnia i nie zauważyłem błędu przed oddaniem projektu.
