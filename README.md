In questa repository è presente l'architettura Server per la modalità "Gioca Online"

L'architettura Client è presente nella seguente repository:
https://github.com/Martella/Progetto_IGES

ISTRUZIONI PER FAR PARTIRE CORRETTAMENTE LA MODALITA'"Gioca Online"

--------Istruzioni da compiere una sola volta:
1) Scarica il progetto presente in questa repository: https://github.com/Martella/Progetto_IGES
2) Scarica il server presente nella repository: https://github.com/Martella/Progetto_IGES_Server
3) Crere una configurazione "rmic" che esegue rmic.exe all'interno della cartella dei progetti
4) Crere una configurazione "RMIregistry" che esegue rmiregistry.exe all'interno della cartella dei progetti

--------Istruzioni da compiere ogni volta che si vuole avviare la modalità "Gioca Online":
5) Eseguire la configurazione "rmic"
6) Eseguire la configurazione "RMIregistry"
7) Eseguire la classe Server.java del progetto: https://github.com/Martella/Progetto_IGES_Server
8) Eseguire la classe Main.java del progetto https://github.com/Martella/Progetto_IGES e cliccare sulla modalità "Gioca Online", in questo modo il giocatore 1 sarà in attesa di un nuovo giocatore
9) Eseguire nuovamente la classe main.java del progetto https://github.com/Martella/Progetto_IGES e cliccare sulla modalità "Gioca Online", in questo il server farà iniziare la partita tra giocatore 1 e giocatore 2
