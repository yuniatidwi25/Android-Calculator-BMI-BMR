<?php
require "connection.php";
$name=$_POST["name"];
$age=$_POST["age"];

$sql = "delete from users where name='$name' and age='$age';";
$result=mysqli_query($con,$sql);

if (mysqli_affected_rows($con) ==-1 || mysqli_affected_rows($con)==0) 
{
  echo "Data fail to be deleted, or there are no such data";
} 
else 
{
  echo "Your data success deleted.";
 
}

$con->close();
?>
