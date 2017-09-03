<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<?php

  require_once "veza.php";
  
    if ($_SERVER["REQUEST_METHOD"] == "POST") {

        $t_id= test_input($_POST["t_id"]);
        $t_uuid= test_input($_POST["t_uuid"]);
    } 
    else {
    	$t_id = $_GET['t_id'];
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
  
  $sql  = "SELECT * FROM TURNIRI_BORDOVI WHERE UUID_TURNIR = '$t_uuid'";           
           
  $array = $dbh->query($sql)->fetchAll(PDO::FETCH_ASSOC);
  echo json_encode($array);

  $dbh = NULL;

?>

</body>
</html>