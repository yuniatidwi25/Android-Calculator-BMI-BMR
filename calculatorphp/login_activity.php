<?php
require "connection.php";
require "urutan.php";
$sql="select id, name, age from users;";

$result=mysqli_query($con,$sql);
$response=array();

while($row=mysqli_fetch_array($result))
{
array_push($response,array("id"=>$row[0],"name"=>$row[1],"age"=>$row[2]));
}

echo json_encode(array("server_response"=>$response));

mysqli_close($con);

?>