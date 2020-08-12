<?php
require "connection.php";

$name=$_POST["name"];
$gender=$_POST["gender"];
$height=$_POST["height"];
$weight=$_POST["weight"];
$age=$_POST["age"];
$bmi=$_POST["bmi"];
$bmr=$_POST["bmr"];


$sql_query="insert into users values('DEFAULT','$name','$gender','$age','$height','$weight', '$bmi','$bmr');";

if(mysqli_query($con,$sql_query))
{
echo "Your data success recorded.";
}
else
{
echo "Your data failed recorded.";
}

?>