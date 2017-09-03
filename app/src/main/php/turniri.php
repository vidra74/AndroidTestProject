<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- uključivanje Bootstrap CSS-a putem web linka -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<!-- uključivanje JQuery framework-a putem web linka -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>

<!-- uključivanje Bootstrap framework-a putem web linka -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
	<div class="navbar-header">
      <!--Tipka vidljiva samo na mobilnim uređajima-->
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#linkovi" aria-expanded="false">
         <span class="icon-bar"></span>    <!--Prva crtica unutar tipke-->
         <span class="icon-bar"></span>    <!--Druga crtica unutar tipke-->
         <span class="icon-bar"></span>    <!--Treća crtica unutar tipke-->
      </button>
      <!--Naziv proizvoda vidljiv cijelo vrijeme na istom mjestu-->
      <a class="navbar-brand" href="#" style="color:#01a1ff">Škola Bridža Dubrovnik</a>
    </div>  
    <!--div čije klase i id određuju ponašanje na mobilnim uređajima-->
    <div class="collapse navbar-collapse" id="linkovi">
      <!--Lista linkova poravnatih desno na većim ekranima-->
      <ul class="nav navbar-nav navbar-right">
        <li><a href="turniri.php">Turniri</a></li>
        <li><a href="#">Tečajevi</a></li>
        <li><a href="#">Pravila</a></li>
	<li><a href="#">Zadaci</a></li>
	<li><a href="clanovi.php">Kontakt</a></li>
      </ul>
    </div>
  </div>
</nav>

<?php

  require_once "veza.php";

  // Create connection
  try
  {
    $dbh = DBSkolaBridza::connect ();
  }
  catch (PDOException $e)
  {
    print ("Ne mogu se spojiti na server<BR>". PHP_EOL);
    print ("Error code: " . $e->getCode () . PHP_EOL);
    print ("Error message: " . $e->getMessage () . PHP_EOL);
    exit (1);
  }

  // Sredi hrvatska slova
  try
  {
    DBSkolaBridza::postaviHrvatski($dbh);
  }
  catch (PDOException $e)
  {
    print ("Ne mogu postaviti hrvatska slova na db server<BR>". PHP_EOL);
    print ("Error code: " . $e->getCode () . PHP_EOL);
    print ("Error message: " . $e->getMessage () . PHP_EOL);
    exit (1);
  }
  $sql = "SELECT a.ID, b.Obracun, c.Naziv Klub, a.Datum, a.Vrijeme, a.Broj_Bordova, a.Status, a.uuid
           FROM TURNIRI a, OBRACUN_TURNIRA b, KLUBOVI c
           WHERE b.id = a.Obracun and c.id = a.Klub
           ORDER BY a.Datum DESC";

  $sth = $dbh->query ($sql);
  $count = 0;
  while ($row = $sth->fetch (PDO::FETCH_NUM))
  {
    if ($row[6] == 1) {
      print ("<a href='turnir_rezultati.php?t_uuid=" . $row[7] . "'>" . $row[0]. "</a> : " . $row[3]. " " . $row[4]. " - " . $row[1]. " " . $row[5]. 
      		" <a href='prijava_turnir.php?turnir=". $row[0]. "'>" . $row[2]."</a><br>". PHP_EOL);
    	}
    	else {
        	print ($row[0]. ": " . $row[3]. " " . $row[4]. " - " . $row[1]. " " . $row[5]. " " . $row[2]."<br>". PHP_EOL);
    }
    $count++;
  }
  printf ("Ukupan broj turnira: %d <BR>". PHP_EOL, $count);
  $dbh = NULL;

?>
<a href='novi_turnir.php'>Kreiraj danas novi turnir</a>

</body>
</html>