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

if ($_SERVER["REQUEST_METHOD"] == "POST") {
  
  
  if (empty($_POST["id"])) {
    $idErr = "* Id is required";
  } else {
    $id= test_input($_POST["id"]);
  }

  if (empty($_POST["turnir"])) {
    $turnirErr = "Turnir is required";
  } else {
    $turnir_id = test_input($_POST["turnir"]);
  }
} 
else {
	$turnir_id = $_GET['turnir'];
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
			$sql = "call Turnir_Unos_Igraca($turnir_id, $id)";
			$result = $dbh->query ($sql);
			if ($result === TRUE) {
				echo "Igrač ID: ". $id. " dodan u turnir, traži partnera.<br>". PHP_EOL;
			} else {
				echo "Igrač ID: ". $id. " već je prijavljen za traženje partnera ili je dodan u turnir.<br>" . PHP_EOL;
			}
		}
		catch (PDOException $e)
		{
			print ("Ne mogu se spojiti na server<BR>". PHP_EOL);
			print ("Error code: " . $e->getCode () . PHP_EOL);
			print ("Error message: " . $e->getMessage () . PHP_EOL);
			exit (1);
		}
	}

	$sql = "select t.tim, group_concat(t.imena separator ' - ') clanovi
		from (select a.id_tim tim, concat(b.ime, ' ', b.prezime) imena
				FROM TURNIR_IGRACI a, IGRACI b 
				WHERE a.id_turnir = 4 and a.id_trazi is null and b.id = a.id_igrac) t
		group by t.tim";
	
	$result = $dbh->query($sql);

	echo "Prijavljeni parovi za turnir id: $turnir_id<br>". PHP_EOL;

	$brojac = 0;
	while($row = $result->fetch (PDO::FETCH_ASSOC)) {
			echo $row["tim"]. " ". $row["clanovi"]. "<br>". PHP_EOL;
			$brojac++;
	}

	echo "$brojac rezultata<br>". PHP_EOL;;


	$sql = "SELECT a.VRIJEME, a.ID_IGRAC, b.IME, b.PREZIME, b.mail
	FROM TURNIR_IGRACI a, IGRACI b
	WHERE a.ID_TIM IS NULL 
	AND b.ID = a.ID_IGRAC AND a.ID_TURNIR = $turnir_id";

	$result = $dbh->query($sql);

	echo "Igrači koji traže partnere za turnir id: $turnir_id<br>". PHP_EOL;


	while($row = $result->fetch (PDO::FETCH_ASSOC)) {
			echo $row["VRIJEME"]. " ". $row["IME"]. " ". $row["PREZIME"]. " <a href='mailto:". $row["mail"]. "'>mail</a><br>". PHP_EOL;
	}
	echo "$brojac rezultata.<br>". PHP_EOL;;


	if (!$id){
		echo '<form method="post" action="'. htmlspecialchars($_SERVER["PHP_SELF"]). '">'. PHP_EOL;
		echo 'ID: <input type="text" name="id"/>'. $idErr. '<br>'. PHP_EOL;
		echo '<input type="hidden" name="turnir" value="'. $turnir_id. '"/><br>'. PHP_EOL;
		echo '<input type="submit"/>'. PHP_EOL;
		echo '</form>'. PHP_EOL;
	}

	$dbh = NULL;
?>

</body>
</html>