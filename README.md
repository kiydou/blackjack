# Blackjack mit Reinforcement Learning

## Grundidee des Projekts

In unserem Projekt haben wir ein Blackjack-Programm entwickelt, das mithilfe von
Reinforcement Learning selbstständig lernt, bessere Spielentscheidungen zu treffen.
Ziel war es, dass die KI durch viele gespielte Runden ihre Entscheidungen optimiert,
um eine möglichst hohe Winrate zu erzielen.

Zuerst entschieden wir uns für ein Deep-Q-Learning Ansatz, jedoch nach vielen
Fehlversuchen wechselten wir trotzdem zu einem klassischen Q-Learning Ansatz.
Das Programm verwendet eine Q-Tabelle, in der gespeichert wird, welche Aktion im
jeweiligen Spielzustand am sinnvollsten ist. Unsere KI kann entweder eine
Karte Ziehen (Hit) oder das Spiel eigenständig beenden (Stand). Sie hat keine Aktionen wie
Double oder Split. Mithilfe von unserem Programm konnten wir eine Policy-Tabelle
erstellen und sie mit einer klassischen Blackjack-Policy vergleichen.

## Probleme während der Programmierung

- **Unstabile Ergebnisse**
  Anfangs haben wir das Spiel nur mit Deep-Q-Learning umgesetzt,
  ohne eine Q-Tabelle zu nutzen. Dabei haben wir gemerkt, dass sowohl die
  Rewards als auch die Winrate, trotz endlosem Coden, stark geschwankt
  haben. Die Ergebnisse konnten wir nicht wirklich deuten und eine Lernkurve
  kam auch nicht zustande.

- **Zu komplexer Kartenzählen-Ansatz**
  Ursprünglich wollten wir Kartenzählen implementieren und haben versucht,
  einzelne Karten als Array an die KI zu übergeben. Dieser Ansatz hat den
  Zustandsraum allerdings extrem vergrößert und das Lernen unnötig
  kompliziert gemacht.

- **Passende Hyperparameter finden**
  Die Wahl von Lernrate, Discount-Faktor, Explorationsrate usw., war auch
  nicht einfach. Kleine Änderungen hatten in der Ausgabe hohe
  Auswirkungen und unsere KI hat dadurch schlechtere Ergebnisse erzielt.

## Unsere Lösungsansätze

- **Erweiterung mit einer Q-Tabelle**
  Das größte Problem (schwankende Ergebnisse) haben wir durch eine
  Implementierung von einer Q-Tabelle gelöst. Dadurch konnten wir für jeden
  Spielzustand speichern, welche Aktion langfristig die besten Ergebnisse
  liefert. Das hat den Lernprozess deutlich stabiler gemacht und die
  Schwankungen stark reduziert.

- **Vereinfachung der Eingaben für die KI**
  Den Kartenzählen-Ansatz haben wir verworfen und stattdessen den Score,
  also die Summe der Karten, als Input übergeben. Dadurch hatten wir ein
  deutlich kleineren Zustandsraum. Das hat zur einer schnelleren Lernrate
  geführt und die Q-Tabelle übersichtlicher gemacht.

- **Testen und Anpassen der Hyperparameter**
  Durch langes Ausprobieren verschiedener Werte konnten wir Parameter
  finden, bei denen unsere KI anständig lernt und sich stetig verbessert.

## Erkenntnisse und Ergebnisse

- Klassisches Q-Learning hat für deutlich stabilere und bessere Lernergebnisse gesorgt
- Einzelne Karten als Input machen die Tabelle nur unnötig komplex
- Hyperparameter haben großen Einfluss auf die Ergebnisse
- Nach vielen Spielrunden verbessert sich die Winrate stetig und landet am
  Ende bei ca. 42%
- Reinforcement Learning funktioniert am besten mit klar definierten Zuständen

Unsere Q-Learning-Policy unterscheidet sich von einer klassischen Blackjack-Policies
kaum. Bei niedrigen Punkteständen wird meist eine weitere Karte gezogen, bei hohen
Punkteständen meist beendet. Auch "Hard" Hands werden von unserer Q-Tabelle gut
ausgewertet. Insgesamt zeigt unsere KI ein gutes und optimiertes Spiel- und
Lernverhalten.