#Minecraft Auth by FDHoho007
Minecraft Auth ist ein Spigot Plugin (für Minecraft 1.8.8 oder höher getestet) mit REST API. Beim Verbinden mit dem Server wird die Verbindung abgebrochen und ein Code an den Spieler zurückgegeben. Diesen können externe Anwendungen verwenden, um die UUID des eben gejointen Spielers zu erhalten. Hierfür eine GET-Anfrage an Port 25580 des Servers in der Form Adresse:25580/&lt;Code&gt; senden. Rückgabewert ist ein JSON-Objekt mit der UUID (uuid) bzw. einem Fehler (error).
##Beispiele
Erfolgreiche Abfrage:
<code>{"uuid":"be53d009-f3cc-444f-9f28-ab87fd33733b"}</code><br>
Unbekannter Code: <code>{"error":"The given token cannot be resolved to a UUID. View Docs for more."}</code><br>
Abgelaufener Code: <code>{"error":"This token expired. You only have limited time use a token. Please request a new one."}</code>