<?php
  $games = $_POST["game"];
  $number = count($games);
  if ($number==0){
    echo "Error";
  }
  $db = new mysqli("localhost","root","","games");
  if ($db->connect_error){
    echo "Error";
  }
  $sql="";
  for ($i=0;$i<$number;$i++){
    $sql="UPDATE games SET Count = Count + 1 WHERE Name='" . $games[$i] . "'";
    $db->query($sql);
  }
  $results=$db->query("SELECT * FROM games");
  echo "<h2 class='res'>Poll Results</h2>";
  while ($row = $results->fetch_assoc()){
    echo "<p class='res' >" . $row["Name"] . " - Votes: " . $row["Count"] . "</p>";
  }
?>