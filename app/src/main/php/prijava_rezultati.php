<!DOCTYPE html>
<head><meta http-equiv="Content-Type" content="text/html; charset=ansi_x3.110-1983">

</head>
<body>
<?php
// define variables and set to empty values

$turnir_id = $_GET['t_uuid'];
$bord_id = $_GET['b_uuid'];
$ns_pair = $_GET['ns_pair'];
$ew_pair = $_GET['ew_pair'];
$contract = $_GET['contract'];
$rbr = $_GET['rbr'];
$declarer = $_GET['declarer'];
$lead = $_GET['lead'];
$dec_tricks = $_GET['dec_tricks'];
$ns_result = $_GET['ns_result'];
$is_bye = $_GET['is_bye'];

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>


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
  

	try {
	    // p_uuid_turnir varchar(36), 
        // p_uuid_bord varchar(36), 
        // p_ns_pair INT,
        // p_ew_pair INT,
        // p_contract varchar(5),
        // p_rbr_board INT,
        // p_declarer varchar(1),
        // p_lead varchar(3),
        // p_dec_tricks INT,
        // p_ns_result INT,
        // p_is_bye BOOLEAN
        
		$sql = "call Turnir_Unos_Rezultat('$turnir_id', '$bord_id', $ns_pair, $ew_pair, '$contract', $rbr, '$declarer', '$lead', $dec_tricks, $ns_result, 0)";
		print ("$sql<br>" . PHP_EOL);
		$result = $dbh->query ($sql);
		if ($result === TRUE) {
			echo "Rezultat ID: ". $id. " dodan u turnir.<br>". PHP_EOL;
		} else {
			echo "Rezultat ID: ". $id. " nije prosao azuriranje na turniru!<br>" . PHP_EOL;
		}
		
		print("$turnir_id, $bord_id, $ns_pair, $ew_pair, $contract, $rbr, $declarer, $lead, $dec_tricks, $ns_result, $is_bye <br>" . PHP_EOL);
	}
	catch (PDOException $e)
	{
		print ("Ne mogu se spojiti na server<BR>". PHP_EOL);
		print ("Error code: " . $e->getCode () . PHP_EOL);
		print ("Error message: " . $e->getMessage () . PHP_EOL);
		exit (1);
	}

	$dbh = NULL;
?>

</body>
</html>