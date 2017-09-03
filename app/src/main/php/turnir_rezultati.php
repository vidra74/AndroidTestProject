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
  
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
        $t_uuid= test_input($_POST["t_uuid"]);
    } 
    else {

    	$t_uuid = $_GET['t_uuid'];
    }  

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
  

  
  $sql = "SELECT a.*
           FROM TURNIRI_REZULTATI a
           WHERE a.uuid_turnir = '$t_uuid'
           ORDER BY a.rbr_board, a.ns_pair ";
           
  // print("$sql <br>" . PHP_EOL);           

  $sth = $dbh->query ($sql);
  
  $count = 0;
  
  while ($row = $sth->fetch (PDO::FETCH_NUM))
  {
    if ($count == 0){
        print ("<table><tr><th>RBR</th><th>NS Pair</th><th>EW Pair</th><th>Declarer</th><th>Contract</th><th>Lead</th><th>Tricks</th><th>NS Result</th></tr>" . PHP_EOL);
    }
    print ("<tr><td> " . $row[6]. " </td><td> " . $row[3]. " </td><td> " . $row[4]. " </td><td> " . $row[7]. " </td><td> " . $row[5]. " </td><td> " . $row[8] . " </td><td> " . $row[9]. " </td><td> " . $row[10]." </td></tr>". PHP_EOL);
    $count++;
  }
    if ($count > 0){
        print ("</table>" . PHP_EOL);
    }  
  printf ("Ukupan broj rezultata: %d <BR>". PHP_EOL, $count);
  $dbh = NULL;

?>

</body>
</html>