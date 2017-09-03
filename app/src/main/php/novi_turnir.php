<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- uključivanje Bootstrap CSS-a putem web linka -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<!-- uključivanje JQuery framework-a putem web linka -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>

<!-- uključivanje Bootstrap framework-a putem web linka -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<?php
// define variables and set to empty values

$id = "";
$turnir_id = "";
$idErr = "";
$klub = "";
$broj_bordova = "";
$obracun = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
  
  if (empty($_POST["klub"])) {
    $idErr = "* Klub is required";
  } else {
    $klub= test_input($_POST["klub"]);
  }

  if (empty($_POST["broj_bordova"])) {
    $idErr = "Broj Bordova is required";
  } else {
    $broj_bordova = test_input($_POST["broj_bordova"]);
  }
  
  if (empty($_POST["obracun"])) {
    $idErr = "Obračun is required";
  } else {
    $obracun = test_input($_POST["obracun"]);
  }
  
  $id = 1;
  
} 
else {
    /*
    $sql = "SELECT count(*) ID FROM `TURNIRI` WHERE DATUM = CURDATE()";
    $result = $dbh->query($sql);
    $row = $result->fetch (PDO::FETCH_ASSOC);
    $turnir_id = $row["ID"];
    if ($turnir_id > 0){
        $id = 1;
    }
    */
}

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>
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

	if ($id) {
		try {

			$sql = "call Novi_Turnir($klub, $obracun, $broj_bordova)";
			$result = $dbh->query ($sql);
			
			if ($result === TRUE) {
				echo "Turnir ID: ". $id. " kreiran sa današnjim datumom.<br>". PHP_EOL;
			} else {
				echo "Turnir ID: ". $id. " nije kreiran jer turnir sa današnjim datumom već postoji.<br>" . PHP_EOL;
			}
	
	        $sql = "SELECT ID FROM `TURNIRI` WHERE DATUM = CURDATE()";
	        $result = $dbh->query($sql);
	        $row = $result->fetch (PDO::FETCH_ASSOC);
			$turnir_id = $row["ID"];
			
	        $sql = "select id_bord, id_turnir, uuid_turnir, uuid_bord, dlm, vrijeme, rbr_bord 
	            from `TURNIRI_BORDOVI` 
	            where id_turnir = $turnir_id";
	
	        $result = $dbh->query($sql);

	        echo "Kreirani bordovi za turnir id: $turnir_id<br>". PHP_EOL;

	        $brojac = 0;
	        while($row = $result->fetch (PDO::FETCH_ASSOC)) {
			    echo $row["id_bord"]. " ". $row["rbr_bord"]. " ". $row["uuid_bord"]. "<br>". PHP_EOL;
			    $brojac++;
	        }
            
	        echo "$brojac rezultata<br>". PHP_EOL;
		}
		catch (PDOException $e)
		{
			print ("Ne mogu se spojiti na server<BR>". PHP_EOL);
			print ("Error code: " . $e->getCode () . PHP_EOL);
			print ("Error message: " . $e->getMessage () . PHP_EOL);
			exit (1);
		}
	}
	
	if (!$id){
		echo '<form method="post" action="'. htmlspecialchars($_SERVER["PHP_SELF"]). '">'. PHP_EOL;
		echo 'Klub: <input type="text" name="klub"/><br>'. PHP_EOL;
        echo 'Broj bordova: <input type="text" name="broj_bordova"/><br>'. PHP_EOL;
        echo 'Obračun: <input type="text" name="obracun"/><br>'. PHP_EOL;
		echo '<input type="submit"/>'. PHP_EOL;
		echo '</form>'. PHP_EOL;
	}	

	$dbh = NULL;
?>

</body>
</html>